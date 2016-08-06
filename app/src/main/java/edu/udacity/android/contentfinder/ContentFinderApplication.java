package edu.udacity.android.contentfinder;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import net.jodah.expiringmap.ExpiringMap;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by shamim on 5/1/16.
 */
public class ContentFinderApplication extends Application {
    private static final String TAG = ContentFinderApplication.class.getSimpleName();
    private static final int CACHE_TIMEOUT = 5;

    private final Properties configProperties = new Properties();
    private final Map<String, String> searchResultCache = ExpiringMap.builder().expiration(CACHE_TIMEOUT, TimeUnit.MINUTES).build();

    private Tracker defaultTracker;

    public synchronized Tracker getDefaultTracker() {
        if (defaultTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            defaultTracker = analytics.newTracker(R.xml.global_tracker);
        }

        return defaultTracker;
    }

    public String findInCache(String key) {
        return searchResultCache.get(key);
    }

    public void storeInCache(String key, String value) {
        searchResultCache.put(key, value);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        try {
            configProperties.load(context.getResources().openRawResource(R.raw.config));
        } catch (IOException ex) {
            Log.e(TAG, "Error while loading configuration properties");
        }
    }

    public String getProperty(String key) {
        return configProperties.getProperty(key);
    }
}
