package com.example.myapplication2.helper;

import android.os.AsyncTask;

import com.example.myapplication2.m_interface.ByteArrayBaseObject;

import java.util.ArrayList;

public class ByteArrayHelper extends AsyncTask<Void, Void, Void> {
    ArrayList<byte[]> blobs;
    ArrayList<String> images;
    ByteArrayBaseObject byteArrayBaseObject;

    public ByteArrayHelper(ArrayList<String> images, ByteArrayBaseObject byteArrayBaseObject) {
        this.images = images;
        this.byteArrayBaseObject = byteArrayBaseObject;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            blobs = ImageHelper.toBlobs(images);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (blobs == null){
            byteArrayBaseObject.errorByteArrayAsyncTask();
        }else {
            byteArrayBaseObject.execByteArrayAsyncTask(blobs);
        }
    }
}
