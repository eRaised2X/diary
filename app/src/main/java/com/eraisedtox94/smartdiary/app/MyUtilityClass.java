package com.eraisedtox94.smartdiary.app;

import android.util.Log;

/**
 * Created by spraful on 19-Apr-17.
 */

public class MyUtilityClass {
    //custom method for writing log to console
    public void WriteLog(Class className ,String log_string){
        Log.d(className.getName(), log_string);
    }
}
