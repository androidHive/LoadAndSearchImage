package com.example.searchimage.DatabaseHelperClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqliteopenhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyImgDB.db";
    String ImgTable = "CREATE TABLE IF NOT EXISTS ImgTable(ImgId text, Comment text)";

    public Sqliteopenhelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    public Sqliteopenhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ImgTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ImgTable");
        db.execSQL(ImgTable);
    }
}