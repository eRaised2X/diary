package com.eraisedtox94.smartdiary;

import android.util.Log;

/**
 * Created by spraful on 19-Apr-17.
 */

public class MyUtilityClass {

    private int fileNameGeneratorInt = 0;
    public void WriteLog(Class className ,String str){
        Log.d(className.getName(),str);
    }

    public String FileNameForNextFileBeingCreated(){
        fileNameGeneratorInt++;
        return fileNameGeneratorInt+".txt";
    }
}
