package com.eraisedtox94.smartdiary.app;

import android.app.Application;
import android.util.Log;

/**
 * Created by spraful on 17-May-17.
 */

public class DiaryApplication extends Application{
    //TODO to be figured out if we really need subclass of Application
    //and how much effect it has on start time
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("remove this","this class is not required actually");
        //AppPrefsManagerImpl.getInstance().initializeContextForSharedPrefClass(getApplicationContext());
    }
}
