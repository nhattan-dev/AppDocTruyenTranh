package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication2.R;
import com.example.myapplication2.model.mImage;
import com.example.myapplication2.model.mImageDTO;

import java.util.ArrayList;

import static com.example.myapplication2.m_interface.Service.DRIVE_URL;

public class ImageAdapter extends ArrayAdapter<mImage> {

    private Context context;
    private ArrayList<mImage> mImages;
    private ArrayList<mImageDTO> mImageDTOS = new ArrayList<>();
    public static String comic_name;

    public ImageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<mImage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mImages = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        for (ImageDTO imageDTO : imageDTOs) {
//            if (imageDTO.getPosition() == position) {
//                return imageDTO.getView();
//            }
//        }
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_item, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);
        if (mImages.size() > 0 && position < mImages.size()) {
            mImage mImage = this.mImages.get(position);
            ImageView img = convertView.findViewById(R.id.ii_img);

//            if (image.getBlob() == null) {
            Glide.with(this.context).load(DRIVE_URL + mImage.getImage_id())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
            mImageDTOS.add(new mImageDTO(position, convertView));
//                image.setBitmap(((BitmapDrawable) img.getDrawable()).getBitmap());
//            }else {
//                img.setImageBitmap(image.getBitmap());
//            }
//            img.setImageBitmap(BitmapFactory.decodeByteArray(image.getBlob(), 0, image.getBlob().length));
        }
        return convertView;
    }
}
