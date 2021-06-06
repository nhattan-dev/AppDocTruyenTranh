package com.example.myapplication2.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication2.activity.Home;
import com.example.myapplication2.helper.MySQLiteOpenHelper;
import com.example.myapplication2.m_interface.CRUD;
import com.example.myapplication2.model.Property;
import com.example.myapplication2.model.mChapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PropertyDAO extends MySQLiteOpenHelper implements CRUD<Property, Integer> {
    SQLiteDatabase db;
    protected static String defaultUpdateTime = "2021-05-23T13:16:00";
    Context context;
    public final static int property_ID = 1;

    public PropertyDAO(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    @Override
    public boolean insert(Property property) {
        int result = -1;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROPERTY_ID, property.getID());
            values.put(PROPERTY_LASTTIME, property.getUpdate_time());
            result = (int) db.insert(PROPERTY_TABLE, null, values);
            return result == property_ID;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return false;
    }

    @Override
    public boolean delete(Property property) {
        return false;
    }

    @Override
    public boolean edit(Property property) {
        int result = -1;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROPERTY_ID, property.getID());
            values.put(PROPERTY_LASTTIME, property.getUpdate_time());

            result = (int) db.update(PROPERTY_TABLE, values, PROPERTY_ID + " = ?", new String[]{property.getID() + ""});
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return false;
    }

    @Override
    public Property select(int property_ID) {
        Property property = new Property();
        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + PROPERTY_ID + ", " + PROPERTY_LASTTIME
                    + " FROM " + PROPERTY_TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                property.setID(cursor.getInt(0));
                property.setUpdate_time(cursor.getString(1));
            } else {
                defaultUpdateTime = new SimpleDateFormat(Home.patternSimpleDateFormat).format(new Date());
                property = new Property(property_ID, defaultUpdateTime);
                if (!insert(property)) {
                    Toast.makeText(context, "DEFAULT VALUE INITALIZATION FAILED !", Toast.LENGTH_SHORT).show();
                }else {
                    select(property_ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

    @Override
    public ArrayList<Property> selectAll(Integer integer) {
        return null;
    }

    @Override
    public boolean isExists(Integer integer) {
        return false;
    }
}
