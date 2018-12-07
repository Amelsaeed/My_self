package com.example.ahmedmagdy.theclinic.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedmagdy.theclinic.Notifications.Token;
import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.DoctorFirebaseClass;
import com.example.ahmedmagdy.theclinic.classes.RegisterClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;

import java.io.IOException;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements OnRequestPermissionsResultCallback{
    private ImageView callogo;

    private TextView singIn, signUp;
    private EditText editTextEmail, editTextPassword, editTextCPassword,editTextAddress,editTextName, editTextcal,specialtyEditText,editTextPhone;
    private ProgressBar progressBar;
    private Spinner spinnerCountry, spinnerType;
    DatabaseReference databaseUserReg;
    DatabaseReference databaseDoctor;
    DatabaseReference databaseChat;
    //DatabaseReference databaseDoctorReg;
    FirebaseAuth mAuth;

    final int theRequestCodeForLocation = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    Boolean isPermissionGranted;
    String caltext;
    Calendar mCurrentDate;
    int year,month,day;
    String mDate;
    FirebaseUser fuser;

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
        databaseChat = FirebaseDatabase.getInstance().getReference("ChatRoom");
//        updateToken(FirebaseInstanceId.getInstance().getToken());


        editTextPhone = findViewById(R.id.edit_phone);
        callogo = findViewById(R.id.calender_logo);

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
        final LinearLayout linearAddress=(LinearLayout)this.findViewById(R.id.linaddress);


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

        callogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate = Calendar.getInstance();
                year=mCurrentDate.get(Calendar.YEAR);
                month=mCurrentDate.get(Calendar.MONTH);
                day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
                //final String abc= getAge(year, month, day);

                DatePickerDialog mPickerDialog =  new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        caltext=Year+"_"+ (Month+1)+"_"+Day;
                        editTextcal.setText(caltext);
                        //  Toast.makeText(RegisterActivity.this,"Your age= "+abc, Toast.LENGTH_LONG).show();

                        mCurrentDate.set(Year, (Month+1),Day);
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
/**
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
                Toast.makeText(RegisterActivity.this, "Please Give us permission so you can use the app", Toast.LENGTH_SHORT).show();
            }
        }
        **/
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
                    linearAddress.setVisibility(LinearLayout.GONE);




                } else if (mtype.equalsIgnoreCase("Doctor")) {
                    // editTextcal.setVisibility(View.GONE);
                    linearCalender.setVisibility(LinearLayout.GONE);
                    linearSpecialty.setVisibility(LinearLayout.VISIBLE);
                    linearAddress.setVisibility(LinearLayout.VISIBLE);




                } else {// (mtype .equalsIgnoreCase ("Hospital") )
                    // editTextcal.setVisibility(View.GONE);
                    linearCalender.setVisibility(LinearLayout.GONE);
                    linearSpecialty.setVisibility(LinearLayout.VISIBLE);
                    linearAddress.setVisibility(LinearLayout.VISIBLE);




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                linearSpecialty.setVisibility(LinearLayout.GONE);
                linearCalender.setVisibility(LinearLayout.VISIBLE);
                linearAddress.setVisibility(LinearLayout.GONE);

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
        //final String mBirthDayCalender = editTextcal.getText().toString().trim();caltext
        Toast.makeText(RegisterActivity.this, mBirthDayCalender, Toast.LENGTH_SHORT).show();


        if (mName.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if (mtype.equalsIgnoreCase("Doctor")  ||  mtype.equalsIgnoreCase("Hospital")) {
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
        if (mtype.equalsIgnoreCase("Doctor")  ||  mtype.equalsIgnoreCase("Hospital")) {
            if (mCity.isEmpty()) {
                editTextAddress.setError("City is required");
                editTextAddress.requestFocus();
                return;
            }
        }
        if (mtype.equalsIgnoreCase("User")) {
            if (mBirthDayCalender.isEmpty()) {
                editTextcal.setError("Birthdar is required");
                editTextcal.requestFocus();
                return;
            }
        }

        if (mtype.equalsIgnoreCase("Doctor")  ||  mtype.equalsIgnoreCase("Hospital")) {
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
                       /* updateToken(FirebaseInstanceId.getInstance().getToken());*/
                        Toast.makeText(RegisterActivity.this, "USER CREATED", Toast.LENGTH_SHORT).show();
                        String Id = mAuth.getCurrentUser().getUid();

                        RegisterClass usersChat = new RegisterClass(Id,mName, mPhone, mCity, mEmail, mtype);
                        databaseChat.child(Id).setValue(usersChat);

                        if (mtype.equalsIgnoreCase("User")) {

                            RegisterClass regdatauser = new RegisterClass(Id,mName, mPhone, mCity,mBirthDayCalender , mEmail, mtype);
                            databaseUserReg.child(mAuth.getCurrentUser().getUid()).setValue(regdatauser);

                        }else {
                            // DatabaseReference reference = databaseDoctor.push();
                            // String id = reference.getKey();

                            DoctorFirebaseClass doctorfirebaseclass = new DoctorFirebaseClass(Id,mName, mPhone, mCity, mSpecialty, mEmail, mtype);
                            databaseDoctor.child(Id).setValue(doctorfirebaseclass);
                            // databaseDoctorReg.child(mAuth.getCurrentUser().getUid()).setValue(regdatadoctor);
                        }
                        Intent intend = new Intent(RegisterActivity.this, SplashActivity.class);
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
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }
    //-----------------------------add Gps----------------------------------
    /**
     * Request the Location Permission
     */

    /**
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, theRequestCodeForLocation);
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    showAddress(latitude,longitude);
                }else{
                    Toast.makeText(RegisterActivity.this, "Error we didn't get the Location\n Please try again after Few seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * this method shows the User's address to the screen, it calls the getAddress which returns a string
     * contains the address then changes the TextView text to it.
     * @param latitude is the latitude of the location
     * @param longitude is the longitude of the location

    private void showAddress(double latitude, double longitude){
        String msg = "";

        try {
            msg = getAddress(latitude, longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editTextAddress.setText(msg);

    }


    /**
     * this method is called by android system after we request a permission
     * and the system pass the result of our request to this method so we can check if we got
     * the permission or not

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

    private String getAddress(double latitude, double longitude) throws IOException {

        //Geocoder class helps us to convert longitude and latitude into Address
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;
        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        String address = "Address : " + addresses.get(0).getAddressLine(0);
        String city = "City: " + addresses.get(0).getLocality();
        String state = "State:" + addresses.get(0).getAdminArea();
        String country = "Country: " + addresses.get(0).getCountryName();
       // String wholeAddress = address + "\n" + city + "\n" + state + "\n" + country;

        String wholeAddress = address ;
        return  wholeAddress;

    }**/

}