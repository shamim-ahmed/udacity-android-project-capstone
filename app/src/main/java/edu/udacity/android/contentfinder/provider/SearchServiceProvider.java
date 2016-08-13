package edu.udacity.android.contentfinder.provider;

import android.app.Activity;

public interface SearchServiceProvider {
    void performSearch(String keyword, Activity activity);
}
