package edu.udacity.android.contentfinder.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import edu.udacity.android.contentfinder.util.IOUtils;
import edu.udacity.android.contentfinder.util.SearchResult;

/**
 * Created by shamim on 5/3/16.
 */

public abstract class SearchTask extends AsyncTask<String, Void, List<SearchResult>> {
    private static final String TAG = SearchTask.class.getSimpleName();

    protected final Activity activity;

    public SearchTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<SearchResult> doInBackground(String... params) {
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
            HttpsURLConnection connection = (HttpsURLConnection) searchUrl.openConnection();
            addAuthorizationHeaders(connection);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }
        } catch (IOException ex) {
            Log.e(TAG, String.format("Error while performing search with URL : %s", urlString), ex);
        } finally {
            IOUtils.close(reader);
        }

        return parseResponse(resultBuilder.toString());
    }

    protected abstract List<SearchResult> parseResponse(String jsonStr);

    protected void addAuthorizationHeaders(HttpsURLConnection connection) {
    }
}
