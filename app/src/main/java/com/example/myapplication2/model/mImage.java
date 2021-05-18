package com.example.myapplication2.model;

import android.graphics.Bitmap;

public class mImage {
    private String image_id;
    private byte[] blob;
    private Bitmap bitmap;
    private int chapter_id;

    public mImage(String image_id, int chapter_id) {
        this.image_id = image_id;
        this.chapter_id = chapter_id;
    }

    public mImage(String image_id, int chapter_id, byte[] blob) {
        this.image_id = image_id;
        this.blob = blob;
        this.chapter_id = chapter_id;
    }

    public mImage(byte[] blob) {
        this.blob = blob;
    }

    public mImage() {
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }
}