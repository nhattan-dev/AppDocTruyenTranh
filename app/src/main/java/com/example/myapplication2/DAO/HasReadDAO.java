package com.example.myapplication2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication2.helper.MySQLiteOpenHelper;
import com.example.myapplication2.m_interface.CRUD;
import com.example.myapplication2.model.mChapter;

import java.util.ArrayList;

public class HasReadDAO extends MySQLiteOpenHelper implements CRUD<mChapter, Integer> {

    SQLiteDatabase db;

    public HasReadDAO(Context context) {
        super(context);
    }

    @Override
    public boolean insert(mChapter mChapter) {
        int result = -1;
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
        int result = -1;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(READ_CHAPTER_ID, mChapter.getChapter_id());
            values.put(READ_CHAPTER_NAME, mChapter.getName());
            values.put(READ_COMIC_ID, mChapter.getComic_id());
            values.put(READ_POSITION, mChapter.getPosition());

            result = (int) db.update(READ_TABLE, values, READ_CHAPTER_ID + " = ?", new String[]{mChapter.getChapter_id() + ""});
            if (result > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        Log.e("update", "" + result);
        return false;
    }

    @Override
    public mChapter select(int chapter_id) {
        mChapter mChapter = new mChapter();
        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + READ_POSITION
                    + " FROM " + READ_TABLE
                    + " WHERE " + READ_CHAPTER_ID + " = ? ";
            Cursor cursor = db.rawQuery(query, new String[]{chapter_id + ""});
            if (cursor.moveToFirst()) {
                mChapter.setPosition(cursor.getInt(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mChapter;
    }

    @Override
    public ArrayList<mChapter> selectAll(Integer comic_id) {
        ArrayList<mChapter> mChapters = new ArrayList<>();
        try {
//            String query = "SELECT " + READ_CHAPTER_ID + ", " + READ_CHAPTER_NAME + " FROM " + READ_TABLE;
            String query = "SELECT " + READ_CHAPTER_ID + ", " + READ_CHAPTER_NAME + " FROM " + READ_TABLE + " WHERE " + READ_COMIC_ID + " = ? ORDER BY " + READ_CHAPTER_NAME + " DESC";
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
