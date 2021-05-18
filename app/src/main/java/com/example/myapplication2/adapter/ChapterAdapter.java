package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication2.R;
import com.example.myapplication2.model.mChapter;

import java.util.ArrayList;

public class ChapterAdapter extends ArrayAdapter<mChapter> {

    private Context context;
    private ArrayList<mChapter> mChapters;

    public ChapterAdapter(@NonNull Context context, int resource, @NonNull ArrayList<mChapter> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mChapters = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chapter_item, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);
        if (mChapters.size() > 0) {
            mChapter mChapter = this.mChapters.get(position);

            TextView tenChapter = convertView.findViewById(R.id.ci_chaptername);
            TextView chapter_id = convertView.findViewById(R.id.ci_chapter_id);

            tenChapter.setText(mChapter.getName());
            chapter_id.setText(mChapter.getChapter_id() + "");
        }
        return convertView;
    }
}
