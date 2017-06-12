package com.eraisedtox94.smartdiary.presenter.mediators;

import android.util.Log;

import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.model.EventPassMsgToOtherPresenter;
import com.eraisedtox94.smartdiary.presenter.util.ReadWriteFileAsyncTask;
import com.eraisedtox94.smartdiary.view.main.FragmentCreateNewEntry;
import com.eraisedtox94.smartdiary.view.util.IViewContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by spraful on 23-May-17.
 */

public class CreateEntryPresenterImpl implements IPresenterContract.ICreateNewEntryPresenter{

    IViewContract.ICreateNewEntryView createEntryView;
    ReadWriteFileAsyncTask readWriteFileAsyncTask;
    IAppPrefsManager appPrefsManager;

    public CreateEntryPresenterImpl(IAppPrefsManager appPrefsManager) {
        Log.d("ctor createEntryImpl", "called");
        this.appPrefsManager = appPrefsManager;
        EventBus.getDefault().register(this);
    }


    //don't get swept by the warning that onEvent in never used, because, it is indeed
    @Subscribe
    public void onEvent(EventPassMsgToOtherPresenter event){
        Log.d("onEvent","event fired");
        readFile(event.getFileName());
    }


    @Override
    public void setView(IViewContract.ICreateNewEntryView view) {
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
        appPrefsManager.setLastOpenedFileIdInsideSharedPref(fileName);
        readWriteFileAsyncTask = new ReadWriteFileAsyncTask(this);
        readWriteFileAsyncTask.execute(fileName, AppUtils.READ_FLAG);

    }


    @Override
    public void writeFile(String fileName,String content) {
        if (fileName == null) {
            return ;
        }
        readWriteFileAsyncTask = new ReadWriteFileAsyncTask(this);
        readWriteFileAsyncTask.execute(fileName, AppUtils.WRITE_FLAG,content);
    }

    @Override
    public void clearPageContent() {
        createEntryView.clearPage();
    }

    @Override
    public void asyncTaskDoneCallback(String content){
        Log.d("this is callback","reached");
        if(createEntryView == null){
            Log.d("createEntryView is null","reached");
            createEntryView = FragmentCreateNewEntry.newInstance();
        }
        Log.d("createEntryView ","not null");
        createEntryView.setContentReadFromFile(content);

    }

    @Override
    public void handleEmojiClicked(int emojiIdentifier) {
        if(createEntryView == null){
            Log.d("createEntryView-gotNull","here");
            createEntryView = FragmentCreateNewEntry.newInstance();
        }
        Log.d("createEntryView "," here not null");
        createEntryView.putEmoji(emojiIdentifier);
    }
}