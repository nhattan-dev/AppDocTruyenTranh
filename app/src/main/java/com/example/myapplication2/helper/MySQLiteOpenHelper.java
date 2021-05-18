package com.example.myapplication2.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "ComicDB";

    protected final static String CHAPTER_TABLE = "Chapter";
    protected final static String CHAPTER_ID = "chapter_id";
    protected final static String CHAPTER_NAME = "name";
    protected final static String CHAPTER_COMIC_ID = "comic_id";

    protected final static String READ_TABLE = "Read";
    protected final static String READ_CHAPTER_ID = "chapter_id";
    protected final static String READ_CHAPTER_NAME = "name";
    protected final static String READ_COMIC_ID = "comic_id";

    protected final static String IMAGE_TABLE = "Image";
    protected final static String IMAGE_CHAPTER_ID = "chapter_id";
    protected final static String IMAGE_ID = "image_id";
    protected final static String IMAGE_BLOB = "image_blob";

    private final static int VERSION = 1;
    private Context context;

    private String CREATE_CHAPTER_TABLE_QUERY = "CREATE TABLE " + CHAPTER_TABLE +
            "("+
            CHAPTER_ID + " INTEGER PRIMARY KEY, " +
            CHAPTER_NAME + " TEXT, " +
            CHAPTER_COMIC_ID + " INTEGER " +
            ")";

    private String CREATE_READ_TABLE_QUERY = "CREATE TABLE " + READ_TABLE +
            "("+
            READ_CHAPTER_ID + " INTEGER PRIMARY KEY, " +
            READ_CHAPTER_NAME + " TEXT, " +
            READ_COMIC_ID + " INTEGER " +
            ")";

    private String CREATE_IMAGE_TABLE_QUERY = "CREATE TABLE " + IMAGE_TABLE +
            "("+
            IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            IMAGE_CHAPTER_ID + " INTEGER, " +
            IMAGE_BLOB + " BLOB," +
            " CONSTRAINT fk_chapter FOREIGN KEY (" + IMAGE_CHAPTER_ID + ") REFERENCES " + CHAPTER_TABLE + "(" + CHAPTER_ID + ") ON DELETE CASCADE" +
            ")";

    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CHAPTER_TABLE_QUERY);
        db.execSQL(CREATE_READ_TABLE_QUERY);
        db.execSQL(CREATE_IMAGE_TABLE_QUERY);
        Log.e("OnCreate()","");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void execQuery(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getCursor(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
