package com.eraisedtox94.smartdiary;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by spraful on 14-Apr-17.
 */

public class DiaryEntryTableUtil {
    // Database table
    public static final String TABLE_DIARY_ENTRIES = "diary_entry_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENTS = "contents";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_DATE_MODIFIED = "date_modified";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DIARY_ENTRIES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_CONTENTS + " text not null,"
            + COLUMN_DATE_CREATED + " text not null,"
            + COLUMN_DATE_MODIFIED + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(DiaryEntryTableUtil.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY_ENTRIES);
        onCreate(database);
    }
}
