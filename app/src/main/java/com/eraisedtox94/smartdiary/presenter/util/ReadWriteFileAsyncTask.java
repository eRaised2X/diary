package com.eraisedtox94.smartdiary.presenter.util;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.eraisedtox94.smartdiary.app.AppConstants;
import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.model.FileReadWriteUtil;
import com.eraisedtox94.smartdiary.presenter.mediators.CreateEntryPresenterImpl;
import com.eraisedtox94.smartdiary.presenter.mediators.IPresenterContract;

import java.io.File;

/**
 * Created by spraful on 24-May-17.
 */


//class async task to do file read write in background
public class ReadWriteFileAsyncTask extends AsyncTask<String, Integer, String> {

    //TODO remove any type of hardcoding and make async task re usable n loosely coupled
    private FileReadWriteUtil fileReadWriteUtil = new FileReadWriteUtil();
    private IPresenterContract.ICreateNewEntryPresenter createNewEntryPresenter;
    //private CreateEntryPresenterImpl createNewEntryPresenter;
    private IPresenterContract.IAllEntriesPresenter allEntriesPresenter;
    //default is read
    String type_Of_Flag = "0";

    public ReadWriteFileAsyncTask(IPresenterContract.ICreateNewEntryPresenter createNewEntryPresenter){
        this.createNewEntryPresenter = createNewEntryPresenter;
    }

    public ReadWriteFileAsyncTask(IPresenterContract.IAllEntriesPresenter allEntriesPresenter){
        this.allEntriesPresenter =  allEntriesPresenter;
    }

    @Override
    protected String doInBackground(String... strings) {

        type_Of_Flag = strings[1];
        String contentToBeWritten="";
        String contentRead = "Seems nothing is there :(";

        //check for sd card's presence
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("reached", "inside async task");
            String fileName = strings[0] + AppUtils.TEXT_FILE_EXTENSION;
            String folderName = AppConstants.nameOfFolderContainingEntries;

            File rootFolder = new File(Environment.getExternalStorageDirectory(), folderName);

            if(type_Of_Flag.equals(AppUtils.READ_FLAG)){
                //check if file even exists
                if (!checkIfFileExists(fileName)) {
                    Log.d("file reading stub", "Aww ....!! resource not found :(");
                    return contentRead;
                }
                Log.d("async read","reached");
                String path = Environment.getExternalStorageDirectory().getPath() + "/" + AppConstants.nameOfFolderContainingEntries + "/" + fileName;
                File file = new File(path);
                contentRead =  fileReadWriteUtil.readContentFromFileInExternalStorage(file);
                return contentRead;
            }
            else if(type_Of_Flag.equals(AppUtils.WRITE_FLAG)){
                if (!rootFolder.exists()) {
                    rootFolder.mkdirs(); // this will create folder if it doesn't exist.
                }
                contentToBeWritten = strings[2];
                File file = new File(rootFolder,fileName);
                fileReadWriteUtil.writeContentToFileInExternalStorage(file,contentToBeWritten);
            }

        }
        return contentRead;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(type_Of_Flag.equals(AppUtils.READ_FLAG)){
            //progressBar.setVisibility(View.INVISIBLE);
            Log.d("check post execute=", "here is string=" + s);
            //inform presenter that you are done
            //Todo here we can have some good model
            if(allEntriesPresenter==null){
                createNewEntryPresenter.asyncTaskDoneCallback(s);
            }
            else{
                allEntriesPresenter.asyncTaskDoneCallback(s);
            }
        }
        else{
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }



    //method definition to check for file existence
    public boolean checkIfFileExists(String file_name) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/" + AppConstants.nameOfFolderContainingEntries + "/" + file_name;
        File file = new File(path);
        Log.d("myCustomDebug", "this is path where we check for file existence " + path);
        return file.exists();
    }

}