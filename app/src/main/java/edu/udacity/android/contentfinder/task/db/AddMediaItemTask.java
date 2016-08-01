package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.util.DbUtils;
import edu.udacity.android.contentfinder.model.MediaItem;

/**
 * Created by shamim on 7/31/16.
 */
public class AddMediaItemTask extends AsyncTask<Void, Void, Uri> {
    private final Activity activity;
    private final MediaItem mediaItem;

    public AddMediaItemTask(Activity activity, MediaItem mediaItem) {
        this.activity = activity;
        this.mediaItem = mediaItem;
    }

    @Override
    protected Uri doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();

        ContentValues values = DbUtils.convertMediaItem(mediaItem);
        return contentResolver.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
    }

    @Override
    public void onPostExecute(Uri resultUri) {
        String toastMessage;

        if (resultUri == null) {
            toastMessage = "The media item could not be saved";
        } else {
            toastMessage = "The media item was saved successfully";
        }

        Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show();
    }
}
