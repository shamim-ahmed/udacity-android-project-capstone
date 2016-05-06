package edu.udacity.android.contentfinder.task;

import android.app.Activity;

/**
 * Created by shamim on 5/6/16.
 */
public class YoutubeSearchTask extends SearchTask {
    public YoutubeSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
        // TODO update some view associated with the activity
    }
}
