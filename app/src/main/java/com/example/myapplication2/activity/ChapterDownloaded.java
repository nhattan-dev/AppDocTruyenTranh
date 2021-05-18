package com.example.myapplication2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.DAO.DowloadDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.adapter.ImageDownloadAdapter;
import com.example.myapplication2.fragment.fm_Downloaded;
import com.example.myapplication2.model.mChapter;
import com.example.myapplication2.model.mImage;

import java.util.ArrayList;

public class ChapterDownloaded extends AppCompatActivity {

    ArrayList<mImage> mImages;
    GridView gridView;
    public static mChapter mChapter;
    mChapter getMChapter;
    TextView cd_chapter_name;
    ImageView cd_back, cd_next, cd_pre, cd_Delete;
    public Context context;
    DowloadDAO dl;
    String name = "";
    ChapterDownloaded application;
    ImageDownloadAdapter adapter;
    ArrayList<byte[]> blobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail_download);

        this.context = this;
        application = this;
        dl = new DowloadDAO(this);
        getMChapter = dl.select(mChapter.getChapter_id());
        setControl();
        setData();
        setEvent();
    }

    private void setEvent() {
        cd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cd_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fm_Downloaded.chaptersTemp.get(0).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                    for (int i = 1; i < fm_Downloaded.chaptersTemp.size(); i++) {
                        if (fm_Downloaded.chaptersTemp.get(i).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                            mChapter.setChapter_id(fm_Downloaded.chaptersTemp.get(i - 1).getChapter_id());
                            getMChapter = dl.select(mChapter.getChapter_id());
                            setData();
                        }
                    }
                }
            }
        });
        cd_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fm_Downloaded.chaptersTemp.get(fm_Downloaded.chaptersTemp.size() - 1).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                    for (int i = 0; i < fm_Downloaded.chaptersTemp.size() - 1; i++) {
                        if (fm_Downloaded.chaptersTemp.get(i).getName().equalsIgnoreCase(cd_chapter_name.getText().toString())) {
                            mChapter.setChapter_id(fm_Downloaded.chaptersTemp.get(i + 1).getChapter_id());
                            getMChapter = dl.select(mChapter.getChapter_id());
                            setData();
                        }
                    }
                }
            }
        });
        cd_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ChapterDownloaded.this)
                        .setTitle("CONFIRM !")
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setMessage("XÁC NHẬN XÓA !")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean result = new DowloadDAO(ChapterDownloaded.this).delete(mChapter);
                                if (result) {
                                    Toast.makeText(ChapterDownloaded.this, "THÀNH CÔNG !", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(ChapterDownloaded.this, "THẤT BẠI !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("NO", null)
                        .show();
            }
        });
    }

    private void setControl() {
        gridView = findViewById(R.id.cdd_list);
        cd_chapter_name = findViewById(R.id.cdd_chapter_name);
        cd_back = findViewById(R.id.cdd_back);
        cd_next = findViewById(R.id.cdd_Next);
        cd_pre = findViewById(R.id.cdd_Pre);
        cd_Delete = findViewById(R.id.cdd_Delete);
    }

    private void setData() {
        adapter = new ImageDownloadAdapter(getApplication(), 0, getMChapter.getBlobs());
        gridView.setAdapter(adapter);
        cd_chapter_name.setText(getMChapter.getName());
    }
}