package com.example.myapplication2.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class mChapter {
    private int chapter_id, comic_id, view;
    private String name;
    private Date update_time;
    private ArrayList<String> links;
    private ArrayList<byte[]> blobs;

    public mChapter() {
    }

    public mChapter(int chapter_id, int comic_id, int view, String name, Date update_time, ArrayList<String> links) {
        this.chapter_id = chapter_id;
        this.comic_id = comic_id;
        this.view = view;
        this.name = name;
        this.update_time = update_time;
        this.links = links;
    }
    public mChapter(mChapter mChapter){
        this.chapter_id = mChapter.chapter_id;
        this.comic_id = mChapter.comic_id;
        this.view = mChapter.view;
        this.name = mChapter.name;
        this.update_time = mChapter.update_time;
        this.links = mChapter.links;
    }



    public mChapter(JSONObject object) {
        try {
            this.chapter_id = Integer.parseInt(object.getString("chapter_id"));
            this.comic_id = Integer.parseInt(object.getString("comic_id"));
            this.view = Integer.parseInt(object.getString("view"));
            this.name = object.getString("name");
            this.update_time = new SimpleDateFormat("yyyy-MM-dd").parse(object.getString("update_time"));
            this.links = new ArrayList<>();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<byte[]> getBlobs() {
        return blobs;
    }

    public void setBlobs(ArrayList<byte[]> blobs) {
        this.blobs = blobs;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }
}
