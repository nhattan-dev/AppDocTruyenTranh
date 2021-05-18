package com.example.myapplication2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication2.helper.MySQLiteOpenHelper;
import com.example.myapplication2.m_interface.CRUD;
import com.example.myapplication2.model.mChapter;

import java.util.ArrayList;

public class ReadDAO extends MySQLiteOpenHelper implements CRUD<mChapter, Integer> {

    SQLiteDatabase db;

    public ReadDAO(Context context) {
        super(context);
    }

    @Override
    public boolean insert(mChapter mChapter) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(READ_CHAPTER_ID, mChapter.getChapter_id());
            values.put(READ_CHAPTER_NAME, mChapter.getName());
            values.put(READ_COMIC_ID, mChapter.getComic_id());
            result = (int) db.insert(READ_TABLE, null, values);
            if (result == -1)
                return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return false;
    }

    @Override
    public boolean delete(mChapter mChapter) {
        try {
            db = this.getWritableDatabase();
            int result = db.delete(READ_TABLE, READ_CHAPTER_ID + " = " + mChapter.getChapter_id(), null);
            return result > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return false;
    }

    @Override
    public boolean edit(mChapter mChapter) {
        return false;
    }

    @Override
    public mChapter select(int chapter_id) {
        return null;
    }

    @Override
    public ArrayList<mChapter> selectAll(Integer comic_id) {
        ArrayList<mChapter> mChapters = new ArrayList<>();
        try {
//            String query = "SELECT " + READ_CHAPTER_ID + ", " + READ_CHAPTER_NAME + " FROM " + READ_TABLE;
            String query = "SELECT " + READ_CHAPTER_ID + ", " + READ_CHAPTER_NAME + " FROM " + READ_TABLE + " WHERE " + READ_COMIC_ID + " = ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{comic_id + ""});
//            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    mChapter mChapter = new mChapter();
                    mChapter.setChapter_id(Integer.parseInt(cursor.getString(0)));
                    mChapter.setName(cursor.getString(1));
                    mChapters.add(mChapter);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return mChapters;
    }

    @Override
    public boolean isExists(Integer integer) {
        return false;
    }
}
