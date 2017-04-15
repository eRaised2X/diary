package com.eraisedtox94.smartdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spraful on 4/9/2017.
 */

public class NoteManager {

    private Context mContext;
    private static NoteManager sNoteManagerInstance = null;

    public static NoteManager newInstance(Context context){

        if (sNoteManagerInstance == null){
            sNoteManagerInstance = new NoteManager(context.getApplicationContext());
        }

        return sNoteManagerInstance;
    }

    private NoteManager(Context context){
        this.mContext = context.getApplicationContext();
    }

    public long create(DiaryEntry note) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TITLE, note.getTitle());
        values.put(Constants.COLUMN_CONTENT, note.getContent());
        values.put(Constants.COLUMN_CREATED_TIME, System.currentTimeMillis());
        values.put(Constants.COLUMN_MODIFIED_TIME, System.currentTimeMillis());
        Uri result = mContext.getContentResolver().insert(DiaryEntryContentProvider.CONTENT_URI, values);
        long id = Long.parseLong(result.getLastPathSegment());
        return id;
    }


    public List<DiaryEntry> getAllNotes() {
        List<DiaryEntry> diaryEntry = new ArrayList<DiaryEntry>();
        Cursor cursor = mContext.getContentResolver().query(DiaryEntryContentProvider.CONTENT_URI, Constants.COLUMNS, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                diaryEntry.add(DiaryEntry.getEntryfromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return diaryEntry;
    }


    public DiaryEntry getEntry(Long id) {
        DiaryEntry entry;
        Cursor cursor = mContext.getContentResolver().query(DiaryEntryContentProvider.CONTENT_URI,
                Constants.COLUMNS, Constants.COLUMN_ID + " = " + id, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            entry = DiaryEntry.getEntryfromCursor(cursor);
            return entry;
        }
        return null;
    }


    public void update(DiaryEntry note) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TITLE, note.getTitle());
        values.put(Constants.COLUMN_CONTENT, note.getContent());
        values.put(Constants.COLUMN_CREATED_TIME, System.currentTimeMillis());
        values.put(Constants.COLUMN_MODIFIED_TIME, System.currentTimeMillis());
        mContext.getContentResolver().update(DiaryEntryContentProvider.CONTENT_URI,
                values, Constants.COLUMN_ID  + "=" + note.getId(), null);

    }


    public void delete(DiaryEntry mDiaryEntry) {
        mContext.getContentResolver().delete(
                DiaryEntryContentProvider.CONTENT_URI, Constants.COLUMN_ID + "=" + mDiaryEntry.getId(), null);
    }


}