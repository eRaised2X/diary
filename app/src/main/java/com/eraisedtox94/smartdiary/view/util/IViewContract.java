package com.eraisedtox94.smartdiary.view.util;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by spraful on 23-May-17.
 */

public interface IViewContract {

    interface IListAllEntriesView {
        void showList();
        Context getContext();
        void notifyChange();
        void setTheAdapter(Cursor data);
        void deleteListItems(String []ids);
        void switchToTab(int index);
    }

    interface ICreateNewEntryView {
        void clearPage();
        void setContentReadFromFile(String data);
        void handleClickOfSave();
        void putEmoji(int emojiId);
    }

}
