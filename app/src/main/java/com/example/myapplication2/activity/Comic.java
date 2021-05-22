package com.example.myapplication2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//    import com.example.myapplication2.API.Comic_Sub_Information_1;
//    import com.example.myapplication2.API.Comic_Sub_Information_2;
//    import com.example.myapplication2.API.Comic_Sub_Information_3;
import com.example.myapplication2.Service.CustomGET;
import com.example.myapplication2.R;
import com.example.myapplication2.fragment.fm_ChapterList;
import com.example.myapplication2.model.mComic;

public class Comic extends AppCompatActivity {

    public static mComic mComic;
    TextView dt_comicname1, dt_comicname2, dt_summary, dt_lastChapter1, dt_lastChapter2, dt_view,
            dt_tacgia, dt_theloai, dt_comic_id, dt_comment, dt_comment2, dt_docngay;
    ImageView dt_img1, dt_img2, dt_back, dt_download;
    TextView l2, l3, l4;//, f1, f2, f3;
    String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        setControl();
        setData();
        setEvent();
        api = "comicapi?comic_idd=" + mComic.getComic_id();
        new CustomGET(dt_view, api).execute();
        api = "commentapi?comic_idd=" + mComic.getComic_id();
        new CustomGET(dt_comment, api).execute();
        api = "genreapi?genre_id=" + mComic.getGenre_id();
        new CustomGET(dt_theloai, api).execute();
//            new Comic_Sub_Information_1(dt_view, comic.getComic_id(), this).execute();
//            new Comic_Sub_Information_2(dt_view, comic.getComic_id(), this).execute();
    }

    public void setView(String sumView) {
        dt_view.setText(sumView);
    }

    private void setEvent() {
        dt_lastChapter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm_ChapterList.mComic = new mComic(mComic);
                Intent intent = new Intent(Comic.this, ChapterList.class);
                startActivity(intent);
            }
        });
        dt_docngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm_ChapterList.mComic = new mComic(mComic);
                Intent intent = new Intent(Comic.this, ChapterList.class);
                startActivity(intent);
            }
        });
        dt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download.comic_id = mComic.getComic_id();
                Intent intent = new Intent(Comic.this, Download.class);
                startActivity(intent);
            }
        });
        dt_comment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Comic.this, Comment.class);
                startActivity(intent);
            }
        });
        dt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Comic.this, Comment.class);
                startActivity(intent);
            }
        });
    }

    private void setData() {

//            new Comic_Sub_Information_3(comic.getGenre_id(), this).execute();
        dt_comicname1.setText(mComic.getName());
        dt_comicname2.setText(mComic.getName());
        dt_summary.setText(mComic.getSummary());
        dt_tacgia.setText(mComic.getAuthor());
        dt_lastChapter1.setText("Chap " + mComic.getmChapters().get(0).getName());
        dt_lastChapter2.setText("Chap " + mComic.getmChapters().get(0).getName());
        dt_comic_id.setText(mComic.getComic_id() + "");
        Glide.with(Comic.this).load("https://drive.google.com/uc?id=" + mComic.getImage()).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(dt_img1);
        Glide.with(Comic.this).load("https://drive.google.com/uc?id=" + mComic.getImage()).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(dt_img2);
    }

    private void setControl() {
        dt_comic_id = findViewById(R.id.dt_comic_id);
        dt_comicname1 = findViewById(R.id.dt_comicname1);
        dt_comicname2 = findViewById(R.id.dt_comicname2);
        dt_summary = findViewById(R.id.dt_summary);
        dt_lastChapter1 = findViewById(R.id.dt_lastchapter1);
        dt_lastChapter2 = findViewById(R.id.dt_lastchapter2);
        dt_tacgia = findViewById(R.id.dt_tacgia);
        dt_view = findViewById(R.id.dt_view);
        dt_theloai = findViewById(R.id.dt_theloai);
        dt_img1 = findViewById(R.id.dt_img1);
        dt_img2 = findViewById(R.id.dt_img2);
        dt_back = findViewById(R.id.dt_back);
        dt_download = findViewById(R.id.dt_download);
        dt_comment = findViewById(R.id.dt_3);
        dt_comment2 = findViewById(R.id.dt_comment2);
        dt_docngay = findViewById(R.id.dt_f2);
        //
        l2 = findViewById(R.id.dt_2);
        l3 = findViewById(R.id.dt_3);
        l4 = findViewById(R.id.dt_4);

        //
        //        f1 = findViewById(R.id.dt_f1);
        //        f2 = findViewById(R.id.dt_f2);
        //        f3 = findViewById(R.id.dt_f3);
    }

    public void setComment(String sumComment) {
        dt_comment.setText(sumComment);
    }

    public void setGenre(String name) {
        dt_theloai.setText(name);
    }
}