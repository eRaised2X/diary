package com.eraisedtox94.smartdiary;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TabFragment1 extends Fragment implements View.OnClickListener{

    private Button btnSaveEntry;

    private View tabview;

    private EditText etTitle;
    private EditText etContent;

    private MyCalendarClass mMyCalendarClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabview = inflater.inflate(R.layout.fragment_note_plain_editor, container, false);

        initialise();
        setListeners();
        return tabview;
    }


    public void initialise(){
        etTitle  = (EditText) tabview.findViewById(R.id.et_title_diary);
        etContent  = (EditText) tabview.findViewById(R.id.et_content_diary);
        btnSaveEntry = (Button)tabview.findViewById(R.id.btnSave);

        mMyCalendarClass = MyCalendarClass.getInstance();
    }


    public void setListeners(){
        btnSaveEntry.setOnClickListener(this);
    }

    private void saveDiaryEntryToDb() {

        String titleString = etTitle.getText().toString();
        String contentString = etContent.getText().toString();
        String dateCreatedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();
        String dateModifiedString = "1/9/99";

        Log.d("formatted date",dateCreatedString);

        // only save if either summary or description
        // is available

        //if (description.length() == 0 && summary.length() == 0) {
          //  return;
        //}

        ContentValues values = new ContentValues();
        //values.put(DiaryEntryTableUtil.COLUMN_ID, id);
        values.put(DiaryEntryTableUtil.COLUMN_TITLE, titleString);
        values.put(DiaryEntryTableUtil.COLUMN_CONTENTS, contentString);
        values.put(DiaryEntryTableUtil.COLUMN_DATE_CREATED, dateCreatedString);
        values.put(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED, dateModifiedString);

        getContext().getContentResolver().insert(
                    DiaryEntryContentProvider.CONTENT_URI, values);
    }

    @Override
    public void onClick(View view) {
        saveDiaryEntryToDb();
    }
}