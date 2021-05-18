package com.example.myapplication2.helper;

import android.util.Log;

import com.example.myapplication2.m_interface.Service;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ImageHelper {

    public static ArrayList<byte[]> toBlobs(ArrayList<String> urls) {
        ArrayList<byte[]> blobs = new ArrayList<>();
        for (String url : urls) {
            byte[] blob = getByteArrayImage(url);
            if (blob != null) {
                blobs.add(blob);
            }
        }
        return blobs;
    }

    public static byte[] getByteArrayImage(String url) {
        try {
            URL imageUrl = new URL(Service.DRIVE_URL + url);
            InputStream is = imageUrl.openStream();
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
