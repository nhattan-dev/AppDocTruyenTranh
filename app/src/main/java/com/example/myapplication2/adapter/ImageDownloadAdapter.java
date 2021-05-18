package com.example.myapplication2.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication2.R;

import java.util.ArrayList;

public class ImageDownloadAdapter extends ArrayAdapter<byte[]> {

    private Context context;
    private ArrayList<byte[]> blobs;
    public static String comic_name;

    public ImageDownloadAdapter(@NonNull Context context, int resource, @NonNull ArrayList<byte[]> objects) {
        super(context, resource, objects);
        this.context = context;
        this.blobs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_item, null);
        }
        if (blobs.size() > 0 && position < blobs.size()) {
            byte[] blob = this.blobs.get(position);
            ImageView img = convertView.findViewById(R.id.ii_img);

            img.setImageBitmap(BitmapFactory.decodeByteArray(blob, 0, blob.length));
        }
        return convertView;
    }
}
