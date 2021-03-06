package edu.udacity.android.contentfinder.util;

public class Constants {
    // constants related to search screens
    public static final String KEYWORD_ARRAY = "keyword_array";
    public static final String SELECTED_KEYWORD_INDEX = "keyword_index";
    public static final String MEDIA_ITEM_ARRAY = "media_item_array";

    // constants related to details screens
    public static final String SELECTED_MEDIA_ITEM = "selected_media_item";
    public static final String SELECTED_KEYWORD = "selected_keyword";

    // widget broadcast event
    public static final String ACTION_DATA_UPDATED = "edu.udacity.android.contentfinder.DATA_UPDATED";

    // limits on db query
    public static final int WIDGET_KEYWORD_LIMIT = 5;
    public static final int APP_KEYWORD_LIMIT = 25;
    public static final int APP_MEDIA_ITEM_LIMIT = 25;

    // private constructor to prevent instantiation
    private Constants() {
    }
}
