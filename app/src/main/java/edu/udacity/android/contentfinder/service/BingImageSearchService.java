package edu.udacity.android.contentfinder.service;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.BingImageSearchTask;
import edu.udacity.android.contentfinder.task.SearchTask;

/**
 * Created by shamim on 5/1/16.
 */

// example url https://content.guardianapis.com/search?api-key=ba8797d1-a1ef-4a5c-902c-ee0278d59bf6&q=hillary
public class BingImageSearchService implements SearchService {
    private static final BingImageSearchService INSTANCE = new BingImageSearchService();

    public static BingImageSearchService getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity, Fragment fragment) {
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

        SearchTask searchTask = new BingImageSearchTask(activity, fragment);
        searchTask.execute(uri.toString());
    }

    private BingImageSearchService() {
    }
}
