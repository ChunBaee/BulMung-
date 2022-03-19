package com.solie.projectf.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHelper {
    public static SQLiteDatabase database;

    public static void openDatabase (Context context, String databaseName) {
        try {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            if(database != null) {
                Log.d("openDatabase()","데이터베이스 " + databaseName + "오픈되었습니다.");

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
