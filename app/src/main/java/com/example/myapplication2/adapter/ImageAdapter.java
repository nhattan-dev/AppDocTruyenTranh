package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication2.R;
import com.example.myapplication2.model.mImage;
import com.example.myapplication2.DTO.mImageDTO;

import java.util.ArrayList;

import static com.example.myapplication2.m_interface.Service.DRIVE_URL;

public class ImageAdapter extends ArrayAdapter<mImage> {

    private Context context;
    private ArrayList<mImage> mImages;
    public mImageDTO[] mImageDTOs;// = new ArrayList<>();
    public int lastPosition;
    GridView view;
    int count = 0;

    public ImageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<mImage> objects, int lastPosition, GridView view) {
        super(context, resource, objects);
        this.context = context;
        this.mImages = objects;
        mImageDTOs = new mImageDTO[mImages.size()];
        this.lastPosition = lastPosition;
        this.view = view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (mImageDTOs[position] != null) {
            return mImageDTOs[position].getView();
        }
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_item, null);
        }
        if (mImages.size() > 0 && position < mImages.size()) {
            mImage mImage = this.mImages.get(position);
            ImageView img = convertView.findViewById(R.id.ii_img);
            Glide.with(this.context).load(DRIVE_URL + mImage.getImage_id())
                    .thumbnail(0.5f)
                    .crossFade()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                            if (position == lastPosition){
//                            if (++count == mImages.size()) {
                                view.setSelection(lastPosition);
                            }
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
            mImageDTOs[position] = new mImageDTO(position, convertView);
        }
        return convertView;
    }
}
