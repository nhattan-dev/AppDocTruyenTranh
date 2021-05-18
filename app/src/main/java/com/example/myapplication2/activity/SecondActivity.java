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

public class SecondActivity extends AppCompatActivity implements BaseObject {

    Button button, switchAct;
    private String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button = findViewById(R.id.second_btn);
        switchAct = findViewById(R.id.second_change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new doGet(SecondActivity.this, api).execute();
            }
        });
        switchAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        Toast.makeText(this, "Second Activity", Toast.LENGTH_SHORT).show();
        end();
    }

    @Override
    public void error() {
        end();
    }

    @Override
    public void end() {

    }

    @Override
    public void execPost(String data) {

    }
}