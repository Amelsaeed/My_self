package com.example.ahmedmagdy.theclinic.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ahmedmagdy.theclinic.Adapters.DoctorAdapter;
import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.DoctorFirebaseClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {
    ImageView favDoctorButton;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference databaseDoctorFav;
    private DatabaseReference databaseUserReg;


    SearchView searchView;
     TextView usernamef;
    private ProgressBar progressBar;

    ListView listViewDoctor;
    private List<DoctorFirebaseClass> doctorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);



        favDoctorButton = (ImageView) findViewById(R.id.alldoctor);
        usernamef=findViewById(R.id.user_name);
        progressBar = (ProgressBar) findViewById(R.id.fav_progress_bar);
        mAuth = FirebaseAuth.getInstance();

        databaseDoctorFav = FirebaseDatabase.getInstance().getReference("Favourits")
                .child(mAuth.getCurrentUser().getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference("Photos");
        databaseUserReg = FirebaseDatabase.getInstance().getReference("user_data");

        listViewDoctor= (ListView)findViewById(R.id.list_view_fav);
        searchView = (SearchView) findViewById(R.id.searchfav);
        doctorList=new ArrayList<>();
        listViewDoctor.setTextFilterEnabled(true);
        removeFocus();

        favDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent it = new Intent(FavActivity.this, UserProfileActivity.class);
                startActivity(it);
            }
        });
        getusername();
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        maketable();
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

    private void maketable() {

        if (isNetworkConnected()) {
            //   if(country != null &&  type != null) {


            /**  if (type.equals("User")){
             addTrampButton.setVisibility(View.GONE);
             } else {
             addTrampButton.setVisibility(View.VISIBLE);
             }**/
            //databaseTramp.child(country).child("Individual").child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            databaseDoctorFav.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    doctorList.clear();
                    for(DataSnapshot doctorSnapshot: dataSnapshot.getChildren()){
                        DoctorFirebaseClass doctorclass=doctorSnapshot.getValue(DoctorFirebaseClass.class);
                        doctorList.add(0,doctorclass);// i= 0  (index)to start from top



                    }
                    // }
                    //}
                    DoctorAdapter adapter = new DoctorAdapter(FavActivity.this, doctorList);
                    //adapter.notifyDataSetChanged();
                    listViewDoctor.setAdapter(adapter);
                    setupSearchView();
                    progressBar.setVisibility(View.GONE);
                    // listViewTramp.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
        /**  } else {
         Toast.makeText(AllDoctorActivity.this, "please check the network connection", Toast.LENGTH_LONG).show();
         }**/
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    listViewDoctor.clearTextFilter();
                } else {
                    listViewDoctor.setFilterText(newText);
                }
                return true;
            }
        });
    }

    private void removeFocus() {
        searchView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }
    private void getusername() {


        final ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                String UserName = dataSnapshot1.child(mAuth.getCurrentUser().getUid()).child("cname").getValue(String.class);


                if(UserName != null) {
                    usernamef.setText(UserName);
                }else{usernamef.setText("Name");}


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseUserReg .addValueEventListener(postListener1);
    }
    @Override
    public void onBackPressed() {
        Intent it = new Intent(FavActivity.this, AllDoctorActivity.class);
        startActivity(it);
    }
}
