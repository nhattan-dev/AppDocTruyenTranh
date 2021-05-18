package com.example.myapplication2.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication2.API.DownloadChapterAPI;
//import com.example.myapplication2.API.ImageListAPI;
import com.example.myapplication2.API.doGet;
import com.example.myapplication2.DAO.DowloadDAO;
import com.example.myapplication2.DAO.ReadDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.adapter.ImageAdapter;
import com.example.myapplication2.fragment.fm_ChapterList;
import com.example.myapplication2.helper.ByteArrayHelper;
import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.m_interface.ByteArrayBaseObject;
import com.example.myapplication2.model.mChapter;
import com.example.myapplication2.model.mImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class Chapter extends AppCompatActivity implements BaseObject, ByteArrayBaseObject {

    ArrayList<mImage> mImages;
    GridView gridView;
    public static mChapter mChapter;
    TextView cd_chapter_name;
    ImageView cd_back, cd_next, cd_pre, cd_comment, cd_download;
    public Context context;
    DowloadDAO dl;
    public AlertDialog alertDialog;
    Chapter application;
    doGet m_doGet;
    private String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        dl = new DowloadDAO(this);
        this.context = this;
        application = this;
        setControl();
        mImages = new ArrayList<>();

        api = "ChapterApi?chapter_id=" + mChapter.getChapter_id();
        m_doGet = new doGet(this, api);
        m_doGet.execute();
        setEvent();
    }

    private void setEvent() {
        cd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cd_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chapter.this, Comment.class);
                startActivity(intent);
            }
        });
        cd_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check in db
                if (dl.isExists(mChapter.getChapter_id())) {
                    Toast.makeText(context, "ĐÃ TẢI", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog = new SpotsDialog(context);
                    alertDialog.show();
                    ArrayList<String> imgs = new ArrayList<>();
                    for (mImage i : mImages) {
                        imgs.add(i.getImage_id());
                    }
                    new ByteArrayHelper(imgs, Chapter.this).execute();
                }
            }
        });
        cd_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fm_ChapterList.chaptersTemp.get(0).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                    for (int i = 0; i < fm_ChapterList.chaptersTemp.size(); i++) {
                        if (fm_ChapterList.chaptersTemp.get(i).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                            mChapter.setChapter_id(fm_ChapterList.chaptersTemp.get(i - 1).getChapter_id());
                            mChapter.setName(fm_ChapterList.chaptersTemp.get(i - 1).getName());

                            mImages = new ArrayList<>();
                            api = "ChapterApi?chapter_id=" + mChapter.getChapter_id();
                            new doGet(Chapter.this, api).execute();
                            break;
                        }
                    }
                }
            }
        });
        cd_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fm_ChapterList.chaptersTemp.get(fm_ChapterList.chaptersTemp.size() - 1).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                    for (int i = 0; i < fm_ChapterList.chaptersTemp.size() - 1; i++) {
                        if (fm_ChapterList.chaptersTemp.get(i).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                            mChapter.setChapter_id(fm_ChapterList.chaptersTemp.get(i + 1).getChapter_id());
                            mChapter.setName(fm_ChapterList.chaptersTemp.get(i + 1).getName());

                            mImages = new ArrayList<>();
                            api = "ChapterApi?chapter_id=" + mChapter.getChapter_id();
                            new doGet(Chapter.this, api).execute();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setControl() {
        gridView = findViewById(R.id.cd_list);
        cd_chapter_name = findViewById(R.id.cd_chapter_name);
        cd_back = findViewById(R.id.cd_back);
        cd_next = findViewById(R.id.cd_Next);
        cd_pre = findViewById(R.id.cd_Pre);
        cd_comment = findViewById(R.id.cd_Comment);
        cd_download = findViewById(R.id.cd_Download);
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        //insert to db
        new ReadDAO(context).insert(mChapter);

        try {
            JSONObject object = new JSONObject(data);
            String str = object.getString("link");
            String[] arr = str.replace('"', '[')
                    .replace("[", "")
                    .replace("]", "").split(",");
            int chapter_id = Integer.parseInt(object.getString("chapter_id"));
//            int i = 0;
            for (String s : arr) {
                mImages.add(new mImage(s, chapter_id));
//                images.add(new Image(s, chapter_id, api.blobs.get(i)));
//                i++;
            }
            cd_chapter_name.setText(object.getString("name"));
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        end();
    }

    ImageAdapter imageAdapter;

    private void setData() {
        Log.e("image size", mImages.size() + "");
        imageAdapter = new ImageAdapter(getApplication(), 0, mImages);
        gridView.setAdapter(imageAdapter);
    }

    @Override
    public void error() {
        Toast.makeText(getApplication(), "SOMETHING WAS WRONG", Toast.LENGTH_SHORT).show();
        end();
    }

    @Override
    public void end() {

    }

    @Override
    public void execPost(String data) {

    }

    @Override
    public void startByteArrayAsyncTask() {

    }

    @Override
    public void execByteArrayAsyncTask(ArrayList<byte[]> data) {
        if (data.size() >= 1) {
            mChapter.setBlobs(data);
            boolean result = new DowloadDAO(Chapter.this).insert(mChapter);
            if (result) {
                Toast.makeText(Chapter.this, "THÀNH CÔNG !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Chapter.this, "THẤT BẠI !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Chapter.this, "THẤT BẠI !", Toast.LENGTH_SHORT).show();
        }

        endByteArrayAsyncTask();
    }

    @Override
    public void errorByteArrayAsyncTask() {
        endByteArrayAsyncTask();
    }

    @Override
    public void endByteArrayAsyncTask() {
        alertDialog.dismiss();
    }
}