package edu.udacity.android.contentfinder.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.udacity.android.contentfinder.R;

/**
 * Created by shamim on 6/18/16.
 */
public class NewsSearchResultFragment extends Fragment {
    private static final String TAG = NewsSearchResultFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Long n = savedInstanceState.getLong("myValue");
            Log.i(TAG, "the value is : " + n);
        }

        return inflater.inflate(R.layout.news_search_result, container, false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "I am here !!");
        outState.putLong("myValue", 1234L);
    }
}

