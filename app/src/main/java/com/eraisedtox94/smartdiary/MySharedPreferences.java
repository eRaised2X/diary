package com.eraisedtox94.smartdiary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by spraful on 20-Apr-17.
 */

public class MySharedPreferences {

    public final String PREFS_NAME = "DIARY_APP_PREFERENCES";

    SharedPreferences whichItemIsInContext;
    SharedPreferences.Editor editor;

    public boolean setItemInContextInSharedPref(Context context, String key, String value) {
        whichItemIsInContext = context.getSharedPreferences(PREFS_NAME, 0);
        editor = whichItemIsInContext.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getItemInContextFromSharedPref(Context context, String key) {
        whichItemIsInContext = context.getSharedPreferences(PREFS_NAME, 0);
        String value = whichItemIsInContext.getString(key, null);
        return value;
    }
}
