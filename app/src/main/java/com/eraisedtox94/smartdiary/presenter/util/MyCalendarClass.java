package com.eraisedtox94.smartdiary.presenter.util;

import com.eraisedtox94.smartdiary.model.IAppPrefsManagerImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by spraful on 18-Apr-17.
 */

public class MyCalendarClass {

    private IAppPrefsManagerImpl appSharedPreferences;

    private static MyCalendarClass myCalendarClass ;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    public String getFormattedDate(){
        return mSimpleDateFormat.format(Calendar.getInstance().getTime());
    }

    public String getFormattedTime(){
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String  currentTime = df.format(Calendar.getInstance().getTime());
        return  currentTime;
    }

    /*
    public int getLanguagePreference(){
        if(appSharedPreferences == null)
        appSharedPreferences = new IAppPrefsManagerImpl();
        appSharedPreferences.loadUserPreference("lang");
        return 0;
    }*/
}
