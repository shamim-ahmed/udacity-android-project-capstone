package edu.udacity.android.contentfinder;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 5/1/16.
 */

/**
 * TODO Implement a global caching mechanism using Guava. Use proguard to reduce size
 *
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

    // Todo remove this when DB layer is implemented
    public List<Keyword> getKeyWords() {
        List<Keyword> resultList = new ArrayList<>();

        Keyword keyword1 = new Keyword();
        keyword1.setWord("us election 2016");
        keyword1.setCreatedDate(new Date());
        resultList.add(keyword1);

        Keyword keyword2 = new Keyword();
        keyword2.setWord("daisy");
        keyword2.setCreatedDate(new Date());
        resultList.add(keyword2);

        Keyword keyword3 = new Keyword();
        keyword3.setWord("brexit");
        keyword3.setCreatedDate(new Date());
        resultList.add(keyword3);

        return resultList;
    }

    public String getProperty(String key) {
        return configProperties.getProperty(key);
    }
}
