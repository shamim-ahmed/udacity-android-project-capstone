package edu.udacity.android.contentfinder.service;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by shamim on 5/4/16.
 */
public interface SearchService {
    void performSearch(String keyword, Activity activity);
}
