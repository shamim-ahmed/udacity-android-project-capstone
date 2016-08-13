package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Button;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.db.ContentFinderDataProvider;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.util.DbUtils;

public class CheckMediaItemExistsTask extends AsyncTask<Void, Void, Boolean> {
    private final Activity activity;
    private final MediaItem mediaItem;

    public CheckMediaItemExistsTask(Activity activity, MediaItem mediaItem) {
        this.activity = activity;
        this.mediaItem = mediaItem;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(ContentFinderContract.MediaItemEntry.CONTENT_URI, null, ContentFinderDataProvider.MEDIA_ITEM_ID_SELECTION, new String[] {mediaItem.getItemId()}, null, null);

        boolean resultFlag = Boolean.FALSE;

        if (cursor != null) {
            resultFlag = cursor.moveToFirst();
        }

        DbUtils.close(cursor);

        return resultFlag;
    }

    @Override
    protected void onPostExecute(Boolean resultFlag) {
        if (resultFlag) {
            Button favoriteButton = (Button)activity.findViewById(R.id.favorite_button);

            if (favoriteButton != null) {
                favoriteButton.setEnabled(false);
            }
        }
    }
}
