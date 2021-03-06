package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.db.ContentFinderDataProvider;
import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.DbUtils;

public class SaveKeywordTask extends AsyncTask<Void, Void, Uri> {
    private final Activity activity;
    private final Keyword keyword;

    public SaveKeywordTask(Activity activity, Keyword keyword) {
        this.activity = activity;
        this.keyword = keyword;
    }

    @Override
    protected Uri doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();

        if (keywordExists(contentResolver)) {
            return null;
        }

        ContentValues values = DbUtils.convertKeyword(keyword);
        return contentResolver.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
    }

    private boolean keywordExists(ContentResolver contentResolver) {
        boolean result = false;
        Cursor cursor = contentResolver.query(ContentFinderContract.KeywordEntry.CONTENT_URI, null, ContentFinderDataProvider.KEYWORD_WORD_SELECTION, new String[]{keyword.getWord()}, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            result = true;
        }

        DbUtils.close(cursor);
        return result;
    }

    @Override
    public void onPostExecute(Uri resultUri) {
        String toastMessage;

        if (resultUri == null) {
            toastMessage = activity.getString(R.string.save_keyword_error);
        } else {
            toastMessage = activity.getString(R.string.save_keyword_success);

            // clear the text field
            EditText editText = (EditText) activity.findViewById(R.id.new_keyword);
            editText.setText("");
        }

        Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show();

        // broadcast the update to the widgets
        Intent dataUpdatedIntent = new Intent(Constants.ACTION_DATA_UPDATED).setPackage(activity.getPackageName());
        activity.getApplicationContext().sendBroadcast(dataUpdatedIntent);
    }
}
