package edu.udacity.android.capstone.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import edu.udacity.android.capstone.util.IOUtils;

/**
 * Created by shamim on 5/3/16.
 */

public abstract class SearchTask extends AsyncTask<String, Void, String> {
    private static final String TAG = SearchTask.class.getSimpleName();

    private final Activity activity;

    // TODO modify the second argument so that it takes a custom activity with a special update method
    // in that way, we can reuse the update logic
    public SearchTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length < 1) {
            Log.w(TAG, "No search URL provided");
            return null;
        }

        String urlString = params[0];
        Log.i(TAG, String.format("The search URL is %s", urlString));

        BufferedReader reader = null;
        StringBuilder resultBuilder = new StringBuilder();

        try {
            URL searchUrl = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(searchUrl.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }
        } catch (IOException ex) {
            Log.e(TAG, String.format("Error while performing search with URL : %s", urlString), ex);
        } finally {
            IOUtils.close(reader);
        }

        return resultBuilder.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i(TAG, String.format("search result is : %s", result));
    }
}
