package com.example.myapplication2.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class mComment {
    private int cmt_id;
    private String commentator;
    private String cmt_content;
    private Date cmt_time;
    private int chapter_id;

    public mComment() {
    }

    public mComment(JSONObject object) throws JSONException {
        this.chapter_id = object.getInt("chapter_id");
        this.commentator = object.getString("commentator");
        this.cmt_content = object.getString("cmt_content");
//        this.cmt_time = new SimpleDateFormat("dd/MM/yyyy").;
        this.cmt_id = object.getInt("cmt_id");
    }

    public mComment(int cmt_id, String commentator, String cmt_content, Date cmt_time, int chapter_id) {
        this.cmt_id = cmt_id;
        this.commentator = commentator;
        this.cmt_content = cmt_content;
        this.cmt_time = cmt_time;
        this.chapter_id = chapter_id;
    }

    public int getCmt_id() {
        return cmt_id;
    }

    public void setCmt_id(int cmt_id) {
        this.cmt_id = cmt_id;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public String getCmt_content() {
        return cmt_content;
    }

    public void setCmt_content(String cmt_content) {
        this.cmt_content = cmt_content;
    }

    public Date getCmt_time() {
        return cmt_time;
    }

    public void setCmt_time(Date cmt_time) {
        this.cmt_time = cmt_time;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }
}
