package com.example.myapplication2.m_interface;

import java.util.ArrayList;

public interface ByteArrayAsyncTask<T> {
    void startByteArrayAsyncTask();
    void execByteArrayAsyncTask(ArrayList<T> data);
    void errorByteArrayAsyncTask();
    void endByteArrayAsyncTask();
}
