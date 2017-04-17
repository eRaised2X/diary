package com.eraisedtox94.smartdiary;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by spraful on 4/9/2017.
 */

public class DiaryEntryContentProvider extends ContentProvider {

    private DatabaseHelper dbHelper;
    private static final int ENTRY = 100;
    private static final int ENTRIES = 101;

    private static final String AUTHORITY = "com.eraisedtox94.smartdiary.data.provider";
    private static final String BASE_PATH = "entries";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);


    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/entries";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/entry";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ENTRIES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ENTRY);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);
        queryBuilder.setTables(DiaryEntryTableUtil.TABLE_DIARY_ENTRIES);

        int type = sURIMatcher.match(uri);
        switch (type){
            case ENTRY:
                //there is nothing to do if the query is for the table
                break;
            case ENTRIES:
                //queryBuilder.appendWhere(Constants.COLUMN_ID + " = " + 1);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int type = sURIMatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = 0;
        switch (type){
            case ENTRIES:
                id = db.insert(DiaryEntryTableUtil.TABLE_DIARY_ENTRIES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case ENTRIES:
                rowsDeleted = sqlDB.delete(DiaryEntryTableUtil.TABLE_DIARY_ENTRIES, selection,
                        selectionArgs);
                break;
            case ENTRY:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            DiaryEntryTableUtil.TABLE_DIARY_ENTRIES,
                            DiaryEntryTableUtil.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            DiaryEntryTableUtil.TABLE_DIARY_ENTRIES,
                            DiaryEntryTableUtil.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case ENTRIES:
                rowsUpdated = sqlDB.update(DiaryEntryTableUtil.TABLE_DIARY_ENTRIES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ENTRY:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DiaryEntryTableUtil.TABLE_DIARY_ENTRIES,
                            values,
                            DiaryEntryTableUtil.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DiaryEntryTableUtil.TABLE_DIARY_ENTRIES,
                            values,
                            DiaryEntryTableUtil.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }



    private void checkColumns(String[] projection) {
        String[] available = { DiaryEntryTableUtil.COLUMN_DATE_CREATED,
                DiaryEntryTableUtil.COLUMN_CONTENTS, DiaryEntryTableUtil.COLUMN_TITLE,
                DiaryEntryTableUtil.COLUMN_ID,DiaryEntryTableUtil.COLUMN_DATE_MODIFIED};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }
}
