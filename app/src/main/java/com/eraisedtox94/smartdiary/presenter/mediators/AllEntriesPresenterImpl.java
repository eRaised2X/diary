package com.eraisedtox94.smartdiary.presenter.mediators;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.model.EventPassMsgToOtherPresenter;
import com.eraisedtox94.smartdiary.presenter.util.ReadWriteFileAsyncTask;
import com.eraisedtox94.smartdiary.view.main.FragmentListOfEntries;
import com.eraisedtox94.smartdiary.view.util.IViewContract;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by spraful on 18-May-17.
 */

public class AllEntriesPresenterImpl implements IPresenterContract.IAllEntriesPresenter,
        LoaderManager.LoaderCallbacks<Cursor> {

    IViewContract.IListAllEntriesView view;
    LoaderManager loaderManager;
    IAppPrefsManager appPrefsManager;

    ReadWriteFileAsyncTask readWriteFileAsyncTask ;

    public AllEntriesPresenterImpl(LoaderManager loaderManager,IAppPrefsManager appPrefsManager){
        this.loaderManager = loaderManager;
        this.appPrefsManager = appPrefsManager;
    }



    @Override
    public void setView(IViewContract.IListAllEntriesView view) {
        this.view = view;
    }


    @Override
    public void fillViewWithListOfEntries() {
        //call createEntryView method from Frag here
        loaderManager.initLoader(0, null,this);
        view.showList();
    }

    @Override
    public void listItemClickListener(String fileName) {
        Log.d("event bus","coming into play");

        EventPassMsgToOtherPresenter event = new EventPassMsgToOtherPresenter();
        event.setFileName(fileName);
        EventBus.getDefault().post(event);
    }


    @Override
    public void asyncTaskDoneCallback(String s){
        //Todo
    }
    @Override
    public void listItemLongClickListener(String[] fileNames) {
        Log.d("event bus","might be used here");
        if(view == null){
            view = FragmentListOfEntries.newInstance();
        }
        view.deleteListItems(fileNames);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { DiaryEntryTableUtil.COLUMN_ID,DiaryEntryTableUtil.COLUMN_TITLE, DiaryEntryTableUtil.COLUMN_DATE_CREATED };
        CursorLoader cursorLoader = new CursorLoader(view.getContext(),
                DiaryEntryContentProvider.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        view.setTheAdapter(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.notifyChange();
    }

}