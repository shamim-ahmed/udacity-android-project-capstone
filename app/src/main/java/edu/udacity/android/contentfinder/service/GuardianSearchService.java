package edu.udacity.android.contentfinder.service;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.GuardianSearchTask;
import edu.udacity.android.contentfinder.task.SearchTask;

/**
 * Created by shamim on 5/1/16.
 */

// example url https://content.guardianapis.com/search?api-key=ba8797d1-a1ef-4a5c-902c-ee0278d59bf6&q=hillary
public class GuardianSearchService implements SearchService {
    private static final GuardianSearchService INSTANCE = new GuardianSearchService();

    public static GuardianSearchService getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity) {
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String scheme = application.getProperty("search.api.scheme.guardian");
        String authority = application.getProperty("search.api.host.guardian");
        String path = application.getProperty("search.api.path.guardian");
        String apiKey = application.getProperty("search.api.key.guardian");

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.scheme(scheme)
                .authority(authority)
                .appendPath(path)
                .appendQueryParameter("api-key", apiKey)
                .appendQueryParameter("q", keyword)
                .build();

        SearchTask searchTask = new GuardianSearchTask(activity);
        searchTask.execute(uri.toString());
    }

    private GuardianSearchService() {
    }
}
