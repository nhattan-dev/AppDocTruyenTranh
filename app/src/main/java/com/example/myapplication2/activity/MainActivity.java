package com.example.myapplication2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2.API.doGet;
import com.example.myapplication2.R;
import com.example.myapplication2.m_interface.BaseObject;

public class MainActivity extends AppCompatActivity implements BaseObject {
    Button button, switchAct;
    String api;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.home_btn);
        switchAct = findViewById(R.id.home_change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading) {
                    isLoading = true;
                    new doGet(MainActivity.this, api).execute();
                }
            }
        });
        switchAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        end();
    }

    @Override
    public void error() {
        end();
    }

    @Override
    public void end() {
        isLoading = false;
    }

    @Override
    public void execPost(String data) {

    }
}