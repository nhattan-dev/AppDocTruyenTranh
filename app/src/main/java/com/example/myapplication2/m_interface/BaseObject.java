package com.example.myapplication2.m_interface;

public interface BaseObject extends Service<String>{
    @Override
    void start();

    @Override
    void execGet(String data);

    @Override
    void error();

    @Override
    void end();

    @Override
    void execPost(String data);
}
