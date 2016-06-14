package edu.udacity.android.contentfinder.service;

import android.app.Activity;
import android.net.Uri;

import edu.udacity.android.contentfinder.ContentFinderApplication;
import edu.udacity.android.contentfinder.task.BingNewsSearchTask;
import edu.udacity.android.contentfinder.task.SearchTask;

/**
 * Created by shamim on 5/1/16.
 */

// example url https://content.guardianapis.com/search?api-key=ba8797d1-a1ef-4a5c-902c-ee0278d59bf6&q=hillary
public class BingSearchService implements SearchService {
    private static final BingSearchService INSTANCE = new BingSearchService();

    private static final String API_LINK = "https://api.datamarket.azure.com/Bing/Search/News?Query=%27Hillary2%27&$format=json&$top=5";


    public static BingSearchService getInstance() {
        return INSTANCE;
    }

    @Override
    public void performSearch(String keyword, Activity activity) {
        ContentFinderApplication application = (ContentFinderApplication) activity.getApplication();
        String scheme = application.getProperty("news.search.api.scheme.bing");
        String authority = application.getProperty("news.search.api.host.bing");
        String path = application.getProperty("news.search.api.path.bing");

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.scheme(scheme)
                .authority(authority)
                .appendPath("Bing").appendPath("Search").appendPath("News")
                .appendQueryParameter("Query", String.format("'%s'", keyword))
                .appendQueryParameter("$format", "json")
                .appendQueryParameter("$top", "5")
                .build();

        SearchTask searchTask = new BingNewsSearchTask(activity);
        searchTask.execute(uri.toString());
    }

    private BingSearchService() {
    }
}
