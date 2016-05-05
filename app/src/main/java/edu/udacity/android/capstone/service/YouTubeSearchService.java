package edu.udacity.android.capstone.service;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.capstone.CapstoneApplication;
import edu.udacity.android.capstone.task.SearchTask;
import edu.udacity.android.capstone.task.YoutubeSearchTask;

/**
 * Created by shamim on 5/1/16.
 */
//https://www.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyCeBaDHs2Ft4mwuAA1zXYlIwRwjE6yHbjw&q=tulip
public class YouTubeSearchService implements SearchService {
    @Override
    public void performSearch(String keyword, Activity activity) {
        CapstoneApplication application = (CapstoneApplication) activity.getApplication();
        String scheme = application.getProperty("search.api.scheme.youtube");
        String authority = application.getProperty("search.api.scheme.host");
        String path = application.getProperty("search.api.path.youtube");
        String searchKey = application.getProperty("search.api.key.youtube");

        Uri searchUri = new Uri.Builder()
                .scheme(scheme)
                .authority(authority)
                .path(path)
                .appendQueryParameter("key", searchKey)
                .appendQueryParameter("part", "snippet")
                .appendQueryParameter("q", keyword)
                .build();

        SearchTask searchTask = new YoutubeSearchTask(activity);
        searchTask.execute(searchUri.toString());
    }
}
