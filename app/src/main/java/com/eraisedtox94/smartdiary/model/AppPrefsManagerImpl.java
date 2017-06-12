package com.eraisedtox94.smartdiary.model;

import android.content.Context;

import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.presenter.mediators.IAppPrefsManager;

import java.util.Set;

/**
 * Created by spraful on 20-Apr-17.
 */


public class AppPrefsManagerImpl implements IAppPrefsManager {

    private Context mContext;
    private final String PREFS_NAME = "DIARY_APP_PREFERENCES";
    private String KEY_FILE_ID = "idOfFileLastlyOpened";
    private String USER_PREFS = "user_preferences";

    public AppPrefsManagerImpl(Context context){
        mContext = context;
    }
    //for last opened file id
    public void setLastOpenedFileIdInsideSharedPref(String value) {
        mContext.getSharedPreferences(PREFS_NAME,0).edit().putString(KEY_FILE_ID, value).apply();
    }

    public String getLastOpenedFileIdFromSharedPref() {
        return mContext.getSharedPreferences(PREFS_NAME,0).getString(KEY_FILE_ID, AppUtils.DEFAULT_FILE_ID);
    }


    //for user language preference
    public void saveUserPreferences(Set<String> value) {
        mContext.getSharedPreferences(PREFS_NAME,0).edit().putStringSet(USER_PREFS, value).apply();
    }

    public Set<String> loadUserPreference() {
        return mContext.getSharedPreferences(PREFS_NAME,0).getStringSet(USER_PREFS, AppUtils.DEFAULT_USER_PREFS);
    }

}