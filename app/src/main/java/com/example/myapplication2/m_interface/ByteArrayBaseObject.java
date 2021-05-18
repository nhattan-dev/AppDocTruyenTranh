package com.example.myapplication2.m_interface;

import java.util.ArrayList;

public interface ByteArrayBaseObject extends ByteArrayAsyncTask<byte[]>{
    @Override
    void startByteArrayAsyncTask();

    @Override
    void execByteArrayAsyncTask(ArrayList<byte[]> data);

    @Override
    void errorByteArrayAsyncTask();
}
