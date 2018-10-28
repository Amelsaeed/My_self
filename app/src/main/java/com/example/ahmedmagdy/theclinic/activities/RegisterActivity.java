package com.example.ahmedmagdy.theclinic.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.DoctorFirebaseClass;
import com.example.ahmedmagdy.theclinic.classes.RegisterClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements LocationListener{
    private TextView shelter;
    private TextView singIn, signUp;
    private EditText editTextEmail, editTextPassword, editTextCPassword,editTextAddress,editTextName, editTextcal,specialtyEditText,editTextPhone;
    private ProgressBar progressBar;
    private Spinner spinnerCountry, spinnerType;
    DatabaseReference databaseUserReg;
    DatabaseReference databaseDoctor;
    //DatabaseReference databaseDoctorReg;
    FirebaseAuth mAuth;
    LocationManager locationManager;
   // String mBirthDayCalender,mSpecialty;
    Calendar mCurrentDate;
//Bitmap mgeneratedateicon;
//ImageGenerator imageGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseUserReg = FirebaseDatabase.getInstance().getReference("user_data");
        //databaseDoctorReg = FirebaseDatabase.getInstance().getReference("doctor_data");
        databaseDoctor = FirebaseDatabase.getInstance().getReference("Doctordb");



        editTextPhone = findViewById(R.id.edit_phone);

        editTextEmail = findViewById(R.id.edit_email);
        editTextPassword = findViewById(R.id.edit_password);
        editTextCPassword = findViewById(R.id.edit_c_password);
        editTextAddress = findViewById(R.id.edit_city);
        editTextName = findViewById(R.id.edit_name);
        signUp = findViewById(R.id.getstarted);
        singIn = (TextView) findViewById(R.id.login);

        editTextName= findViewById(R.id.edit_name);
        editTextcal= findViewById(R.id.calender);
        specialtyEditText = (EditText) findViewById(R.id.specialty_reg);
        final LinearLayout linearCalender=(LinearLayout)this.findViewById(R.id.linear_calender);
        final LinearLayout linearSpecialty=(LinearLayout)this.findViewById(R.id.linear_specialty);


        ///////////////Calender//////////////////

        // Create an object of ImageGenerator class in your activity
// and pass the context as the parameter
        ImageGenerator mImageGenerator = new ImageGenerator(this);

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

        editTextcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate = Calendar.getInstance();
                int year=mCurrentDate.get(Calendar.YEAR);
                int month=mCurrentDate.get(Calendar.MONTH);
                int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mPickerDialog =  new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        editTextcal.setText(Year+"_"+ ((Month/10)+1)+"_"+Day);
                        mCurrentDate.set(Year, ((Month/10)+1),Day);
                        //   mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                    }
                }, year, month, day);
                mPickerDialog.show();
            }
        });///////////////////*Calender////////////////////---------------------

        spinnerCountry = findViewById(R.id.spinner_country);
        spinnerType = findViewById(R.id.spinner_type);

        progressBar = findViewById(R.id.progressbar);


        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(it);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        //--------Gps---------------------

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        getLocation();
        //--------------------------------------
/**
// spinner for countries
        ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(
                RegisterActivity.this, R.array.countries_array, android.R.layout.simple_spinner_item);
        adapterc.setDropDownViewResource(R.layout.spinner_list_item);
        spinnerCountry.setAdapter(adapterc);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });**/

        // spinner for type
        ArrayAdapter<CharSequence> adaptert = ArrayAdapter.createFromResource(
                RegisterActivity.this, R.array.type_array, android.R.layout.simple_spinner_item);
        adaptert.setDropDownViewResource(R.layout.spinner_list_item);
        spinnerType.setAdapter(adaptert);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
             String mtype = spinnerType.getSelectedItem().toString().trim();
                if (mtype.equalsIgnoreCase("User")) {
                   // specialtyEditText.setVisibility(View.GONE);
                    linearSpecialty.setVisibility(LinearLayout.GONE);
                    linearCalender.setVisibility(LinearLayout.VISIBLE);




                } else if (mtype.equalsIgnoreCase("Doctor")) {
                   // editTextcal.setVisibility(View.GONE);
                    linearCalender.setVisibility(LinearLayout.GONE);
                    linearSpecialty.setVisibility(LinearLayout.VISIBLE);



                } else {// (mtype .equalsIgnoreCase ("Hospital") )
                   // editTextcal.setVisibility(View.GONE);
                    linearCalender.setVisibility(LinearLayout.GONE);
                    linearSpecialty.setVisibility(LinearLayout.VISIBLE);



                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                linearSpecialty.setVisibility(LinearLayout.GONE);
                linearCalender.setVisibility(LinearLayout.VISIBLE);
            }
        });
    }


    private void registerUser() {
        final String mEmail = editTextEmail.getText().toString().trim();
        String mPassword = editTextPassword.getText().toString().trim();
        String mCPassword = editTextCPassword.getText().toString().trim();
        final String mPhone = editTextPhone.getText().toString().trim();
        final String mName = editTextName.getText().toString().trim();
        final String mCity = editTextAddress.getText().toString().trim();
        final String mBirthDayCalender = editTextcal.getText().toString().trim();
        final String mSpecialty = specialtyEditText.getText().toString().trim();
        //final String mCountry = spinnerCountry.getSelectedItem().toString().trim();
        final String mtype = spinnerType.getSelectedItem().toString().trim();


        if (mName.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if (mtype.equalsIgnoreCase("Doctor")  ||  mtype.equalsIgnoreCase("Doctor")) {
            if (mPhone.isEmpty()) {
                editTextPhone.setError("Phone is required");
                editTextPhone.requestFocus();
                return;
            }
        }


        if (mEmail.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (mPassword.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if (mPassword.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (mCPassword.isEmpty()) {
            editTextCPassword.setError("you should confirm your password");
            editTextCPassword.requestFocus();
            return;
        }

        if (!mCPassword.equals(mPassword)) {
            editTextCPassword.setError("it must be the same as password");
            editTextCPassword.requestFocus();
            return;
        }
        if (mCity.isEmpty()) {
            editTextAddress.setError("City is required");
            editTextAddress.requestFocus();
            return;
        }
        if (mtype.equalsIgnoreCase("User")) {
            if (mBirthDayCalender.isEmpty()) {
                editTextcal.setError("Birthdar is required");
                editTextcal.requestFocus();
                return;
            }
        }

        if (mtype.equalsIgnoreCase("Doctor")  ||  mtype.equalsIgnoreCase("Doctor")) {
            if (mSpecialty.isEmpty()) {
                specialtyEditText.setError("Specialty is required");
                specialtyEditText.requestFocus();
                return;
            }
        }
        progressBar.setVisibility(View.VISIBLE);
        if (isNetworkConnected()) {
            mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "USER CREATED", Toast.LENGTH_SHORT).show();
                        if (mtype.equalsIgnoreCase("User")) {
                            RegisterClass regdatauser = new RegisterClass(mName, mPhone, mCity, mBirthDayCalender, mEmail, mtype);
                            databaseUserReg.child(mAuth.getCurrentUser().getUid()).setValue(regdatauser);

                        }else {
                            DatabaseReference reference = databaseDoctor.push();
                            String id = reference.getKey();
                            DoctorFirebaseClass doctorfirebaseclass = new DoctorFirebaseClass(id,mName, mPhone, mCity, mSpecialty, mEmail, mtype);
                            databaseDoctor.child(id).setValue(doctorfirebaseclass);
                           // databaseDoctorReg.child(mAuth.getCurrentUser().getUid()).setValue(regdatadoctor);
                        }
                         Intent intend = new Intent(RegisterActivity.this, AllDoctorActivity.class);
                         intend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         finish();
                         startActivity(intend);

                    } else {
                        //Log.e(TAG, task.getException().getMessage());
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(RegisterActivity.this, "you are already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "REGISTER ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "please check the network connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    //  check if network is connected
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return true;
        } else {
            return false;
        }
    }
//-----------------------------add Gps----------------------------------
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        //  City.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            Log.v("Image1",address);
            Log.v("Image2",city);
            Log.v("Image3",state);
            Log.v("Image4",country);

            editTextAddress.setText(address);

            // editTextAddress.setText(addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
            //Toast.makeText(RegisterActivity.this, addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();


        }catch(Exception e)
        {

        }

    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(RegisterActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

}

