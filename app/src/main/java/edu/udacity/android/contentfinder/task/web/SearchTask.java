package edu.udacity.android.contentfinder.task.web;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.util.IOUtils;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.util.StringUtils;

public abstract class SearchTask extends AsyncTask<String, Void, List<MediaItem>> {
    private static final String TAG = SearchTask.class.getSimpleName();

    protected final Activity activity;

    public SearchTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<MediaItem> doInBackground(String... params) {
        if (params.length < 1) {
            Log.w(TAG, "No search URL provided");
            return null;
        }

        String urlString = params[0];
        Log.i(TAG, String.format("The search URL is %s", urlString));

        StringBuilder resultBuilder = new StringBuilder();
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String searchResult = application.findInCache(urlString);

        if (StringUtils.isNotBlank(searchResult)) {
            // result obtained from cache
            Log.i(TAG, String.format("Search result for URL %s has been retrieved from cache", urlString));
            resultBuilder.append(searchResult);
        } else {
            // we have to make a netwoork call
            BufferedReader reader = null;

            try {
                URL searchUrl = new URL(urlString);
                HttpsURLConnection connection = (HttpsURLConnection) searchUrl.openConnection();
                addAuthorizationHeaders(connection);

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    resultBuilder.append(line);
                }

                // put the result in cache
                application.storeInCache(urlString, resultBuilder.toString());
                Log.i(TAG, String.format("Search result for URL %s has been placed in cache", urlString));
            } catch (IOException ex) {
                Log.e(TAG, String.format("Error while performing search with URL : %s", urlString), ex);
            } finally {
                IOUtils.close(reader);
            }
        }

        return parseResponse(resultBuilder.toString());
    }

    protected abstract List<MediaItem> parseResponse(String jsonStr);

    protected void addAuthorizationHeaders(HttpsURLConnection connection) {
    }
}
