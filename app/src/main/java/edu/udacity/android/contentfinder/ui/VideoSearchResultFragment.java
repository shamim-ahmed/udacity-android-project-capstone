package edu.udacity.android.contentfinder.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.udacity.android.contentfinder.R;

/**
 * Created by shamim on 6/18/16.
 */
public class VideoSearchResultFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_search_result, container, false);
    }
}