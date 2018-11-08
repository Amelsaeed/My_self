package com.example.ahmedmagdy.theclinic.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmedmagdy.theclinic.Adapters.BookingAdapter;
import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.BookingClass;
import com.example.ahmedmagdy.theclinic.classes.BookingTimesClass;
import com.example.ahmedmagdy.theclinic.classes.UtilClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

//import com.example.ahmedmagdy.theclinic.Adapters.DoctorAdapter;

public class DoctorProfileActivity extends AppCompatActivity implements OnRequestPermissionsResultCallback {
    ImageView ppicuri;
    TextView pname,pcity,pspeciality,pdegree,pphone,pprice,ptime,paddbook;
    EditText peditbox ;
    EditText dialogAddress;
    private ProgressBar progressBarBooking, progressBarImage;

    private Uri imagePath;
    private final int GALLERY_REQUEST_CODE = 1;
    private final int CAMERA_REQUEST_CODE = 2;
    String address;
    String mTrampPhotoUrl = "";

    double latitude;
    double longitude;
    int reloadCount = 0;
    byte[] byteImageData;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference databaseDoctor;
    private DatabaseReference databaseUserReg;
    private DatabaseReference databasetimeBooking;

    String DoctorID, uid,mDate,picuri;
    ListView listViewBooking;
    private List<BookingClass> bookingList;
    final int theRequestCodeForLocation = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    Boolean isPermissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        mAuth = FirebaseAuth.getInstance();

        databaseDoctor = FirebaseDatabase.getInstance().getReference("Doctordb");
        databaseUserReg = FirebaseDatabase.getInstance().getReference("user_data");
        databasetimeBooking = FirebaseDatabase.getInstance().getReference("bookingtimes");

        mStorageRef = FirebaseStorage.getInstance().getReference("Photos");

        progressBarBooking = (ProgressBar) findViewById(R.id.booking_progress_bar);
        progressBarImage = (ProgressBar) findViewById(R.id.progressbar_image);

        listViewBooking= (ListView)findViewById(R.id.list_view_booking);
        bookingList=new ArrayList<BookingClass>();

        pname = (TextView) findViewById(R.id.pname);
        pcity = (TextView) findViewById(R.id.ppcity);
        pspeciality = (TextView) findViewById(R.id.pspeciality);
        pdegree = (TextView) findViewById(R.id.pdegree);
        pphone = (TextView) findViewById(R.id.pphone);
        pprice = (TextView) findViewById(R.id.pprice);
        ptime = (TextView) findViewById(R.id.ptime);

        peditbox = (EditText) findViewById(R.id.peditbox);
        ppicuri = (ImageView) findViewById(R.id.edit_photo);
        paddbook = (TextView) findViewById(R.id.add);

        FirebaseUser user = mAuth.getCurrentUser();//mAuth.getCurrentUser().getUid()
        if (user != null) {
            if(user.getUid() != null){
                uid = user.getUid();
                // Toast.makeText(DoctorProfileActivity.this, uid, Toast.LENGTH_LONG).show();

            }

        }
        Intent intent = getIntent();
        DoctorID = intent.getStringExtra("DoctorID");
        // Toast.makeText(DoctorProfileActivity.this, DoctorID, Toast.LENGTH_LONG).show();

        if(!DoctorID.equals(uid)){paddbook.setVisibility(View.GONE);}
        getallData();


        pname.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "Name";
                    editDialog(whatdata);}
                return true;
            }

        });
        pname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Doctor's name", Toast.LENGTH_LONG).show();
            }
        });
        pcity.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "State/ City/ Region";
                    editDialog(whatdata);}
                return true;
            }
        });
        pcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Doctor's state and city", Toast.LENGTH_LONG).show();
            }
        });
        pspeciality.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "Specialty";
                    editDialog(whatdata);}
                return true;
            }
        });
        pspeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Doctor's specialty", Toast.LENGTH_LONG).show();
            }
        });
        pdegree.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "Degree";
                    editDialog(whatdata);}
                return true;
            }
        });
        pdegree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Doctor's degree", Toast.LENGTH_LONG).show();
            }
        });
        pphone.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "Phone Number";
                    editDialog(whatdata);}
                return true;
            }
        });
        pphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Doctor's Phone Number", Toast.LENGTH_LONG).show();
            }
        });
        pprice.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "Detection price";
                    editDialog(whatdata);}
                return true;
            }
        });
        pprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Detection price", Toast.LENGTH_LONG).show();
            }
        });
        ptime.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if(DoctorID.equals(uid)){
                    String whatdata = "Average detection time in min";
                    editDialog(whatdata);}
                return true;
            }
        });
        ptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(DoctorProfileActivity.this, "Average detection time in min", Toast.LENGTH_LONG).show();
            }
        });
        paddbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String whatdata = "Ex:from Sat to Mon in address at 15:00 clock";
                editDialogbook();
            }
        });

        ////////////////////////////////
        //--------Gps---------------------


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestPermission();

        if(isPermissionGranted){

            getLocation();
        }else{
            requestPermission();
            if(isPermissionGranted){
                //We have it, Get the location.
                getLocation();
            }
            else {
                Toast.makeText(DoctorProfileActivity.this, "Please Give us permission so you can use the app", Toast.LENGTH_SHORT).show();
            }
        }
        //--------------------------------------
        peditbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(DoctorID.equals(uid)) {

                    final String about1 = peditbox.getText().toString().trim();
                    databaseDoctor.child(DoctorID).child("cAbout").setValue(about1);
                }/**else {
                 //////////////////////////////
                 Toast.makeText(DoctorProfileActivity.this, "You can't change it", Toast.LENGTH_LONG).show();
                 final ValueEventListener postListener1 = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot1) {
                String DoctorAbout = dataSnapshot1.child(DoctorID).child("cAbout").getValue(String.class);
                if (DoctorAbout != null) {peditbox.setText(DoctorAbout);}
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                }
                };
                 databaseDoctor.addValueEventListener(postListener1);
                 ////////////////////////////////////
                 }**/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });
        listViewBooking.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long id) {
                // TODO Auto-generated method stub

                BookingClass bookingclass = bookingList.get(position);
                final String timeID= bookingclass.getCbid();
                // Toast.makeText(DoctorProfileActivity.this, timeID, Toast.LENGTH_LONG).show();
//////////////////////////////////////////////////
                final Dialog dialog = new Dialog(DoctorProfileActivity.this);
                dialog.setContentView(R.layout.chose_account_dialog);
                dialog.setTitle("Chose an account");
                dialog.setCanceledOnTouchOutside(false);

                TextView youraccount = (TextView) dialog.findViewById(R.id.your_account_tv);
                TextView anotheraccount = (TextView) dialog.findViewById(R.id.another_acount_tv);
                TextView cancel = (TextView) dialog.findViewById(R.id.dismiss_dialog);

                youraccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openClenderAction(timeID , position);
                    }
                });

                anotheraccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mAuth.getInstance().signOut();
                        Intent it = new Intent(DoctorProfileActivity.this, LoginActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        finish();
                        startActivity(it);

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCanceledOnTouchOutside(false);

                dialog.show();
                //////////////////////////////////

                return true;
            }
        });

        /**  listViewBooking.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        BookingClass bookingclass = bookingList.get(position);
        final String timeID= bookingclass.getCbid();
        Toast.makeText(DoctorProfileActivity.this, timeID, Toast.LENGTH_LONG).show();
        ///***********************calender**********************************************
        ImageGenerator mImageGenerator = new ImageGenerator(DoctorProfileActivity.this);
        // Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50, 50);
        // Set the size of the date and month font in dip.
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);
        // Set the position of the date and month in dip.
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);
        // Set the color of the font to be generated
        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);
        // abookingphoto.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View v) {
        final Calendar mCurrentDate = Calendar.getInstance();
        int year=mCurrentDate.get(Calendar.YEAR);
        int month=mCurrentDate.get(Calendar.MONTH);
        int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mPickerDialog =  new DatePickerDialog(DoctorProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
        String datedmy= Year+"_"+ (Month+1)+"_"+Day;
        Toast.makeText(DoctorProfileActivity.this, datedmy, Toast.LENGTH_LONG).show();
        // Toast.makeText(context, id+doctorID, Toast.LENGTH_LONG).show();
        makepatientbooking(timeID, datedmy);
        //editTextcal.setText(Year+"_"+ ((Month/10)+1)+"_"+Day);
        mCurrentDate.set(Year, ((Month+1)),Day);
        //   mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
        }
        }, year, month, day);
        mPickerDialog.show();
        //  }
        //  });
        ///***********************calender*********************************************
        return true;
        }
        });**/

        ppicuri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses image
                if(DoctorID.equals(uid)) {
                    displayImportImageDialog();}
            }
        });

    }

    private void openClenderAction(final String timeID , final int position) {
        ImageGenerator mImageGenerator = new ImageGenerator(DoctorProfileActivity.this);

// Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50, 50);

// Set the size of the date and month font in dip.
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

// Set the position of the date and month in dip.
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

// Set the color of the font to be generated
        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);

        // abookingphoto.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View v) {
        final Calendar mCurrentDate = Calendar.getInstance();
        int year=mCurrentDate.get(Calendar.YEAR);
        int month=mCurrentDate.get(Calendar.MONTH);
        int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mPickerDialog =  new DatePickerDialog(DoctorProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                String datedmy= Year+"_"+ (Month+1)+"_"+Day;
                Toast.makeText(DoctorProfileActivity.this, datedmy, Toast.LENGTH_LONG).show();
                // Toast.makeText(context, id+doctorID, Toast.LENGTH_LONG).show();
                makepatientbooking(timeID, datedmy, position);
                //editTextcal.setText(Year+"_"+ ((Month/10)+1)+"_"+Day);
                mCurrentDate.set(Year, ((Month+1)),Day);
                //   mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
            }
        }, year, month, day);
        mPickerDialog.show();
    }

    private void displayImportImageDialog() {

        final Dialog dialog = new Dialog(DoctorProfileActivity.this);
        dialog.setContentView(R.layout.import_image_dialog);
        dialog.setTitle("Import image from:");
        dialog.setCanceledOnTouchOutside(false);

        TextView gallery = (TextView) dialog.findViewById(R.id.gallery_tv);
        TextView openCamera = (TextView) dialog.findViewById(R.id.open_camera_tv);
        TextView cancel = (TextView) dialog.findViewById(R.id.dismiss_dialog);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openGalleryAction();
            }
        });

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openCameraAction();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }
    private void openCameraAction() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

    }

    private void openGalleryAction() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                if (data.getData() != null) {
                    imagePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                        Bitmap compressedBitmap = getScaledBitmap(bitmap);
                        ppicuri.setImageBitmap(compressedBitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                        byteImageData = baos.toByteArray();
                        uploadImage();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // addDoctorTextView.setEnabled(true);
                }

            } else if (requestCode == CAMERA_REQUEST_CODE) {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap compressedBitmap = getScaledBitmap(bitmap);
                ppicuri.setImageBitmap(compressedBitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byteImageData = baos.toByteArray();
                uploadImage();


            }
        }


    }


    private void uploadImage() {

        if (isNetworkConnected()) {

            if (byteImageData != null) {
                progressBarImage.setVisibility(View.VISIBLE);


                StorageReference trampsRef = mStorageRef.child( "homelesspic/" + System.currentTimeMillis() + ".jpg");

                // StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("profilepics/pro.jpg");


                trampsRef.putBytes(byteImageData)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBarImage.setVisibility(View.GONE);

                                mTrampPhotoUrl = taskSnapshot.getDownloadUrl().toString();
                                databaseDoctor.child(DoctorID).child("cUri").setValue(mTrampPhotoUrl);
                                if (!mTrampPhotoUrl.equals("")) {
                                    Log.v("Image","Upload end");
                                    Toast.makeText(DoctorProfileActivity.this, "Upload end", Toast.LENGTH_LONG).show();

                                }


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressBarImage.setVisibility(View.GONE);

                                Toast.makeText(DoctorProfileActivity.this, "an error occurred while  uploading image", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        } else {
            Toast.makeText(DoctorProfileActivity.this, "please check the network connection", Toast.LENGTH_LONG).show();
        }
    }

    private void makepatientbooking(final String timeID, final String datedmy, final int position) {

        /*************************************/
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String patientname = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("cname").getValue(String.class);
                String patientBirthday = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("cbirthday").getValue(String.class);

                BookingClass currentBooking = bookingList.get(position);
                String patientpic = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("cpatentphoto").getValue(String.class);
                if(patientpic != null){
                    picuri=patientpic;
                }else{picuri="https://firebasestorage.googleapis.com/v0/b/the-clinic-66fa1.appspot.com/o/user_logo_m.jpg?alt=media&token=ff53fa61-0252-43a4-8fa3-0eb3a3976ee5";}
                // Toast.makeText(DoctorProfileActivity.this, picuri, Toast.LENGTH_LONG).show();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mDate = sdf.format(calendar.getTime());

                ////to do/////////-------------------------------------------------------------
                DatabaseReference reference1 = databasetimeBooking.push();
                //final DatabaseReference databasetimeBooking = FirebaseDatabase.getInstance().getReference("bookingtimes").child(DoctorID).child(timeID).child(datedmy);
                // DatabaseReference reference = databasetimeBooking.push();
                String timesid = reference1.getKey();
                //Log.v("Data"," 2-User id :"+ mUserId);
                String userid = mAuth.getCurrentUser().getUid();
                // get age from birthday
                String patientAge = UtilClass.calculateAgeFromDate(patientBirthday);

                BookingTimesClass bookingtimesclass = new BookingTimesClass(userid, patientname, patientAge, mDate, currentBooking.getCbaddress(),currentBooking.getCbtime() , picuri);

                // Database for Account Activity
                databasetimeBooking.child(DoctorID).child(timeID)
                        .child(datedmy)
                        .child(timesid).setValue(bookingtimesclass);
                //////////////////////*******-----------------
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseUserReg .addValueEventListener(postListener);

        /*************************************/

    }
    ////////////////////////////////////////////
    private void editDialog(final String whatdata) {


        final Dialog dialog = new Dialog(DoctorProfileActivity.this);
        dialog.setContentView(R.layout.edit_data_dialig);
       // dialog.setTitle("Edit your data");
        dialog.setCanceledOnTouchOutside(false);

        final EditText editfield = (EditText) dialog.findViewById(R.id.edit_data_tv_e);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel_tv_e);
        TextView submit = (TextView) dialog.findViewById(R.id.submit_tv_e);
        editfield.setHint(whatdata);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editfield1 = editfield.getText().toString().trim();

                if (editfield1.isEmpty()) {
                    editfield.setError("Please fill the field");
                    editfield.requestFocus();
                    return;}
                getRegData(editfield1, whatdata);
                dialog.dismiss();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }
    private void getRegData(final String editfield1, final String whatdata) {

        if (whatdata.equals("Name")) {
            databaseDoctor.child(DoctorID).child("cName").setValue(editfield1);
        } else if (whatdata.equals("State/ City/ Region")) {
            databaseDoctor.child(DoctorID).child("cCity").setValue(editfield1);
        } else if (whatdata.equals("Specialty")) {
            databaseDoctor.child(DoctorID).child("cSpecialty").setValue(editfield1);
        } else if (whatdata.equals("Degree")) {
            databaseDoctor.child(DoctorID).child("cDegree").setValue(editfield1);
        } else if (whatdata.equals("Phone Number")) {
            databaseDoctor.child(DoctorID).child("cPhone").setValue(editfield1);
        } else if (whatdata.equals("Detection price")) {
            databaseDoctor.child(DoctorID).child("cPrice").setValue(editfield1);
        } else if (whatdata.equals("Average detection time in min")) {
            databaseDoctor.child(DoctorID).child("cTime").setValue(editfield1);
        }

        //**************************************************//
        // private void getallData();
        final ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                String DoctorName = dataSnapshot1.child(DoctorID).child("cName").getValue(String.class);
                String DoctorCity = dataSnapshot1.child(DoctorID).child("cCity").getValue(String.class);
                String DoctorSpecialty = dataSnapshot1.child(DoctorID).child("cSpecialty").getValue(String.class);
                String DoctorDegree = dataSnapshot1.child(DoctorID).child("cDegree").getValue(String.class);
                String DoctorPhone = dataSnapshot1.child(DoctorID).child("cPhone").getValue(String.class);
                String DoctorPrice = dataSnapshot1.child(DoctorID).child("cPrice").getValue(String.class);
                String DoctorTime = dataSnapshot1.child(DoctorID).child("cTime").getValue(String.class);
                if(DoctorName != null) {
                    pname.setText(DoctorName);
                }else{pname.setText("Name");}
                if(DoctorCity != null) {
                    pcity.setText(DoctorCity);
                }else{pcity.setText("State/ City/ Region");}
                if(DoctorSpecialty != null) {
                    pspeciality.setText(DoctorSpecialty);
                }else{pspeciality.setText("Specialty");}
                if(DoctorDegree != null) {
                    pdegree.setText(DoctorDegree);
                }else{pdegree.setText("Degree");}
                if(DoctorPhone != null) {
                    pphone.setText(DoctorPhone);
                }else{pphone.setText("Phone Number");}
                if(DoctorPrice != null) {
                    pprice.setText(DoctorPrice);
                }else{pprice.setText("Detection price");}
                if(DoctorTime != null) {
                    ptime.setText(DoctorTime+"min.");
                }else{ptime.setText("Not yet");}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseDoctor .addValueEventListener(postListener1);
    }
    /***-------------------------------------------------***/
    private void editDialogbook() {

        final DatabaseReference databaseBooking = FirebaseDatabase.getInstance().getReference("bookingdb").child(DoctorID);

        final Dialog dialog = new Dialog(DoctorProfileActivity.this);
        dialog.setContentView(R.layout.booking_data_dialig);
        dialog.setTitle("Edit your data");
        dialog.setCanceledOnTouchOutside(false);
        getLocation();
        Toast.makeText(this, address, Toast.LENGTH_LONG).show();

        dialogAddress = (EditText) dialog.findViewById(R.id.dialog_address);
        dialogAddress.setEnabled(true);
        dialogAddress.setText(address);
        dialogAddress.setEnabled(false);


        final EditText dialogTime = (EditText) dialog.findViewById(R.id.dialog_time);

        TextView cancel = (TextView) dialog.findViewById(R.id.cancel_tv_e);
        TextView submit = (TextView) dialog.findViewById(R.id.submit_tv_e);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getaddress = dialogAddress.getText().toString().trim();
                final String gettime = dialogTime.getText().toString().trim();

                if (getaddress.isEmpty()) {
                    dialogAddress.setError("Please fill the address");
                    dialogAddress.requestFocus();
                    return;}
                if (gettime.isEmpty()) {
                    dialogTime.setError("Please fill the times");
                    dialogTime.requestFocus();
                    return;}

                DatabaseReference reference = databaseBooking.push();
                String id = reference.getKey();
                //Log.v("Data"," 2-User id :"+ mUserId);
                BookingClass bookingclass = new BookingClass(id, gettime, getaddress,DoctorID,String.valueOf(latitude),String.valueOf(longitude));
                // BookingAdapter myAdapter = new BookingAdapter(DoctorProfileActivity.this, bookingList, id, DoctorID);
                // Database for Account Activity
                databaseBooking.child(id).setValue(bookingclass);

                dialog.dismiss();
                //to refresh activity as you need to go back activity and return
                finish();
                startActivity(getIntent());

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }
    protected void onStart() {
        super.onStart();
        progressBarBooking.setVisibility(View.VISIBLE);
        // getRegData();
        if (isNetworkConnected()) {
            final DatabaseReference databaseBooking = FirebaseDatabase.getInstance().getReference("bookingdb").child(DoctorID);

            //databaseTramp.child(country).child("Individual").child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            databaseBooking.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bookingList.clear();
                    for(DataSnapshot doctorSnapshot: dataSnapshot.getChildren()){
                        BookingClass bookingclass=doctorSnapshot.getValue(BookingClass.class);
                        bookingList.add(0,bookingclass);// i= 0  (index)to start from top



                    }
                    // }
                    //}
                    BookingAdapter adapter = new BookingAdapter(DoctorProfileActivity.this, bookingList);
                    //adapter.notifyDataSetChanged();
                    listViewBooking.setAdapter(adapter);
                    progressBarBooking.setVisibility(View.GONE);
                    // listViewTramp.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


        } else {
            Toast.makeText(DoctorProfileActivity.this, "please check the network connection", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return true;
        } else {
            return false;
        }
    }

    private void getallData() {

        //**************************************************//
        // private void getallData();
        final ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                String DoctorName = dataSnapshot1.child(DoctorID).child("cName").getValue(String.class);
                String DoctorCity = dataSnapshot1.child(DoctorID).child("cCity").getValue(String.class);
                String DoctorSpecialty = dataSnapshot1.child(DoctorID).child("cSpecialty").getValue(String.class);
                String DoctorDegree = dataSnapshot1.child(DoctorID).child("cDegree").getValue(String.class);
                String DoctorPhone = dataSnapshot1.child(DoctorID).child("cPhone").getValue(String.class);
                String DoctorPrice = dataSnapshot1.child(DoctorID).child("cPrice").getValue(String.class);
                String DoctorTime = dataSnapshot1.child(DoctorID).child("cTime").getValue(String.class);
                String DoctorAbout = dataSnapshot1.child(DoctorID).child("cAbout").getValue(String.class);
                String DoctorPic = dataSnapshot1.child(DoctorID).child("cUri").getValue(String.class);


                if(DoctorName != null) {
                    pname.setText(DoctorName);
                }else{pname.setText("Name");}
                if(DoctorCity != null) {
                    pcity.setText(DoctorCity);
                }else{pcity.setText("State/ City/ Region");}
                if(DoctorSpecialty != null) {
                    pspeciality.setText(DoctorSpecialty);
                }else{pspeciality.setText("Specialty");}
                if(DoctorDegree != null) {
                    pdegree.setText(DoctorDegree);
                }else{pdegree.setText("Degree");}
                if(DoctorPhone != null) {
                    pphone.setText(DoctorPhone);
                }else{pphone.setText("Phone Number");}
                if(DoctorPrice != null) {
                    pprice.setText(DoctorPrice+"$");
                }else{pprice.setText("Detection price");}
                if(DoctorTime != null) {
                    ptime.setText(DoctorTime+"min.");
                }else{ptime.setText("Not yet");}
                if(DoctorAbout != null) {
                    peditbox.setText(DoctorAbout);
                }
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new RoundedCorners(16));
                if(DoctorPic != null) {
                    Glide.with(DoctorProfileActivity.this)
                            .load(DoctorPic)
                            .apply(requestOptions)
                            .into(ppicuri);
                }else{
                    Glide.with(DoctorProfileActivity.this)
                            .load("https://firebasestorage.googleapis.com/v0/b/the-clinic-66fa1.appspot.com/o/doctor_logo_m.jpg?alt=media&token=d3108b95-4e16-4549-99b6-f0fa466e0d11")
                            .apply(requestOptions)
                            .into(ppicuri);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseDoctor .addValueEventListener(postListener1);
    }


    //resize image
    private Bitmap getScaledBitmap(Bitmap bm) {

        int width = 0;

        try {
            width = bm.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bm.getHeight();
        int bounding = dpToPx(250);

        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ppicuri.getLayoutParams();
        params.width = width;
        params.height = height;
        ppicuri.setLayoutParams(params);

        return scaledBitmap;

    }

    // convert dp to pixel
    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    //-----------------------------add Gps----------------------------------
    /**
     * Request the Location Permission
     */
    private void requestPermission(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, theRequestCodeForLocation);
        }
        else{
            isPermissionGranted = true;
        }

    }

    /**
     * this method gets the location of the user then
     * Calls the showAddress Method and pass to it the Latitude
     * and the Longitude
     */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, theRequestCodeForLocation);
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    showAddress(latitude,longitude);
                }else{
                    Toast.makeText(DoctorProfileActivity.this, "Error we didn't get the Location\n Please try again after Few seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * this method shows the User's address to the screen, it calls the getAddress which returns a string
     * contains the address then changes the TextView text to it.
     * @param latitude is the latitude of the location
     * @param longitude is the longitude of the location
     */
    private void showAddress(double latitude, double longitude){
        String msg = "";

        try {
            msg = getAddress(latitude, longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //pcity.setText(msg);
//
    }


    /**
     * this method is called by android system after we request a permission
     * and the system pass the result of our request to this method so we can check if we got
     * the permission or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch(requestCode){
            case theRequestCodeForLocation:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    isPermissionGranted = true;
                }else{
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                    isPermissionGranted = false;
                }
                break;
        }

    }

    /**
     * this method takes the longitude and latitude of the location then convert them into real address
     * and return it as string
     * @param latitude the Latitude
     * @param longitude the Longitude
     * @return the address as String
     * @throws IOException
     */
    private String getAddress(double latitude, double longitude) throws IOException {

        //Geocoder class helps us to convert longitude and latitude into Address
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;
        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        address =  addresses.get(0).getAddressLine(0);
        String city = "City: " + addresses.get(0).getLocality();
        String state = "State:" + addresses.get(0).getAdminArea();
        String country = "Country: " + addresses.get(0).getCountryName();
       // String wholeAddress = address + "\n" + city + "\n" + state + "\n" + country;
        String wholeAddress = address ;
       // pcity.setText(address);

        return  wholeAddress;

    }


}
