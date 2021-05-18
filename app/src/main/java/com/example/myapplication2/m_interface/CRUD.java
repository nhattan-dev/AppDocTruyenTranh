package com.example.myapplication2.m_interface;

import java.util.ArrayList;

public interface CRUD<T, N> {
    boolean insert(T t);
    boolean delete(T t);
    boolean edit(T t);
    T select(int chapter_id);
    ArrayList<T> selectAll(N n);
    boolean isExists(N n);
}
