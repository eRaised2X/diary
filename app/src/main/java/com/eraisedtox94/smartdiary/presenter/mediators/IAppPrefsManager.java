package com.eraisedtox94.smartdiary.presenter.mediators;

import java.util.Set;

/**
 * Created by spraful on 22-May-17.
 */

public interface IAppPrefsManager {

    public void setLastOpenedFileIdInsideSharedPref( String value);
    public String getLastOpenedFileIdFromSharedPref();
    public void saveUserPreferences( Set<String> value);
    public Set<String> loadUserPreference();

}
