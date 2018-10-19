package com.example.ahmedmagdy.theclinic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmedmagdy.theclinic.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timer =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);//5000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }) ;
        timer.start();
    }
}
