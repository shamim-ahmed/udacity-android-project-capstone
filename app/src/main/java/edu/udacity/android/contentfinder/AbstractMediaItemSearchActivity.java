package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;
import edu.udacity.android.contentfinder.util.Constants;

public abstract class AbstractMediaItemSearchActivity extends AbstractSearchActivity {
    private static final String TAG = AbstractMediaItemSearchActivity.class.getSimpleName();

    private static final MediaItem[] EMPTY_MEDIA_ITEM_ARRAY = new MediaItem[0];
    private static final Keyword[] EMPTY_KEYWORD_ARRAY = new Keyword[0];

    protected abstract void configureActionListeners();
    protected abstract ArrayAdapter<MediaItem> createMediaItemListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_item_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configureActionListeners();
        loadApplicationData(savedInstanceState);
        loadAdvertisement();
    }

    @Override
    public void loadKeywords(List<Keyword> keywordList, boolean mediaSearchFlag) {
        ArrayAdapter<Keyword> adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(keywordList);

        final Spinner keywordSpinner = getKeywordSpinner();
        keywordSpinner.setAdapter(adapter);

        Button searchButton = getSearchButton();

        if (adapter.getCount() > 0) {
            keywordSpinner.setSelection(0);
        } else {
            String dummyStr = getString(R.string.placeholder_keyword_text);
            Keyword dummyKeyword = new Keyword();
            dummyKeyword.setWord(dummyStr);
            adapter.add(dummyKeyword);

            // disable the UI items
            keywordSpinner.setEnabled(false);
            searchButton.setEnabled(false);
        }

        if (mediaSearchFlag) {
            searchButton.performClick();
        }
    }

    protected void loadApplicationData(Bundle savedInstanceState) {
        Spinner keywordSpinner = getKeywordSpinner();
        ListView mediaItemListView = getMediaItemListView();

        if (savedInstanceState != null) {
            Parcelable[] keywordArray = savedInstanceState.getParcelableArray(Constants.KEYWORD_ARRAY);
            int selectedKeywordIndex = savedInstanceState.getInt(Constants.SELECTED_KEYWORD_INDEX);

            if (keywordArray != null && keywordArray.length > 0 && selectedKeywordIndex != Spinner.INVALID_POSITION) {
                Log.i(TAG, "Restoring keywords and media items from bundle...");
                // load keywords in spinner
                List<Keyword> keywordList = new ArrayList<>();

                for (Parcelable keyword : keywordArray) {
                    keywordList.add((Keyword) keyword);
                }

                loadKeywords(keywordList, false);
                keywordSpinner.setSelection(selectedKeywordIndex);

                // load media items
                List<MediaItem> mediaItemList = new ArrayList<>();
                Parcelable[] mediaItemArray = savedInstanceState.getParcelableArray(Constants.MEDIA_ITEM_ARRAY);

                if (mediaItemArray != null && mediaItemArray.length > 0) {
                    for (Parcelable item : mediaItemArray) {
                        mediaItemList.add((MediaItem) item);
                    }
                }

                ArrayAdapter<MediaItem> mediaItemListAdapter = createMediaItemListAdapter();
                mediaItemListAdapter.addAll(mediaItemList);
                mediaItemListView.setAdapter(mediaItemListAdapter);
            } else {
                Log.i(TAG, "No keyword was found in bundle...");
                loadKeywords(Collections.<Keyword>emptyList(), false);
            }
        } else {
            Log.i(TAG, "Loading keywords from database and media items from Internet...");
            SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
            searchKeywordTask.execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        final Spinner keywordSpinner = getKeywordSpinner();
        @SuppressWarnings("unchecked")
        ArrayAdapter<Keyword> spinnerAdapter = (ArrayAdapter<Keyword>) keywordSpinner.getAdapter();
        final int keywordCount = keywordSpinner.getCount();
        Keyword[] keywordArray = new Keyword[keywordCount];

        for (int i = 0; i < keywordCount; i++) {
            keywordArray[i] = spinnerAdapter.getItem(i);
        }

        if (hasValidKeyword(keywordArray)) {
            outState.putParcelableArray(Constants.KEYWORD_ARRAY, keywordArray);
            outState.putInt(Constants.SELECTED_KEYWORD_INDEX, keywordSpinner.getSelectedItemPosition());
        } else {
            outState.putParcelableArray(Constants.KEYWORD_ARRAY, EMPTY_KEYWORD_ARRAY);
            outState.putInt(Constants.SELECTED_KEYWORD_INDEX, Spinner.INVALID_POSITION);
        }

        ListView mediaItemListView = getMediaItemListView();
        @SuppressWarnings("unchecked")
        ArrayAdapter<MediaItem> mediaItemListAdapter = (ArrayAdapter<MediaItem>) mediaItemListView.getAdapter();

        if (mediaItemListAdapter == null) {
            outState.putParcelableArray(Constants.MEDIA_ITEM_ARRAY, EMPTY_MEDIA_ITEM_ARRAY);
            return;
        }

        final int mediaItemCount = mediaItemListAdapter.getCount();
        MediaItem[] mediaItemArray = new MediaItem[mediaItemCount];

        for (int i = 0; i < mediaItemCount; i++) {
            mediaItemArray[i] = mediaItemListAdapter.getItem(i);
        }

        outState.putParcelableArray(Constants.MEDIA_ITEM_ARRAY, mediaItemArray);
    }

    protected Spinner getKeywordSpinner() {
        return (Spinner) findViewById(R.id.keyword_spinner);
    }

    protected ListView getMediaItemListView() {
        return (ListView) findViewById(R.id.mediaItem_list);
    }

    protected Button getSearchButton() {
        return (Button) findViewById(R.id.search_button);
    }

    private boolean hasValidKeyword(Keyword[] keywordArray) {
        if (keywordArray == null || keywordArray.length == 0) {
            return false;
        }

        if (keywordArray.length >= 2) {
            return true;
        }

        Keyword keyword = keywordArray[0];

        if (keyword.getWord().equals(getString(R.string.placeholder_keyword_text))) {
            return false;
        }

        return true;
    }
}
