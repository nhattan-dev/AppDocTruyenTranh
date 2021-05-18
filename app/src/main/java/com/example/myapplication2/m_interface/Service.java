package com.example.myapplication2.m_interface;

public interface Service<T> {
    public final static String BASE_URL = "http://truyensieuhay.somee.com/api/";
    public final static String DRIVE_URL = "https://drive.google.com/uc?id=";
    void start();
    void execGet(T t);
    void execPost(T t);
    void error();
    void end();
}
