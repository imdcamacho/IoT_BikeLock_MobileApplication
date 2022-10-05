package com.example.iot_bikelock_mobileapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iot_bikelock_mobileapplication.R;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Button unlockButton = findViewById(R.id.buttonUnlock);
        Button closeButton = findViewById(R.id.buttonClose);

        unlockButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonUnlock:
                break;
            case R.id.buttonClose:
                    Intent intent = new Intent(StatusActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                break;
        }
    }
}