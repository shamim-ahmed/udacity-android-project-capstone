package edu.udacity.android.contentfinder.service;

import android.app.Activity;

/**
 * Created by shamim on 5/4/16.
 */
public interface SearchService {
    void performSearch(String keyword, Activity activity);
}
