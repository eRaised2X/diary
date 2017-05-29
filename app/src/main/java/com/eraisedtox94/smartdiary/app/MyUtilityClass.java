package com.eraisedtox94.smartdiary.app;

import android.util.Log;

/**
 * Created by spraful on 19-Apr-17.
 */

public class MyUtilityClass {

    //private int fileNameGeneratorInt = 0;

    /*
    public String FileNameForNextFileBeingCreated(){
        fileNameGeneratorInt++;
        return fileNameGeneratorInt+".txt";
    }*/

    public void WriteLog(Class className ,String log_string){
        Log.d(className.getName(), log_string);
    }
}
