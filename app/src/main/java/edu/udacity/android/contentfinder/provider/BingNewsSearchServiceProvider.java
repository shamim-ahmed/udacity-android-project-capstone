package edu.udacity.android.contentfinder.provider;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.web.BingNewsSearchTask;
import edu.udacity.android.contentfinder.task.web.SearchTask;

/**
 * Created by shamim on 5/1/16.
 */

public class BingNewsSearchServiceProvider implements SearchServiceProvider {
    private static final BingNewsSearchServiceProvider INSTANCE = new BingNewsSearchServiceProvider();

    public static BingNewsSearchServiceProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity) {
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String scheme = application.getProperty("news.search.api.scheme.bing");
        String authority = application.getProperty("news.search.api.host.bing");
        String path = application.getProperty("news.search.api.path.bing");
        String format = application.getProperty("news.search.api.result.format");
        String max = application.getProperty("news.search.api.result.max");

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.scheme(scheme)
                .authority(authority)
                .appendEncodedPath(path)
                .appendQueryParameter("Query", String.format("'%s'", keyword))
                .appendQueryParameter("$format", format)
                .appendQueryParameter("$top", max)
                .build();

        SearchTask searchTask = new BingNewsSearchTask(activity);
        searchTask.execute(uri.toString());
    }

    private BingNewsSearchServiceProvider() {
    }
}
