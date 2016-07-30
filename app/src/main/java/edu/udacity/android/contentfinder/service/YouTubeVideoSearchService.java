package edu.udacity.android.contentfinder.service;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.web.SearchTask;
import edu.udacity.android.contentfinder.task.web.YouTubeVideoSearchTask;

/**
 * Created by shamim on 5/1/16.
 */
//https://www.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyCeBaDHs2Ft4mwuAA1zXYlIwRwjE6yHbjw&q=tulip
public class YouTubeVideoSearchService implements SearchService {
    private static final YouTubeVideoSearchService INSTANCE = new YouTubeVideoSearchService();

    public static YouTubeVideoSearchService getInstance() {
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
    private YouTubeVideoSearchService() {
    }
}
