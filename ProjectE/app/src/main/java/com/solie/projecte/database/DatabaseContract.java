package com.solie.projecte.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static class pageNum implements BaseColumns {
        public static final String TABLE_NAME = "pageNum";
        public static final String COLUMN_NAME_NUMOF = "NUM_OF";
    }

    public static class pageData implements BaseColumns {
        public static final String TABLE_NAME = "pageData";
        public static final String COLUMN_NAME_pageImage = "pageImage";
        public static final String COLUMN_NAME_pageName = "pageName";
        public static final String COLUMN_NAME_pageRate = "pageRate";
        public static final String COLUMN_NAME_pageGrade = "pageGrade";
    }

    public static class infoData implements BaseColumns {
        public static final String TABLE_NAME = "infoData";
        public static final String COLUMN_NAME_infoImage = "infoImage";
        public static final String COLUMN_NAME_infoName = "infoName";
        public static final String COLUMN_NAME_infoGrade = "infoGrade";
        public static final String COLUMN_NAME_infoDate = "infoDate";
        public static final String COLUMN_NAME_infoGenre = "infoGenre";
        public static final String COLUMN_NAME_infoGood = "infoGood";
        public static final String COLUMN_NAME_infoBad = "infoBad";
        public static final String COLUMN_NAME_infoReservationGrade = "infoReservationGrade";
        public static final String COLUMN_NAME_infoReservationRate = "infoReservationRate";
        public static final String COLUMN_NAME_infoRating = "infoRating";
        public static final String COLUMN_NAME_infoAudience = "infoAudience";
        public static final String COLUMN_NAME_infoSynopsis = "infoSynopsis";
        public static final String COLUMN_NAME_infoDirector = "infoDirector";
        public static final String COLUMN_NAME_infoActor = "infoActor";
    }
}
