package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.provider.BingImageSearchServiceProvider;
import edu.udacity.android.contentfinder.provider.SearchServiceProvider;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.ui.ImageListAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class ImageSearchActivity extends AbstractSearchActivity {
    private static final String TAG = ImageSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.keyword_spinner);

        ListView imageListView = (ListView) findViewById(R.id.image_list);
        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem selectedResult = (MediaItem) parent.getItemAtPosition(position);
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                Intent intent = new Intent(ImageSearchActivity.this, ImageDetailActivity.class);
                intent.putExtra(Constants.SELECTED_IMAGE_KEY, selectedResult);
                intent.putExtra(Constants.SELECTED_IMAGE_KEYWORD, selectedKeyword);
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

        Parcelable[] keywordArray = null;
        Parcelable[] mediaItemArray = null;
        int selectedKeywordIndex = Spinner.INVALID_POSITION;

        if (savedInstanceState != null) {
            keywordArray = savedInstanceState.getParcelableArray(Constants.KEYWORD_ARRAY);
            selectedKeywordIndex = savedInstanceState.getInt(Constants.SELECTED_KEYWORD_INDEX);
            mediaItemArray = savedInstanceState.getParcelableArray(Constants.IMAGE_ARRAY);
        }

        if (keywordArray != null && keywordArray.length > 0 && selectedKeywordIndex != Spinner.INVALID_POSITION) {
            Log.i(TAG, "Restoring keywords and media items from bundle...");
            // load keywords in spinner
            List<Keyword> keywordList = new ArrayList<>();

            for (int i = 0; i < keywordArray.length; i++) {
                keywordList.add((Keyword) keywordArray[i]);
            }

            loadKeywords(keywordList);
            keywordSpinner.setSelection(selectedKeywordIndex);

            // load media items
            ArrayAdapter<MediaItem> imageListAdapter = new ImageListAdapter(this);
            imageListView.setAdapter(imageListAdapter);
            List<MediaItem> imageList = new ArrayList<>();

            if (mediaItemArray != null) {
                for (int i = 0; i < mediaItemArray.length; i++) {
                    imageList.add((MediaItem) mediaItemArray[i]);
                }
            }

            imageListAdapter.addAll(imageList);
        } else {
            Log.i(TAG, "Loading keywords from database and media items from Internet...");
            SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
            searchKeywordTask.execute();
        }

        loadAdvertisement();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.keyword_spinner);
        ArrayAdapter<Keyword> spinnerAdapter = (ArrayAdapter<Keyword>) keywordSpinner.getAdapter();
        final int keywordCount = keywordSpinner.getCount();
        Keyword[] keywordArray = new Keyword[keywordCount];

        for (int i = 0; i < keywordCount; i++) {
            keywordArray[i] = spinnerAdapter.getItem(i);
        }

        outState.putParcelableArray(Constants.KEYWORD_ARRAY, keywordArray);
        outState.putInt(Constants.SELECTED_KEYWORD_INDEX, keywordSpinner.getSelectedItemPosition());

        ListView imageList = (ListView) findViewById(R.id.image_list);
        ArrayAdapter<MediaItem> adapter = (ArrayAdapter<MediaItem>) imageList.getAdapter();
        final int imageCount = adapter.getCount();
        MediaItem[] imageArray = new MediaItem[imageCount];

        for (int i = 0; i < imageCount; i++) {
            imageArray[i] = adapter.getItem(i);
        }

        outState.putParcelableArray(Constants.IMAGE_ARRAY, imageArray);
    }
}
