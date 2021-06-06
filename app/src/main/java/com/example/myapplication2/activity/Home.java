package com.example.myapplication2.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication2.R;
import com.example.myapplication2.adapter.ViewPagerAdapter;
import com.example.myapplication2.handler.AlarmHandler;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Home extends AppCompatActivity {
    ViewPager mViewPager;
    ImageView btn_Search;
    public static EditText txt_Search;
    ViewPagerAdapter adapter;
    ViewFlipper mFlipper;
    RelativeLayout home;
    private static boolean isOpen = true;
    private TabLayout mTabLayout;
    public static boolean isLastUpdate;
    public static int position;
    public static Date lastTime = new Date();
    public final static String patternSimpleDateFormat = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setControl();
        setDefault();
        setFlipper();
        setData();
        setEvent();

        AlarmHandler handler = new AlarmHandler(Home.this);
        handler.cancelAlarmManager();
        handler.setAlarmManager();
    }

    private void setDefault() {
        txt_Search.setVisibility(View.GONE);
        isOpen = false;
    }

    private void setFlipper() {
        ImageView img = new ImageView(this);
        img.setBackgroundResource(R.drawable.nguyenton1);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageView img1 = new ImageView(this);
        img1.setBackgroundResource(R.drawable.view);
        img1.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageView img2 = new ImageView(this);
        img2.setBackgroundResource(R.drawable.thuongnguyendo);
        img2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageView img3 = new ImageView(this);
        img3.setBackgroundResource(R.drawable.bachluyenthanhthan);
        img3.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mFlipper.addView(img);
        mFlipper.addView(img1);
        mFlipper.addView(img2);
        mFlipper.addView(img3);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    txt_Search.setVisibility(View.GONE);
                    isOpen = false;
                } else {
                    txt_Search.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
            }
        });
        txt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = txt_Search.getText().toString();
                adapter.sortByName(str);
            }
        });
    }

    public void setData() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setControl() {
        mViewPager = findViewById(R.id.mViewPager);
        mTabLayout = findViewById(R.id.mTabLayout);
        btn_Search = findViewById(R.id.search);
        txt_Search = findViewById(R.id.txt_search);
        mFlipper = findViewById(R.id.mFlipper);
        home = findViewById(R.id.home);
    }
}