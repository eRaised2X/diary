package com.eraisedtox94.smartdiary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by spraful on 18-Apr-17.
 */

public class MyCalendarClass {

    private static MyCalendarClass myCalendarClass = new MyCalendarClass();

    Calendar mCalendar = Calendar.getInstance();
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");;


    //TODO- check its efficiency
    public static MyCalendarClass getInstance(){
        if(myCalendarClass!=null){
            return myCalendarClass;
        }
        else
            return new MyCalendarClass();
    }

    public String getFormattedDate(){
        return  mSimpleDateFormat.format(mCalendar.getTime());
    }

    public String getFormattedTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
        String  currentTime = df.format(mCalendar.getTime());
        return  currentTime;
    }
}
