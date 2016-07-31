package edu.udacity.android.contentfinder.util;

import android.content.ContentValues;
import android.database.Cursor;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.db.ContentFinderDataProvider;
import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 7/31/16.
 */
public class DbUtils {
    public static ContentValues convertKeyword(Keyword keyword) {
        ContentValues values = new ContentValues();
        values.put(ContentFinderContract.KeywordEntry.COLUMN_WORD, keyword.getWord());
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
