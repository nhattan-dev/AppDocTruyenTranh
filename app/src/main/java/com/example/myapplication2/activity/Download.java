package com.example.myapplication2.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication2.API.ChapterListDownloadAPI;
import com.example.myapplication2.Service.doGet;
import com.example.myapplication2.DAO.DowloadDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.adapter.DownloadAdapter;
import com.example.myapplication2.helper.DownloadComicHelper;
import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.m_interface.ByteArrayBaseObject;
import com.example.myapplication2.model.mChapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class Download extends AppCompatActivity implements BaseObject, ByteArrayBaseObject {
    ArrayList<mChapter> mChapters, chaptersTemp;
    GridView gridView;
    ImageView dl_download, dl_back;
    CheckBox dl_checkbox_all;
    public static int comic_id;
    DownloadAdapter adapter;
    public static boolean isAll = false;
    Context context;
    private String api;
    DowloadDAO dl;
    public AlertDialog alertDialog;
    private int size = 0, position = -1;
    private ArrayList<mChapter> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dowload);
        dl = new DowloadDAO(this);
        mChapters = new ArrayList<>();
        chaptersTemp = new ArrayList<>();
        this.context = this;
        setControl();

        setEvent();

        api = "comicapi?comic_id=" + comic_id + "&begin=1&end=10000";
        new doGet(this, api).execute();
    }

    private void setEvent() {
        dl_checkbox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    isAll = true;
                else
                    isAll = false;
            }
        });
        dl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dl_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dowload
                Toast.makeText(getApplication(), "CHỨC NĂNG TẢI HÀNG LOẠT CHƯA PHÁT TRIỂN", Toast.LENGTH_SHORT).show();
                alertDialog = new SpotsDialog(context);
                alertDialog.show();
                if (isAll) {
                    //tải tất cả
                    list = new ArrayList<>(mChapters);
                } else {
                    for (int i = 0; i < adapter.checkboxs.length; i++) {
                        if (adapter.checkboxs[i]) {
                            list.add(mChapters.get(i));
                        }
                    }
                }
                size = list.size();

                for (int i = 0; i < list.size(); i++) {
                    if (dl.isExists(list.get(i).getChapter_id())) {
                        Toast.makeText(context, "ĐÃ TẢI CHAP " + list.get(i).getName(), Toast.LENGTH_SHORT).show();
                    } else {
                        new DownloadComicHelper(list.get(i), Download.this).execute();
                    }
                }
                //save to db
            }
        });
    }

    private void setControl() {
        gridView = findViewById(R.id.dl_GridView1);
        dl_download = findViewById(R.id.dl_download);
        dl_back = findViewById(R.id.dl_back);
        dl_checkbox_all = findViewById(R.id.dl_checkbox_all);
    }

    private void setData() {
        adapter = new DownloadAdapter(this, 0, mChapters);
        gridView.setAdapter(adapter);
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        Log.e("loading chapter: ", data);
        try {
            JSONObject object = new JSONObject(data);
            JSONArray chapterArray = object.getJSONArray("chapters");
            for (int i = 0; i < chapterArray.length(); i++) {
                JSONObject jsonObject = chapterArray.getJSONObject(i);
                mChapters.add(new mChapter(jsonObject));
                chaptersTemp.add(new mChapter(jsonObject));
            }
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        end();
    }

    @Override
    public void error() {
        Toast.makeText(getApplication(), "SOMETHING WAS WRONG", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void end() {

    }

    @Override
    public void execPost(String data) {
        end();
    }

    @Override
    public void startByteArrayAsyncTask() {

    }

    @Override
    public void execByteArrayAsyncTask(ArrayList<byte[]> data) {
        if (data.size() >= 1) {
            mChapters.get(position).setBlobs(data);
            boolean result = new DowloadDAO(Download.this).insert(mChapters.get(position));
            if (result) {
                Toast.makeText(Download.this, "THÀNH CÔNG !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Download.this, "THẤT BẠI !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Download.this, "THẤT BẠI !", Toast.LENGTH_SHORT).show();
        }

        endByteArrayAsyncTask();
    }

    @Override
    public void errorByteArrayAsyncTask() {

    }

    @Override
    public void endByteArrayAsyncTask() {

    }
}