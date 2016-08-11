package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.util.Constants;

/**
 * Created by shamim on 8/11/16.
 */
public abstract class AbstractMediaItemSearchActivity extends AbstractSearchActivity {
    private static final String TAG = AbstractMediaItemSearchActivity.class.getSimpleName();

    protected abstract Spinner getKeywordSpinner();

    protected abstract ListView getMediaItemListView();

    protected abstract ArrayAdapter<MediaItem> createMediaItemListAdapter();

    protected void loadApplicationData(Bundle savedInstanceState) {
        Spinner keywordSpinner = getKeywordSpinner();
        ListView mediaItemListView = getMediaItemListView();

        Parcelable[] keywordArray = null;
        int selectedKeywordIndex = Spinner.INVALID_POSITION;

        if (savedInstanceState != null) {
            keywordArray = savedInstanceState.getParcelableArray(Constants.KEYWORD_ARRAY);
            selectedKeywordIndex = savedInstanceState.getInt(Constants.SELECTED_KEYWORD_INDEX);
        }

        if (keywordArray != null && keywordArray.length > 0 && selectedKeywordIndex != Spinner.INVALID_POSITION) {
            Log.i(TAG, "Restoring keywords and media items from bundle...");
            // load keywords in spinner
            List<Keyword> keywordList = new ArrayList<>();

            for (int i = 0; i < keywordArray.length; i++) {
                keywordList.add((Keyword) keywordArray[i]);
            }

            loadKeywords(keywordList, false);
            keywordSpinner.setSelection(selectedKeywordIndex);

            // load media items
            List<MediaItem> mediaItemList = new ArrayList<>();

            Parcelable[] mediaItemArray = savedInstanceState.getParcelableArray(Constants.MEDIA_ITEM_ARRAY);

            if (mediaItemArray != null && mediaItemArray.length > 0) {
                for (int i = 0; i < mediaItemArray.length; i++) {
                    mediaItemList.add((MediaItem) mediaItemArray[i]);
                }
            }

            ArrayAdapter<MediaItem> mediaItemListAdapter = createMediaItemListAdapter();
            mediaItemListAdapter.addAll(mediaItemList);
            mediaItemListView.setAdapter(mediaItemListAdapter);
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
        ArrayAdapter<Keyword> spinnerAdapter = (ArrayAdapter<Keyword>) keywordSpinner.getAdapter();
        final int keywordCount = keywordSpinner.getCount();
        Keyword[] keywordArray = new Keyword[keywordCount];

        for (int i = 0; i < keywordCount; i++) {
            keywordArray[i] = spinnerAdapter.getItem(i);
        }

        outState.putParcelableArray(Constants.KEYWORD_ARRAY, keywordArray);
        outState.putInt(Constants.SELECTED_KEYWORD_INDEX, keywordSpinner.getSelectedItemPosition());

        ListView mediaItemListView = getMediaItemListView();
        ArrayAdapter<MediaItem> mediaItemListAdapter = (ArrayAdapter<MediaItem>) mediaItemListView.getAdapter();
        final int mediaItemCount = mediaItemListAdapter.getCount();
        MediaItem[] mediaItemArray = new MediaItem[mediaItemCount];

        for (int i = 0; i < mediaItemCount; i++) {
            mediaItemArray[i] = mediaItemListAdapter.getItem(i);
        }

        outState.putParcelableArray(Constants.MEDIA_ITEM_ARRAY, mediaItemArray);
    }
}
