package edu.udacity.android.contentfinder.util;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.db.ContentFinderDataProvider;
import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 7/31/16.
 */
public class DbUtils {
    private static final String DATE_FORMAT_STR = "yyyy-MM-dd:HH:mm:ss";

    public static ContentValues convertKeyword(Keyword keyword) {
        ContentValues values = new ContentValues();
        values.put(ContentFinderContract.KeywordEntry.COLUMN_WORD, keyword.getWord());
        values.put(ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE, DateUtils.formatDate(keyword.getCreatedDate()));
        return values;
    }

    public static void close(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception ex) {
                // ignore it
            }
        }
    }


    private DbUtils() {
    }
}
