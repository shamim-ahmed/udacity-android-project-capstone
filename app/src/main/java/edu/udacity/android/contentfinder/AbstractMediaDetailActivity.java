package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.util.Constants;

/**
 * Created by shamim on 8/12/16.
 */
public class AbstractMediaDetailActivity extends AbstractActivity {
    private static final String TAG = AbstractMediaDetailActivity.class.getSimpleName();

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            return;
        }

        final MediaItem mediaItem = (MediaItem) extras.get(Constants.SELECTED_MEDIA_ITEM);
        final Keyword keyword = (Keyword) extras.get(Constants.SELECTED_KEYWORD);

        outBundle.putParcelable(Constants.SELECTED_MEDIA_ITEM, mediaItem);
        outBundle.putParcelable(Constants.SELECTED_KEYWORD, keyword);
    }

    public Map<String, Parcelable> getMediaItemAndKeyword(Bundle savedInstanceState) {
        MediaItem mediaItem;
        Keyword keyword;

        if (savedInstanceState != null) {
            Log.i(TAG, "Restoring media item and keyword from saved bundle");
            mediaItem = savedInstanceState.getParcelable(Constants.SELECTED_MEDIA_ITEM);
            keyword = savedInstanceState.getParcelable(Constants.SELECTED_KEYWORD);
        } else {
            Log.i(TAG, "Retrieving media item and keyword from the data passed during invocation");
            Bundle extras = getIntent().getExtras();
            mediaItem = (MediaItem) extras.get(Constants.SELECTED_MEDIA_ITEM);
            keyword = (Keyword) extras.get(Constants.SELECTED_KEYWORD);
        }

        Map<String, Parcelable> resultMap = new HashMap<>();
        resultMap.put(Constants.SELECTED_MEDIA_ITEM, mediaItem);
        resultMap.put(Constants.SELECTED_KEYWORD, keyword);

        return resultMap;
    }
}
