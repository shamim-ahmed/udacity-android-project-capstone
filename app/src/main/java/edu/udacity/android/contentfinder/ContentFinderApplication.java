package edu.udacity.android.contentfinder;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by shamim on 5/1/16.
 */
public class ContentFinderApplication extends Application {
    private static final String TAG = ContentFinderApplication.class.getSimpleName();
    private final Properties configProperties = new Properties();

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        try {
            configProperties.load(context.getResources().openRawResource(R.raw.config));
        } catch (IOException ex) {
            Log.e(TAG, "error while loading configuration properties");
        }
    }

    public String getProperty(String key) {
        return configProperties.getProperty(key);
    }
}
