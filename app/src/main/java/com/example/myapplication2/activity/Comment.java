package com.example.myapplication2.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication2.API.CommentAPI;
//import com.example.myapplication2.API.PostComment;
import com.example.myapplication2.API.doGet;
import com.example.myapplication2.API.doPost;
import com.example.myapplication2.R;
import com.example.myapplication2.adapter.CommentAdapter;
import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.model.mComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comment extends AppCompatActivity implements BaseObject {

    private ArrayList<mComment> mComments, commentsTemp;
    GridView mGridView;
    ImageView cm_back, cm_new;
    private CommentAdapter adapter;
    Context context;
    Comment application;
    private String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        this.context = this;
        this.application = this;

        defaultData();

        setControl();

        setEvent();

        api = "commentapi?comic_id=" + Comic.mComic.getComic_id() + "&begin=1&end=100";
//        new CommentAPI(this, ComicDetail.comic.getComic_id(), 0, 20).execute();
        new doGet(this, api).execute();
    }

    private void defaultData() {
        mComments = new ArrayList<>();
        commentsTemp = new ArrayList<>();
    }

    private void setEvent() {
        cm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cm_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.comment_new_dialog);

                Button yes, no;
                EditText name, content;
                yes = dialog.findViewById(R.id.cm_yes);
                no = dialog.findViewById(R.id.cm_no);
                name = dialog.findViewById(R.id.cm_name_new);
                content = dialog.findViewById(R.id.cm_noidung_new);

                dialog.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentator = "", cmt_content = "";
                        commentator = name.getText().toString();
                        cmt_content = content.getText().toString();
                        if (commentator.isEmpty() || cmt_content.isEmpty()) {
                            Toast.makeText(context, "NHẬP ĐẦY ĐỦ THÔNG TIN", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(context, "POST TO SERVER", Toast.LENGTH_SHORT).show();
                            String[] paras, values;
                            paras = new String[5];
                            values = new String[5];
                            paras[0] = "cmt_id";
                            paras[1] = "commentator";
                            paras[2] = "cmt_content";
                            paras[3] = "cmt_time";
                            paras[4] = "chapter_id";
                            values[0] = "0";
                            values[1] = commentator;
                            values[2] = cmt_content;
                            values[3] = "0";
                            values[4] = Chapter.mChapter.getChapter_id() + "";
                            api = "CommentApi";
                            new doPost(application, paras, values, api).execute();
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            defaultData();
//                            new CommentAPI(application, ComicDetail.comic.getComic_id(), 0, 20).execute();
                            dialog.dismiss();
                        }
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void setControl() {
        mGridView = findViewById(R.id.cm_GridView);
        cm_back = findViewById(R.id.cm_back);
        cm_new = findViewById(R.id.cm_new);
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        Log.e("loading comment: ", data);
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length() && i < 9; i++) {
                JSONObject object = array.getJSONObject(i);
                mComments.add(new mComment(object));
                commentsTemp.add(new mComment(object));
            }
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        end();
    }

    private void setData() {
        adapter = new CommentAdapter(this, 0, mComments);
        mGridView.setAdapter(adapter);
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
        if (data.equalsIgnoreCase("0")) {
            Toast.makeText(Comment.this, "THÀNH CÔNG !", Toast.LENGTH_SHORT).show();
            defaultData();
            api = "commentapi?comic_id=" + Comic.mComic.getComic_id() + "&begin=1&end=100";
            new doGet(this, api).execute();
        }

        end();
    }
}