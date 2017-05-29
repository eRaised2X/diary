package com.eraisedtox94.smartdiary.presenter.mediators;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.presenter.util.IAppPrefsManager;
import com.eraisedtox94.smartdiary.presenter.util.IPresenterContract;
import com.eraisedtox94.smartdiary.presenter.util.ReadWriteFileAsyncTask;
import com.eraisedtox94.smartdiary.view.util.IViewContract;

/**
 * Created by spraful on 18-May-17.
 */

public class AllEntriesPresenterImpl implements IPresenterContract.IAllEntriesPresenter,
        LoaderManager.LoaderCallbacks<Cursor> {

    IViewContract.IListAllEntriesView view;
    LoaderManager loaderManager;

    ReadWriteFileAsyncTask readWriteFileAsyncTask ;

    //todo this constructor breaks the purpose of enforcing developer to pass parameters
    public AllEntriesPresenterImpl(){

    }

    public AllEntriesPresenterImpl(LoaderManager loaderManager,IAppPrefsManager IAppPrefsManager){
        this.loaderManager = loaderManager;
    }

    @Override
    public void fillViewWithListOfEntries() {
        //call createEntryView method from Frag here
        loaderManager.initLoader(0, null,this);
        view.showList();
    }

    @Override
    public void attachView(IViewContract.IListAllEntriesView view) {
        this.view = view;
    }



    @Override
    public void listItemClickListener(String fileName) {
        Log.d("event bus","can come into play");
        //todo you are creating a new instance on each click ..which is bad like hell
        readWriteFileAsyncTask = new ReadWriteFileAsyncTask();
        readWriteFileAsyncTask.execute(fileName, AppUtils.READ_FLAG);
    }

    @Override
    public void listItemLongClickListener(String[] fileNames) {
        Log.d("event bus","should be here");
        //view.deleteListItems(fileNames);
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