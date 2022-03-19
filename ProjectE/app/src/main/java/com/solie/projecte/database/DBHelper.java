package com.solie.projecte.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.solie.projecte.activity.MainActivity;

import static com.solie.projecte.activity.MainActivity.database;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper sInstance;
    private static final String databaseName = "CinemaHeaven";
    private static final int databaseVersion = 1;

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    private static final String createPageNum = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.pageNum.TABLE_NAME +
            "(" +
            DatabaseContract.pageNum._ID + " integer PRIMARY KEY autoincrement, " +
            DatabaseContract.pageNum.COLUMN_NAME_NUMOF + " integer " +
            ")";

    private static final String createPageData = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.pageData.TABLE_NAME +
            "(" +
            DatabaseContract.pageData._ID + " integer PRIMARY KEY autoincrement, " +
            DatabaseContract.pageData.COLUMN_NAME_pageImage + " text, " +
            DatabaseContract.pageData.COLUMN_NAME_pageName + " text, " +
            DatabaseContract.pageData.COLUMN_NAME_pageRate + " float, " +
            DatabaseContract.pageData.COLUMN_NAME_pageGrade + " integer" +
            ")";

    private static final String createInfoData = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.infoData.TABLE_NAME +
            "(" +
            DatabaseContract.infoData._ID + " integer PRIMARY KEY autoincrement, " +
            DatabaseContract.infoData.COLUMN_NAME_infoImage + " text, " +
            DatabaseContract.infoData.COLUMN_NAME_infoName + " text, " +
            DatabaseContract.infoData.COLUMN_NAME_infoGrade + " integer, " +
            DatabaseContract.infoData.COLUMN_NAME_infoDate + " text, " +
            DatabaseContract.infoData.COLUMN_NAME_infoGenre + " text, " +
            DatabaseContract.infoData.COLUMN_NAME_infoGood + " integer, " +
            DatabaseContract.infoData.COLUMN_NAME_infoBad + " integer, " +
            DatabaseContract.infoData.COLUMN_NAME_infoReservationGrade + " integer, " +
            DatabaseContract.infoData.COLUMN_NAME_infoReservationRate + " float, " +
            DatabaseContract.infoData.COLUMN_NAME_infoRating + " float, " +
            DatabaseContract.infoData.COLUMN_NAME_infoAudience + " integer, " +
            DatabaseContract.infoData.COLUMN_NAME_infoSynopsis + " text, " +
            DatabaseContract.infoData.COLUMN_NAME_infoDirector + " text, " +
            DatabaseContract.infoData.COLUMN_NAME_infoActor + " text " +
            ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPageNum);
        db.execSQL(createPageData);
        db.execSQL(createInfoData);
    }

    public static void insertPageNumData(int NUM_OF_FRAGMENTS) {
        if (database != null) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.pageNum.COLUMN_NAME_NUMOF, NUM_OF_FRAGMENTS);

            database.insert(DatabaseContract.pageNum.TABLE_NAME, null, values);
        }
    }

    public static void insertPageData(String pageImage, String pageName, float pageRate, int pageGrade) {
        if (MainActivity.database != null) {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.pageData.COLUMN_NAME_pageImage, pageImage);
            values.put(DatabaseContract.pageData.COLUMN_NAME_pageName, pageName);
            values.put(DatabaseContract.pageData.COLUMN_NAME_pageRate, pageRate);
            values.put(DatabaseContract.pageData.COLUMN_NAME_pageGrade, pageGrade);

            database.insert(DatabaseContract.pageData.TABLE_NAME, null, values);
        }
    }

    public static void insertPageInfoData(String infoImage, String infoName, int infoGrade, String infoDate, String infoGenre, int infoGood, int infoBad, int infoReservationGrade, float infoReservationRate, float infoRating, int infoAudience, String infoSynopsis, String infoDirector, String infoActor) {
        if (MainActivity.database != null) {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoImage, infoImage);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoName, infoName);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoGrade, infoGrade);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoDate, infoDate);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoGenre, infoGenre);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoGood, infoGood);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoBad, infoBad);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoReservationGrade, infoReservationGrade);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoReservationRate, infoReservationRate);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoRating, infoRating);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoAudience,infoAudience);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoSynopsis, infoSynopsis);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoDirector, infoDirector);
            values.put(DatabaseContract.infoData.COLUMN_NAME_infoActor, infoActor);

            database.insert(DatabaseContract.infoData.TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
