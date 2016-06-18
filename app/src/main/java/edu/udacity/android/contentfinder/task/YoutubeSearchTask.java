package edu.udacity.android.contentfinder.task;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import edu.udacity.android.contentfinder.util.SearchResult;

/**
 * Created by shamim on 5/6/16.
 */
public class YoutubeSearchTask extends SearchTask {
    private static final String TAG = YoutubeSearchTask.class.getSimpleName();

    public YoutubeSearchTask(Activity activity, Fragment fragment) {
        super(activity, fragment);
    }

    @Override
    protected List<SearchResult> parseResponse(String jsonStr) {
        return null;
    }

    @Override
    public void onPostExecute(List<SearchResult> resultList) {
        Log.i(TAG, "the search result : " + resultList);
        // TODO update some view associated with the activity
    }
}
