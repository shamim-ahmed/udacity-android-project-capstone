package edu.udacity.android.contentfinder.task;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.util.IOUtils;
import edu.udacity.android.contentfinder.util.SearchResult;

/**
 * Created by shamim on 6/13/16.
 */
public abstract class BingSearchTask extends SearchTask {
    private static final String TAG = BingSearchTask.class.getSimpleName();

    private final String apiKey;

    public BingSearchTask(Activity activity) {
        super(activity);

        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        apiKey = application.getProperty("news.search.api.key.bing");
    }

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

            String keyStr = String.format("%s:%s", apiKey, apiKey);
            String encodedKeyStr = Base64.encodeToString(keyStr.getBytes(), Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", String.format("Basic %s", encodedKeyStr));
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
}
