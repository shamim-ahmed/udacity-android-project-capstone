package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.udacity.android.contentfinder.AbstractSearchActivity;
import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.DateUtils;
import edu.udacity.android.contentfinder.util.DbUtils;

public class SearchKeywordTask extends AsyncTask<Void, Void, List<Keyword>> {
    private final Activity activity;

    public SearchKeywordTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Keyword> doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();
        String sortOrder = String.format("%s DESC LIMIT %d", ContentFinderContract.KeywordEntry._ID, Constants.APP_KEYWORD_LIMIT);
        Cursor cursor = contentResolver.query(ContentFinderContract.KeywordEntry.CONTENT_URI, null, null, null, sortOrder);

        if (cursor == null) {
            return Collections.emptyList();
        }

        List<Keyword> resultList = new ArrayList<>();

        for (int i = 0, n = cursor.getCount(); i < n; i++) {
            cursor.moveToNext();
            Keyword keyword = readKeyword(cursor);
            resultList.add(keyword);
        }

        DbUtils.close(cursor);
        return resultList;
    }

    @Override
    public void onPostExecute(List<Keyword> resultList) {
        ((AbstractSearchActivity) activity).loadKeywords(resultList, true);
    }

    private Keyword readKeyword(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ContentFinderContract.KeywordEntry._ID);
        int wordIndex = cursor.getColumnIndex(ContentFinderContract.KeywordEntry.COLUMN_WORD);
        int createdDateIndex = cursor.getColumnIndex(ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE);

        Keyword keyword = new Keyword();
        keyword.setId(cursor.getLong(idIndex));
        keyword.setWord(cursor.getString(wordIndex));
        keyword.setCreatedDate(DateUtils.parseDate(cursor.getString(createdDateIndex)));

        return keyword;
    }
}
