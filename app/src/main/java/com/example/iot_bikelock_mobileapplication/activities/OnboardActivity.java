package com.example.iot_bikelock_mobileapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_bikelock_mobileapplication.R;
import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnboardActivity extends AppCompatActivity implements View.OnClickListener {

    public ViewPager mSlideViewPager;
    public Button mNextButton, mBackButton, mSkipButton, mStart;
    public LinearLayout mDotLayout;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started);

        mNextButton = findViewById(R.id.nextButton);
        mBackButton = findViewById(R.id.backButton);
        mSkipButton = findViewById(R.id.skipButton);
        mStart = findViewById(R.id.startButton);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout);

        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mSkipButton.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mStart.setVisibility(View.GONE);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);
        mDotLayout.setBackgroundColor(Color.parseColor("#00000000"));

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSlideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextButton:
                if (getitem(0) < 3)
                    mSlideViewPager.setCurrentItem(getitem(1),true);
                else {
                    Intent i = new Intent(OnboardActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
            case R.id.backButton:
                if (getitem(0) > 0){
                    mSlideViewPager.setCurrentItem(getitem(-1),true);
                }
                break;
            case R.id.skipButton:
            case R.id.startButton:
                Intent i = new Intent(OnboardActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setUpindicator(int position){

        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setBackgroundColor(Color.TRANSPARENT);
            dots[i].setTextSize(35);
            dots[i].setBackgroundColor(Color.TRANSPARENT);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position == 0){
                mBackButton.setVisibility(View.INVISIBLE);
                mStart.setVisibility(View.INVISIBLE);
            }
            if (position == 1 ){
                mNextButton.setVisibility(View.VISIBLE);
                mBackButton.setVisibility(View.VISIBLE);
                mStart.setVisibility(View.INVISIBLE);
            }
            if (position == 2 ){
                mNextButton.setVisibility(View.INVISIBLE);
                mBackButton.setVisibility(View.VISIBLE);
                mStart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i) {

        return mSlideViewPager.getCurrentItem() + 1;
    }
}