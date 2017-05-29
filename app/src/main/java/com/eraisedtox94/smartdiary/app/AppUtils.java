package com.eraisedtox94.smartdiary.app;

import java.util.Collections;
import java.util.Set;

/**
 * Created by spraful on 23-May-17.
 */

public class AppUtils {
    //String for fonts path
    public static String FONT_BRADLEY_RESOURCE_LOCATION = "fonts/bradley_hand.ttf";

    //default file id (just to prevent use of null)
    public static String DEFAULT_FILE_ID  = "0";

    //default user pref SET to prevent use of null
    public static Set<String> DEFAULT_USER_PREFS = Collections.emptySet();

    public static String TEXT_FILE_EXTENSION = ".txt";

    //flags for indicating whether it is a read operation or a write operation
    public static String READ_FLAG = "0";
    public static String WRITE_FLAG = "1";

    //flag to act as delimiter to indicate what part is title and what is content inside file read
    public static String TITLE_CONTENT_SEPARATOR_FLAG = "!~^$^~!";
}
