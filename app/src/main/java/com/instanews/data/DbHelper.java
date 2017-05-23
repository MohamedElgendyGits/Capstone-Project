package com.instanews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String NAME = "InstaNewsApp.db";
    private static final int VERSION = 1;


    DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + Contract.NewsContract.TABLE_NAME + " ("
                + Contract.NewsContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.NewsContract.COLUMN_NEWS_TITLE + " TEXT NOT NULL,"
                + Contract.NewsContract.COLUMN_NEWS_DESCRIPTION + " TEXT NOT NULL,"
                + Contract.NewsContract.COLUMN_NEWS_AUTHOR + " TEXT NOT NULL,"
                + Contract.NewsContract.COLUMN_PUBLISHED_DATE + " TEXT NOT NULL,"
                + Contract.NewsContract.COLUMN_NEWS_JSON + " TEXT NOT NULL" +
                ");";

        db.execSQL(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Contract.NewsContract.TABLE_NAME);

        onCreate(db);
    }
}

