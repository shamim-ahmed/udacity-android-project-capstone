package edu.udacity.android.contentfinder.provider;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.web.BingImageSearchTask;
import edu.udacity.android.contentfinder.task.web.SearchTask;

/**
 * Created by shamim on 5/1/16.
 */

public class BingImageSearchServiceProvider implements SearchServiceProvider {
    private static final BingImageSearchServiceProvider INSTANCE = new BingImageSearchServiceProvider();

    public static BingImageSearchServiceProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity) {
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String scheme = application.getProperty("photos.search.api.scheme.bing");
        String authority = application.getProperty("photos.search.api.host.bing");
        String path = application.getProperty("photos.search.api.path.bing");
        String format = application.getProperty("photos.search.api.result.format");
        String max = application.getProperty("photos.search.api.result.max");

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.scheme(scheme)
                .authority(authority)
                .appendEncodedPath(path)
                .appendQueryParameter("Query", String.format("'%s'", keyword))
                .appendQueryParameter("$format", format)
                .appendQueryParameter("$top", max)
                .build();

        SearchTask searchTask = new BingImageSearchTask(activity);
        searchTask.execute(uri.toString());
    }

    private BingImageSearchServiceProvider() {
    }
}
