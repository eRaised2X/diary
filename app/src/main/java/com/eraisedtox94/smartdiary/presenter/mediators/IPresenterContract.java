package com.eraisedtox94.smartdiary.presenter.mediators;

import com.eraisedtox94.smartdiary.view.util.IViewContract;

/**
 * Created by spraful on 23-May-17.
 */

public interface IPresenterContract {

    interface ICreateNewEntryPresenter {
        void setView(IViewContract.ICreateNewEntryView view);
        void clearPageContent();
        void handleSaveEntry();
        void readFile(String fileName);
        void writeFile(String fileName,String content);
        void asyncTaskDoneCallback(String content);
    }

    interface IAllEntriesPresenter{
        void fillViewWithListOfEntries();
        void setView(IViewContract.IListAllEntriesView view);
        void listItemClickListener(String fileId);
        void listItemLongClickListener(String []fileIDs);
        void asyncTaskDoneCallback(String s);
    }

}