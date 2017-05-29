package com.eraisedtox94.smartdiary.model;

/**
 * Created by spraful on 4/6/2017.
 */

public class DiaryEntry {
    private Long id;
    private String title;
    private String content;
    private String dateCreated;
    private String dataModified;

    //getters
    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public long getId() {
        return id;
    }


    //setters
    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDataModified(String dataModified) {
        this.dataModified = dataModified;
    }

    /*
    //TODO --to be used definitely
    public static DiaryEntry getEntryfromCursor(Cursor cursor){
        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setId(cursor.getLong(cursor.getColumnIndex(DiaryEntryTableUtil.COLUMN_ID)));
        diaryEntry.setTitle(cursor.getString(cursor.getColumnIndex(DiaryEntryTableUtil.COLUMN_TITLE)));
        diaryEntry.setDateCreated(cursor.getString(cursor.getColumnIndex(DiaryEntryTableUtil.COLUMN_DATE_CREATED)));
        diaryEntry.setDataModified(cursor.getString(cursor.getColumnIndex(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED)));
        return diaryEntry;
    }*/

}