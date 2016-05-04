package edu.udacity.android.capstone.task;

import android.app.Activity;

import edu.udacity.android.capstone.service.SearchService;

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
