package com.eraisedtox94.smartdiary.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;

/**
 * Created by spraful on 4/8/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DiaryEntryTableUtil.DATABASE_NAME, null, DiaryEntryTableUtil.DATABASE_VERSION);

    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        DiaryEntryTableUtil.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        DiaryEntryTableUtil.onUpgrade(database, oldVersion, newVersion);
    }

}