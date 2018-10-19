package com.example.ahmedmagdy.theclinic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ahmedmagdy.theclinic.R;

public class AllDoctorActivity extends AppCompatActivity {
    ImageView addDoctorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctor);

        addDoctorButton = (ImageView) findViewById(R.id.adddoctor);
        addDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent it = new Intent(AllDoctorActivity.this, AddDoctorActivity.class);
                startActivity(it);
            }
        });
    }
}
