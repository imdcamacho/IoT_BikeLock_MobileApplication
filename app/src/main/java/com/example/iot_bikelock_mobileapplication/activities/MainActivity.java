package com.example.iot_bikelock_mobileapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iot_bikelock_mobileapplication.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button statusButton = findViewById(R.id.buttonStatus);
        Button slotsButton = findViewById(R.id.buttonSlots);
        Button scanButton = findViewById(R.id.buttonScan);

        statusButton.setOnClickListener(this);
        slotsButton.setOnClickListener(this);
        scanButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonScan:
//
                IntentIntegrator intentIntegrator= new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
                break;
            case R.id.buttonSlots:
                    startActivity(new Intent(MainActivity.this, BikeSlotsActivity.class));
                break;
            case R.id.buttonStatus:
                    startActivity(new Intent(MainActivity.this, StatusActivity.class));
                break;
        }
    }
}