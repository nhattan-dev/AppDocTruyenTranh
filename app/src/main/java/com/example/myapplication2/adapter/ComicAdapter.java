package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication2.R;
import com.example.myapplication2.model.mComic;

import java.util.ArrayList;

public class ComicAdapter extends ArrayAdapter<mComic> {

    private Context context;
    private ArrayList<mComic> mComics;

    public ComicAdapter(@NonNull Context context, int resource, @NonNull ArrayList<mComic> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mComics = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comic_item2, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);
        if (mComics.size() > 0) {
            mComic mComic = this.mComics.get(position);

            TextView tenTruyen = convertView.findViewById(R.id.txtTenTruyen);
            TextView tenChap = convertView.findViewById(R.id.txtTenChap);
            ImageView imgAnh = convertView.findViewById(R.id.imgTruyen);

            tenTruyen.setText(mComic.getName());
            if (mComic.getmChapters().size() > 0)
                tenChap.setText("Chap " + mComic.getmChapters().get(0).getName());
            Glide.with(this.context).load("https://drive.google.com/uc?id=" + mComic.getImage()).thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAnh);
        }
        return convertView;
    }
}
