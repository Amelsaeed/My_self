package com.example.ahmedmagdy.theclinic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmedmagdy.theclinic.R;
public class AddDoctorActivity extends AppCompatActivity {
    TextView addDoctorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        addDoctorButton = (TextView) findViewById(R.id.getstarted);
        addDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent it = new Intent(AddDoctorActivity.this, DoctorProfileActivity.class);
                startActivity(it);
            }
        });
    }
}
