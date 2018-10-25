package com.example.ahmedmagdy.theclinic.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.ahmedmagdy.theclinic.Adapters.DoctorAdapter;
import com.example.ahmedmagdy.theclinic.Adapters.BookingAdapter;
import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.BookingClass;
import com.example.ahmedmagdy.theclinic.classes.BookingTimesClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DoctorProfileActivity extends AppCompatActivity {
    ImageView ppicuri;
    TextView pname,pcity,pspeciality,pdegree,pphone,pprice,ptime,pedit1,pedit2,pedit3,pedit4,pedit5,pedit6,pedit7,paddbook;
    EditText peditbox;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference databaseDoctor;
    private DatabaseReference databaseReg;
    String type,country;
    String DoctorID;
    ListView listViewBooking;
    private List<BookingClass> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        mAuth = FirebaseAuth.getInstance();

        databaseDoctor = FirebaseDatabase.getInstance().getReference("Doctordb");
        mStorageRef = FirebaseStorage.getInstance().getReference("Photos");
        databaseReg = FirebaseDatabase.getInstance().getReference("reg_data");

        progressBar = (ProgressBar) findViewById(R.id.booking_progress_bar);
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


        Intent intent = getIntent();
        DoctorID = intent.getStringExtra("DoctorID");
        String DoctorName = intent.getStringExtra("DoctorName");
        String DoctorCity = intent.getStringExtra("DoctorCity");
        String DoctorSpecialty = intent.getStringExtra("DoctorSpecialty");
        String DoctorUri = intent.getStringExtra("DoctorUri");

        // databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cAbout").setValue(about);

    pname.setText(DoctorName);
    pcity.setText(DoctorCity);
    pspeciality.setText(DoctorSpecialty);
        pdegree.setText("Degree");
        pphone.setText("Phone No.");
        pprice.setText("Price");
        ptime.setText("20 min.");




        Glide.with(DoctorProfileActivity.this)
                .load(DoctorUri)
                .into(ppicuri);

        getallData();


        pname.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                String whatdata = "Name";
                editDialog(whatdata);
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
                String whatdata = "State/ City";
                editDialog(whatdata);
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
                String whatdata = "Specialty";
                editDialog(whatdata);
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
                String whatdata = "Degree";
                editDialog(whatdata);
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
                String whatdata = "Phone Number";
                editDialog(whatdata);
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
                String whatdata = "Detection price";
                editDialog(whatdata);
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
                String whatdata = "Average detection time in min";
                editDialog(whatdata);
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

        peditbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            final String about1 = peditbox.getText().toString().trim();

                final ValueEventListener postListener2 = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot5) {

                        type = dataSnapshot5.child(mAuth.getCurrentUser().getUid()).child("ctype").getValue(String.class);
                        country = dataSnapshot5.child(mAuth.getCurrentUser().getUid()).child("ccountry").getValue(String.class);
                        // maketable();
                     databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cAbout").setValue(about1);
                      // databaseDoctor.child("Egypt").child("User").child("users").child("-LPNTQiOo29mHsGHlXtR").child("cAbout").setValue(about);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                };
                databaseReg.addValueEventListener(postListener2);
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
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                BookingClass bookingclass = bookingList.get(position);
               final String timeID= bookingclass.getCbid();
                Toast.makeText(DoctorProfileActivity.this, timeID, Toast.LENGTH_LONG).show();

                ///***********************calender***********************************************//
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
/**
                abookingphoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {**/
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
                ///***********************calender***********************************************//


                return true;
            }
        });

    }

    private void makepatientbooking(final String timeID, final String datedmy) {

        /*************************************/
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String patientname = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("cname").getValue(String.class);
                String patientage = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("cBirthDay").getValue(String.class);
                ////to do/////////-------------------------------------------------------------
                final DatabaseReference databasetimeBooking = FirebaseDatabase.getInstance().getReference("bookingtimes").child(DoctorID).child(timeID).child(datedmy);
                DatabaseReference reference = databasetimeBooking.push();
                String timesid = reference.getKey();
                //Log.v("Data"," 2-User id :"+ mUserId);
                BookingTimesClass bookingtimesclass = new BookingTimesClass(timesid, patientname, patientage);

                // Database for Account Activity
                databaseDoctor.child(DoctorID).child(timeID)
                        .child(datedmy)
                        .child(timesid).setValue(bookingtimesclass);
                //////////////////////*******-----------------
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReg .addValueEventListener(postListener);
        /*************************************/

    }
////////////////////////////////////////////
    private void editDialog(final String whatdata) {

        // databaseDoctor.child("Egypt").child("User").child("users").child(DoctorID).child("cName").setValue("fathy");

            final Dialog dialog = new Dialog(DoctorProfileActivity.this);
            dialog.setContentView(R.layout.edit_data_dialig);
            dialog.setTitle("Edit your data");
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


        ////import data of country and tope
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                type = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ctype").getValue(String.class);
                country = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ccountry").getValue(String.class);
                // maketable();
                if (whatdata.equals("Name")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cName").setValue(editfield1);
                } else if (whatdata.equals("State/ City")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cCity").setValue(editfield1);
                } else if (whatdata.equals("Specialty")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cSpecialty").setValue(editfield1);
                } else if (whatdata.equals("Degree")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cDegree").setValue(editfield1);
                } else if (whatdata.equals("Phone Number")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cPhone").setValue(editfield1);
                } else if (whatdata.equals("Detection price")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cPrice").setValue(editfield1);
                } else if (whatdata.equals("Average detection time in min")) {
                    databaseDoctor.child(country).child(type).child("users").child(DoctorID).child("cTime").setValue(editfield1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReg .addValueEventListener(postListener);
        //**************************************************//
       // private void getallData();
        final ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                String DoctorName = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cName").getValue(String.class);
                String DoctorCity = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cCity").getValue(String.class);
                String DoctorSpecialty = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cSpecialty").getValue(String.class);
                String DoctorDegree = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cDegree").getValue(String.class);
                String DoctorPhone = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cPhone").getValue(String.class);
                String DoctorPrice = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cPrice").getValue(String.class);
                String DoctorTime = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cTime").getValue(String.class);
                if(DoctorName != null) {
                    pname.setText(DoctorName);
                }else{pname.setText("Name");}
                if(DoctorCity != null) {
                    pcity.setText(DoctorCity);
                }else{pcity.setText("State/ City");}
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

    final EditText dialogAddress = (EditText) dialog.findViewById(R.id.dialog_address);
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
            BookingClass bookingclass = new BookingClass(id, gettime, getaddress,DoctorID);
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
        progressBar.setVisibility(View.VISIBLE);
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
                        progressBar.setVisibility(View.GONE);
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


        ////import data of country and tope
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                type = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ctype").getValue(String.class);
                country = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ccountry").getValue(String.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReg .addValueEventListener(postListener);
        //**************************************************//
        // private void getallData();
        final ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                String DoctorName = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cName").getValue(String.class);
                String DoctorCity = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cCity").getValue(String.class);
                String DoctorSpecialty = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cSpecialty").getValue(String.class);
                String DoctorDegree = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cDegree").getValue(String.class);
                String DoctorPhone = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cPhone").getValue(String.class);
                String DoctorPrice = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cPrice").getValue(String.class);
                String DoctorTime = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cTime").getValue(String.class);
                String DoctorAbout = dataSnapshot1.child(country).child(type).child("users").child(DoctorID).child("cAbout").getValue(String.class);

                if(DoctorName != null) {
                   pname.setText(DoctorName);
               }else{pname.setText("Name");}
                if(DoctorCity != null) {
                pcity.setText(DoctorCity);
                }else{pcity.setText("State/ City");}
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
                if(DoctorAbout != null) {
                    peditbox.setText(DoctorAbout);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseDoctor .addValueEventListener(postListener1);
    }

}
