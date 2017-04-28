package com.eraisedtox94.smartdiary;

import android.content.ContentValues;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FragmentDiaryMain extends Fragment implements View.OnClickListener{

    MyUtilityClass myUtilityClass = new MyUtilityClass();
    private Button btnSaveEntry;
    private Button btnNewEntry;

    private View tabview;

    private EditText etTitle;
    private EditText etContent;

    private MyCalendarClass mMyCalendarClass;

    private Typeface typefaceforContentEditText;

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabview = inflater.inflate(R.layout.tab_frgmnt_diary, container, false);
        Log.d("life cycle FragMain","onCreate called");
        initialise();
        setListeners();
        //clearPage();

        return tabview;
    }

    public void writeToExternalFile(String file_name){

    }



    //method definition to check for file existence
    public boolean fileExistance(String fname) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/DiaryFiles/" + fname;
        File file = new File(path);
        //File file = getBaseContext().getFileStreamPath(fname);
        //Log.d("myCustomDebug","this is path where we check for file existence "+getFilesDir());
        Log.d("myCustomDebug","this is path where we check for file existence "+path);
        return file.exists();
    }


    public void initialise(){

        if(tabview==null){
            Log.d("culprit", "detected");
        }
        etTitle = (EditText) tabview.findViewById(R.id.et_title_diary);
        etContent  = (EditText) tabview.findViewById(R.id.et_content_diary);
        btnSaveEntry = (Button)tabview.findViewById(R.id.btnSave);
        btnNewEntry = (Button)tabview.findViewById(R.id.btnNew);
        btnSaveEntry.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar)tabview.findViewById(R.id.pbarDiaryMain);
        progressBar.setVisibility(View.INVISIBLE);

        //TODO refactoring required
        etContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length()!=0){
                    btnSaveEntry.setVisibility(View.VISIBLE);
                }
            }
        });
        // Loading Font Face
        typefaceforContentEditText = Typeface.createFromAsset(getContext().getAssets(), Constants.fontPathBradley);
        etContent.setTypeface(typefaceforContentEditText);
        mMyCalendarClass = MyCalendarClass.getInstance();
    }


    public void setListeners(){
        btnSaveEntry.setOnClickListener(this);
        btnNewEntry.setOnClickListener(this);
    }


    public void clearPage(){
        etTitle.setText("");
        etContent.setText("");
    }



    public void updateDiaryEntryOnStorage() {

    }


    public void writeIntoFileInExternalStorage(String filenameToBeCreated, String content){

        //String Content is a java string and can be as long as range of int hence like 8GB in size
        //should not be an issue
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            myUtilityClass.WriteLog(getClass(),"reached");
            //writing in external storage inside a folder
            File file = null;
            String filename = filenameToBeCreated+".txt";//myUtilityClass.FileNameForNextFileBeingCreated();
            String foldername = Constants.nameOfFolderContainingEntries;

            File rootFolder = new File(Environment.getExternalStorageDirectory(), foldername);

            //if file does not already exist
            //if(!fileExistance(filename)) {
                //if even folder does not exist
            if (!rootFolder.exists()) {
                rootFolder.mkdirs(); // this will create folder.
            }

            try {
                file = new File(rootFolder, filename);  // file path to save
                FileWriter fileWriter = new FileWriter(file);
                //using buffered writer is fastest in writing I think
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                myUtilityClass.WriteLog(getClass(), "my file exception caught ");
                e.printStackTrace();
            }
        }
    }




    public int readFromFileInExternalStorage(String filenameToBeReadFrom){
       AsynTaskToReadFromExternalDirectory asynTaskToReadFromExternalDirectory = new AsynTaskToReadFromExternalDirectory(filenameToBeReadFrom);
        asynTaskToReadFromExternalDirectory.execute();
        return 0;
    }


    public void handleSaveOfDiaryEntry(){

        String titleString = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String dateCreatedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();
        String dateModifiedString = "1/9/99";

        ContentValues values = new ContentValues();
        //values.put(DiaryEntryTableUtil.COLUMN_ID, id);
        values.put(DiaryEntryTableUtil.COLUMN_TITLE, titleString);
        //values.put(DiaryEntryTableUtil.COLUMN_FILENAME, "1");
        values.put(DiaryEntryTableUtil.COLUMN_DATE_CREATED, dateCreatedString);
        values.put(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED, dateModifiedString);

        Uri uri = getContext().getContentResolver().insert(
                                    DiaryEntryContentProvider.CONTENT_URI, values);
        writeIntoFileInExternalStorage(uri.getLastPathSegment(), content);

        Toast.makeText(getContext(),"FILE SAVED",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSave:
                handleSaveOfDiaryEntry();
                updateDiaryEntryOnStorage();
                btnSaveEntry.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnNew:
                clearPage();
                break;
            default:
                break;
        }
    }

    public void setContentInEditText(String content){
        Log.d("does it even work","setting et");
        etContent.setText(content);
    }



    public class AsynTaskToReadFromExternalDirectory extends AsyncTask<String, Integer, String> {


        private String filenameToBeReadFrom;

        public AsynTaskToReadFromExternalDirectory(String filenameToBeReadFrom) {
            this.filenameToBeReadFrom = filenameToBeReadFrom;
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer content = new StringBuffer();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.d("gonna read file", "inside async");
                //reading from file in external storage inside a folder

                File file = null;
                String filename = filenameToBeReadFrom + ".txt";
                String foldername = Constants.nameOfFolderContainingEntries;

                File rootFolder = new File(Environment.getExternalStorageDirectory(), foldername);

                //if file does not exist
                if (!fileExistance(filename)) {
                    Log.d("file reading stub", "awww.... resource not found :(");
                    return null;
                }


                String str;
                try {
                    file = new File(rootFolder, filename);  // file path to locate file
                    FileReader fileReader = new FileReader(file);
                    //using buffered reader/writer is fastest in reading/writing I think
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while ((str = bufferedReader.readLine()) != null) {
                        content.append(str + "\n");
                    }
                    Log.d("content read is:", content.toString());
                    bufferedReader.close();
                    //initialiseEditViewsAgain();
                    //clearPage();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d("file exception caught", "crap..again.");
                    e.printStackTrace();
                }
            }
            return content.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            Log.d("check post execute=", s);
            etContent.setText(s);

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

    }

}