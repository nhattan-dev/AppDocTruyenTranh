package com.example.myapplication2.model;

public class Property {
    private int ID;
    private String update_time;

    public Property(int ID, String update_time) {
        this.ID = ID;
        this.update_time = update_time;
    }

    public Property() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
