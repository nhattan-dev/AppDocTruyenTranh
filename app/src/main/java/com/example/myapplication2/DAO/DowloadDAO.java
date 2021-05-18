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

public class DowloadDAO extends MySQLiteOpenHelper implements CRUD<mChapter, Integer> {

    SQLiteDatabase db;

    public DowloadDAO(Context context) {
        super(context);
    }

    @Override
    public boolean insert(mChapter mChapter) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CHAPTER_ID, mChapter.getChapter_id());
            values.put(CHAPTER_NAME, mChapter.getName());
            values.put(CHAPTER_COMIC_ID, mChapter.getComic_id());
            result = (int) db.insert(CHAPTER_TABLE, null, values);
            if (result == -1)
                return false;
            for (byte[] url : mChapter.getBlobs()) {
                values = new ContentValues();
                values.put(IMAGE_CHAPTER_ID, result);
                values.put(IMAGE_BLOB, url);
                db.insert(IMAGE_TABLE, null, values);
            }
            return true;
        } catch (Exception e) {
            if (result != -1) {
                delete(mChapter);
            }
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        Log.e("insert", "" + result);
        return false;
    }

    @Override
    public boolean delete(mChapter mChapter) {
        try {
            db = this.getWritableDatabase();
            int result = db.delete(IMAGE_TABLE, IMAGE_CHAPTER_ID + " = " + mChapter.getChapter_id(), null);
            if (result > 0) {
                result = db.delete(CHAPTER_TABLE, CHAPTER_ID + " = " + mChapter.getChapter_id(), null);
                return result > 0 ? true : false;
            }
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
        mChapter mChapter = new mChapter();
        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + IMAGE_BLOB + ", " + CHAPTER_NAME
                    + " FROM " + IMAGE_TABLE
                    + " INNER JOIN " + CHAPTER_TABLE
                    + " ON " + CHAPTER_TABLE + "." + CHAPTER_ID + " = " + IMAGE_TABLE + "." + IMAGE_CHAPTER_ID
                    + " WHERE " + IMAGE_TABLE + "." + IMAGE_CHAPTER_ID + " = ? " +
                    " ORDER BY " + IMAGE_ID + " ASC";
            Cursor cursor = db.rawQuery(query, new String[]{chapter_id + ""});
            ArrayList<byte[]> blobs = new ArrayList<>();
            String name = "";
            if (cursor.moveToFirst()) {
                name = cursor.getString(1);
                do {
                    blobs.add(cursor.getBlob(0));
                } while (cursor.moveToNext());
            }

            mChapter.setBlobs(blobs);
            mChapter.setName(name);
            mChapter.setChapter_id(chapter_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mChapter;
    }

    @Override
    public boolean isExists(Integer chapter_id) {
        try {
            String query = "SELECT " + CHAPTER_ID + " FROM " + CHAPTER_TABLE + " WHERE " + CHAPTER_ID + " = ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{chapter_id + ""});
            if (cursor.moveToFirst())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return false;
    }

    @Override
    public ArrayList<mChapter> selectAll(Integer comic_id) {
        ArrayList<mChapter> mChapters = new ArrayList<>();
        try {
//            String query = "SELECT " + CHAPTER_ID + ", " + CHAPTER_NAME + " FROM " + CHAPTER_TABLE;
            String query = "SELECT " + CHAPTER_ID + ", " + CHAPTER_NAME + " FROM " + CHAPTER_TABLE + " WHERE " + CHAPTER_COMIC_ID + " = ?";
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
}
