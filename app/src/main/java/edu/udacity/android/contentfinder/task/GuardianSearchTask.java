package edu.udacity.android.contentfinder.task;

import android.app.Activity;

/**
 * Created by shamim on 5/3/16.
 */
public class GuardianSearchTask extends SearchTask {
    public GuardianSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // do something with the search result
    }
}
