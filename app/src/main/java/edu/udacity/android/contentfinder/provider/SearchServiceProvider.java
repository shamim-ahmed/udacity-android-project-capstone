package edu.udacity.android.contentfinder.provider;

import android.app.Activity;

/**
 * Created by shamim on 5/4/16.
 */
public interface SearchServiceProvider {
    void performSearch(String keyword, Activity activity);
}
