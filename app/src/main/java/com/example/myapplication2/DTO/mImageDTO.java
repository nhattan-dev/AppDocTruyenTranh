package com.example.myapplication2.DTO;

import android.view.View;

public class mImageDTO {
    private int position;
    private View view;
    private boolean isLoaded = false;

    public mImageDTO(int position, View view) {
        this.position = position;
        this.view = view;
    }

    public mImageDTO() {
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
