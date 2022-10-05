package com.example.iot_bikelock_mobileapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iot_bikelock_mobileapplication.R;

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
                    startActivity(new Intent(MainActivity.this, ScanActivity.class));
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