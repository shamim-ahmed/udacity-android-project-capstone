package edu.udacity.android.contentfinder.task.web;

import android.app.Activity;
import android.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import edu.udacity.android.contentfinder.ContentFinderApplication;

public abstract class BingSearchTask extends SearchTask {
    private final String apiKey;

    public BingSearchTask(Activity activity) {
        super(activity);

        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        apiKey = application.getProperty("search.api.key.bing");
    }

    @Override
    protected void addAuthorizationHeaders(HttpsURLConnection connection) {
        String keyStr = String.format("%s:%s", apiKey, apiKey);
        String encodedKeyStr = Base64.encodeToString(keyStr.getBytes(), Base64.NO_WRAP);
        connection.setRequestProperty("Authorization", String.format("Basic %s", encodedKeyStr));
    }
}
