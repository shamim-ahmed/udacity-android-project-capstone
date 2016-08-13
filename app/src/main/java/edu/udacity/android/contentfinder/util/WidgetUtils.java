package edu.udacity.android.contentfinder.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;

import java.util.Date;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.db.ContentFinderContract;

public class WidgetUtils {

    public static ContentValues readCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ContentFinderContract.KeywordEntry._ID);
        int wordIndex = cursor.getColumnIndex(ContentFinderContract.KeywordEntry.COLUMN_WORD);
        int createdDateIndex = cursor.getColumnIndex(ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE);

        ContentValues values = new ContentValues();
        values.put(ContentFinderContract.KeywordEntry._ID, cursor.getString(idIndex));
        values.put(ContentFinderContract.KeywordEntry.COLUMN_WORD, cursor.getString(wordIndex));
        values.put(ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE, cursor.getString(createdDateIndex));

        return values;
    }

    public static void populateView(ContentValues values, RemoteViews views, Context context) {
        String word = (String) values.get(ContentFinderContract.KeywordEntry.COLUMN_WORD);
        String dateStr = (String) values.get(ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE);

        views.setTextViewText(R.id.widgetItem_word, word);
        views.setTextViewText(R.id.widgetItem_created_date, dateStr);
    }

    // private constructor to prevent instantiation
    private WidgetUtils() {
    }
}
