package com.eraisedtox94.smartdiary;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TabFragment1 extends Fragment {

    Button btnCreateEntry;
    NoteManager mNoteManager;
    DiaryEntry mDiaryEntry;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_plain_editor, container, false);
        btnCreateEntry = (Button)view.findViewById(R.id.btnSave);
        btnCreateEntry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveState();
            }
        });
        return view;
    }



    private void saveState() {
        String title = "title";//(String) mCategory.getSelectedItem();
        String content = "content";//mTitleText.getText().toString();
        int id = 1;//mBodyText.getText().toString();
        String dateCreated = "11/2/12";
        String dateModified = "1/9/99";

        // only save if either summary or description
        // is available

        //if (description.length() == 0 && summary.length() == 0) {
          //  return;
        //}

        ContentValues values = new ContentValues();
        values.put(DiaryEntryTableUtil.COLUMN_ID, id);
        values.put(DiaryEntryTableUtil.COLUMN_TITLE, title);
        values.put(DiaryEntryTableUtil.COLUMN_CONTENTS, content);
        values.put(DiaryEntryTableUtil.COLUMN_DATE_CREATED, dateCreated);
        values.put(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED, dateModified);

        //if (todoUri == null) {
            // New todo
            Uri todoUri =  getContext().getContentResolver().insert(
                    DiaryEntryContentProvider.CONTENT_URI, values);
        //} else {
          //  // Update todo
            //getContext().getContentResolver().update(todoUri, values, null, null);
        //}
    }
}