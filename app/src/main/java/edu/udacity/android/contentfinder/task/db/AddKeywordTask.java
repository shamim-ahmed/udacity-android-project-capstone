package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.util.DbUtils;

/**
 * Created by shamim on 7/30/16.
 */
public class AddKeywordTask extends AsyncTask<Void, Void, Uri> {
    private final Activity activity;
    private final Keyword keyword;

    public AddKeywordTask(Activity activity, Keyword keyword) {
        this.activity = activity;
        this.keyword = keyword;
    }

    @Override
    protected Uri doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();

        ContentValues values = DbUtils.convertKeyword(keyword);
        Uri uri = contentResolver.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
        return uri;
    }

    @Override
    public void onPostExecute(Uri resultUri) {

    }
}
