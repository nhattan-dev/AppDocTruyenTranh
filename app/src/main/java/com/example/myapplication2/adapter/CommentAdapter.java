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
import com.example.myapplication2.model.mComment;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter {
    ArrayList<mComment> mComments;
    Context context;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        mComments = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_item, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);
        if (mComments.size() > 0) {
            mComment mComment = this.mComments.get(position);

            TextView name = convertView.findViewById(R.id.cm_name);
            TextView noidung = convertView.findViewById(R.id.cm_noidung);

            name.setText(mComment.getCommentator());
            noidung.setText(mComment.getCmt_content());
        }
        return convertView;
    }
}
