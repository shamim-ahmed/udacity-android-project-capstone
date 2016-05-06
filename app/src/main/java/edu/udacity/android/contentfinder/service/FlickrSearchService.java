package edu.udacity.android.contentfinder.service;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.FlickrSearchTask;

/**
 * Created by shamim on 5/1/16.
 */
// https://api.flickr.com/services/rest/?format=json&sort=random&method=flickr.photos.search&tags=girl&tag_mode=all&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1
public class FlickrSearchService implements SearchService {
    private static final FlickrSearchService INSTANCE = new FlickrSearchService();

    public static FlickrSearchService getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity) {
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String scheme = application.getProperty("search.api.scheme.flickr");
        String authority = application.getProperty("search.api.host.flickr");
        String path = application.getProperty("search.api.path.flickr");
        String apiKey = application.getProperty("search.api.key.flickr");
        String method = application.getProperty("search.api.method.flickr");
        String format = application.getProperty("search.api.format.flickr");

        Uri searchUri = new Uri.Builder()
                .scheme(scheme)
                .authority(authority)
                .path(path)
                .appendQueryParameter("api_key", apiKey)
                .appendQueryParameter("method", method)
                .appendQueryParameter("format", format)
                .appendQueryParameter("tags", keyword)
                .build();

        FlickrSearchTask searchTask = new FlickrSearchTask(activity);
        searchTask.execute(searchUri.toString());
    }

    private FlickrSearchService() {
    }
}
