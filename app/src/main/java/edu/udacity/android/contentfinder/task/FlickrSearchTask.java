package edu.udacity.android.contentfinder.task;

import android.app.Activity;

/**
 * Created by shamim on 5/4/16.
 */
public class FlickrSearchTask extends SearchTask {
    public FlickrSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    public void onPostExecute(String searchResult) {
        super.onPostExecute(searchResult);

        // update an appropriate view associated with the activity
    }
}
