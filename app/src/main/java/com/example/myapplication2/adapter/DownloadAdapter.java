package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication2.R;
import com.example.myapplication2.model.mChapter;

import java.util.ArrayList;

public class DownloadAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<mChapter> mChapters;
    public boolean[] checkboxs;
    public ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    public DownloadAdapter(@NonNull Context context, int resource, @NonNull ArrayList<mChapter> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mChapters = objects;
        checkboxs = new boolean[objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            checkboxs[i] = false;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.download_chapter_item, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);
        if (mChapters.size() > 0) {
            mChapter mChapter = this.mChapters.get(position);

            CheckBox checkBox = convertView.findViewById(R.id.dl_checkbox);
            TextView chapterName = convertView.findViewById(R.id.dl_chaptername);

            chapterName.setText("Chapter " + mChapter.getName());
            if (checkboxs[position])
                checkBox.setChecked(true);
            else
                checkBox.setChecked(false);
            checkBox.setOnClickListener(v -> {
                if (checkBox.isChecked())
                    checkboxs[position] = true;
                else
                    checkboxs[position] = false;
            });
        }
        return convertView;
    }

}
