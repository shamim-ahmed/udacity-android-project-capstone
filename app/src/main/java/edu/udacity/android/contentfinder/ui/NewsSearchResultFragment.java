package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.udacity.android.contentfinder.NewsDetailActivity;
import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.util.SearchResult;

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

        final Context context = inflater.getContext();
        View newsFragRoot = inflater.inflate(R.layout.news_search_result, container, false);
        ListView newsList = (ListView) newsFragRoot.findViewById(R.id.news_list);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult selectedResult = (SearchResult) parent.getItemAtPosition(position);
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("selectedResult", selectedResult);
                context.startActivity(intent);
            }
        });

        return newsFragRoot;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "I am here !!");
        outState.putLong("myValue", 1234L);
    }
}

