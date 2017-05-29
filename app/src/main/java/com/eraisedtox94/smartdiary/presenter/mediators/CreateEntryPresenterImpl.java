package com.eraisedtox94.smartdiary.presenter.mediators;

import android.util.Log;

import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.presenter.util.IPresenterContract;
import com.eraisedtox94.smartdiary.presenter.util.ReadWriteFileAsyncTask;
import com.eraisedtox94.smartdiary.view.util.IViewContract;

/**
 * Created by spraful on 23-May-17.
 */

public class CreateEntryPresenterImpl implements IPresenterContract.ICreateNewEntryPresenter{

    IViewContract.ICreateNewEntryView createEntryView;
    ReadWriteFileAsyncTask readWriteFileAsyncTask;

    public CreateEntryPresenterImpl() {
        Log.d("ctor createEntryImpl", "called");
    }

    @Override
    public void attachView(IViewContract.ICreateNewEntryView view) {
        this.createEntryView = view;
    }

    @Override
    public void handleSaveEntry() {
        createEntryView.handleClickOfSave();
    }

    @Override
    public void readFile(String fileName) {
        if (fileName == null) {
            return ;
        }
        Log.d("readFile","reached");
        //appPrefsManager = new IAppPrefsManagerImpl();
        //mAppPrefsManagerImpl.setLastOpenedFileIdInsideSharedPref(fileName);
        readWriteFileAsyncTask = new ReadWriteFileAsyncTask();
        readWriteFileAsyncTask.execute(fileName, AppUtils.READ_FLAG);

    }


    @Override
    public void writeFile(String fileName,String content) {
        if (fileName == null) {
            return ;
        }
        readWriteFileAsyncTask = new ReadWriteFileAsyncTask();
        readWriteFileAsyncTask.execute(fileName, AppUtils.WRITE_FLAG,content);
    }

    @Override
    public void clearPageContent() {
        createEntryView.clearPage();
    }

    /*public void fileReadCallback(String content){
        Log.d("this is callback","reached");
        if(createEntryView == null){
            Log.d("createEntryView is null","reached");
        }
        createEntryView.setContentReadFromFile(content);

    }*/

}
