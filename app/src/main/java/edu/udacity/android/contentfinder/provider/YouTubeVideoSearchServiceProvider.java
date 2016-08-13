package edu.udacity.android.contentfinder.provider;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.web.SearchTask;
import edu.udacity.android.contentfinder.task.web.YouTubeVideoSearchTask;

public class YouTubeVideoSearchServiceProvider implements SearchServiceProvider {
    private static final YouTubeVideoSearchServiceProvider INSTANCE = new YouTubeVideoSearchServiceProvider();

    public static YouTubeVideoSearchServiceProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity) {
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String scheme = application.getProperty("search.api.scheme.youtube");
        String authority = application.getProperty("search.api.host.youtube");
        String path = application.getProperty("search.api.path.youtube");
        String searchKey = application.getProperty("search.api.key.youtube");

        Uri searchUri = new Uri.Builder()
                .scheme(scheme)
                .authority(authority)
                .path(path)
                .appendQueryParameter("key", searchKey)
                .appendQueryParameter("part", "snippet")
                .appendQueryParameter("type", "video")
                .appendQueryParameter("q", keyword)
                .build();

        SearchTask searchTask = new YouTubeVideoSearchTask(activity);
        searchTask.execute(searchUri.toString());
    }

    // private constructor to prevent instantiation
    private YouTubeVideoSearchServiceProvider() {
    }
}
