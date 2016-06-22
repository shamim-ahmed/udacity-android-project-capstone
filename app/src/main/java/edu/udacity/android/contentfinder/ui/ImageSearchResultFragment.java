package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.udacity.android.contentfinder.ImageDetailActivity;
import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

/**
 * Created by shamim on 6/18/16.
 */
public class ImageSearchResultFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_search_result, container, false);
        final Context context = inflater.getContext();

        ListView imageList = (ListView) rootView.findViewById(R.id.image_list);
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult resultItem = (SearchResult) parent.getItemAtPosition(position);
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra(Constants.SELECTED_IMAGE_KEY, resultItem);
                context.startActivity(intent);
            }
        });

        return rootView;
    }
}
