package com.example.myapplication2.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mComic {
    String name, summary, image, author, tran;
    int comic_id, genre_id;
    ArrayList<mChapter> mChapters;

    public mComic(String name, String summary, String image, String author, String tran, int comic_id, int genre_id, ArrayList<mChapter> mChapters) {
        this.name = name;
        this.summary = summary;
        this.image = image;
        this.author = author;
        this.tran = tran;
        this.comic_id = comic_id;
        this.genre_id = genre_id;
        this.mChapters = mChapters;
    }

    public mComic() {
    }

    public mComic(mComic mComic) {
        this.name = mComic.name;
        this.summary = mComic.summary;
        this.image = mComic.image;
        this.author = mComic.author;
        this.tran = mComic.tran;
        this.comic_id = mComic.comic_id;
        this.genre_id = mComic.genre_id;
        this.mChapters = new ArrayList<>(mComic.mChapters);
    }

    public mComic(JSONObject object) throws JSONException {
        this.name = object.getString("name");
        this.summary = object.getString("summary");
        this.image = object.getString("image");
        this.author = object.getString("author");
        this.tran = object.getString("trans");
        this.comic_id = Integer.parseInt(object.getString("comic_id"));
        this.genre_id = Integer.parseInt(object.getString("genre_id"));
        JSONArray array = object.getJSONArray("chapters");
        this.mChapters = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            this.mChapters.add(new mChapter(jsonObject));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public ArrayList<mChapter> getmChapters() {
        return mChapters;
    }

    public void setmChapters(ArrayList<mChapter> mChapters) {
        this.mChapters = mChapters;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", tran='" + tran + '\'' +
                ", comic_id=" + comic_id +
                ", genre_id=" + genre_id +
                ", chapters=" + mChapters +
                '}';
    }
}
