package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.List;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 7/31/16.
 */
public class SearchKeywordTask extends AsyncTask<Void, Void, List<Keyword>> {
    private final Activity activity;

    public SearchKeywordTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Keyword> doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(ContentFinderContract.KeywordEntry.CONTENT_URI, null, null, null, null);
        
        return null;
    }
}
