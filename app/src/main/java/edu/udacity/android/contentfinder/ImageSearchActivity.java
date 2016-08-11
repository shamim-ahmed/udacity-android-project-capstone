package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.provider.BingImageSearchServiceProvider;
import edu.udacity.android.contentfinder.provider.SearchServiceProvider;
import edu.udacity.android.contentfinder.ui.ImageListAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class ImageSearchActivity extends AbstractMediaItemSearchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner keywordSpinner = getKeywordSpinner();

        ListView imageListView = getMediaItemListView();
        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem selectedResult = (MediaItem) parent.getItemAtPosition(position);
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                Intent intent = new Intent(ImageSearchActivity.this, ImageDetailActivity.class);
                intent.putExtra(Constants.SELECTED_MEDIA_ITEM, selectedResult);
                intent.putExtra(Constants.SELECTED_KEYWORD, selectedKeyword);
                startActivity(intent);
            }
        });

        final Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                if (selectedKeyword != null) {
                    SearchServiceProvider searchServiceProvider = BingImageSearchServiceProvider.getInstance();
                    searchServiceProvider.performSearch(selectedKeyword.getWord(), ImageSearchActivity.this);
                }
            }
        });

        loadApplicationData(savedInstanceState);
        loadAdvertisement();
    }

    @Override
    protected Spinner getKeywordSpinner() {
        return (Spinner) findViewById(R.id.keyword_spinner);
    }

    @Override
    protected ListView getMediaItemListView() {
        return (ListView) findViewById(R.id.image_list);
    }

    @Override
    protected ArrayAdapter<MediaItem> createMediaItemListAdapter() {
        return new ImageListAdapter(this);
    }
}
