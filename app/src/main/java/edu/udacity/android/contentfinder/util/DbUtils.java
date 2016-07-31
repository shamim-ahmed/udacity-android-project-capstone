package edu.udacity.android.contentfinder.util;

import android.content.ContentValues;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
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

    private DbUtils() {
    }
}
