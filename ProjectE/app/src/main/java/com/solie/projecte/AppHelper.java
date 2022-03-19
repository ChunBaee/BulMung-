package com.solie.projecte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.RequestQueue;

public class AppHelper {

    public static RequestQueue requestQueue;
    public static String host = "boostcourse-appapi.connect.or.kr";
    public static int port = 10000;

    public static String thumbnailHost1 = "https://img.youtube.com/vi/";
    public static String thumbnailHost2 = "/0.jpg";

    public static String youtubeHost = "https://youtu.be/";
}
