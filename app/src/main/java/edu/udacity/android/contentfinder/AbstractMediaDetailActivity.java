package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.task.db.CheckMediaItemExistsTask;
import edu.udacity.android.contentfinder.util.AppUtils;
import edu.udacity.android.contentfinder.util.Constants;

/**
 * Created by shamim on 8/12/16.
 */
public abstract class AbstractMediaDetailActivity extends AbstractActivity {
    private static final String TAG = AbstractMediaDetailActivity.class.getSimpleName();

    public abstract void loadImage(MediaItem mediaItem);

    public abstract void configureButtons(MediaItem mediaItem);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Map<String, Parcelable> dataMap = getMediaItemAndKeyword(savedInstanceState);
        final MediaItem mediaItem = (MediaItem) dataMap.get(Constants.SELECTED_MEDIA_ITEM);
        final Keyword keyword = (Keyword) dataMap.get(Constants.SELECTED_KEYWORD);

        if (mediaItem == null || keyword == null) {
            return;
        }

        mediaItem.setKeywordId(keyword.getId());

        // populate the views
        TextView titleView = (TextView) findViewById(R.id.mediaItem_detail_title);
        titleView.setText(mediaItem.getTitle());

        TextView descriptionView = (TextView) findViewById(R.id.mediaItem_detail_description);
        descriptionView.setText(mediaItem.getDescription());

        TextView sourceView = (TextView) findViewById(R.id.mediaItem_detail_source);
        sourceView.setText(AppUtils.getSource(mediaItem.getWebUrl()));

        loadImage(mediaItem);
        configureButtons(mediaItem);
        disableSaveButtonIfAlreadySaved(mediaItem);
        loadAdvertisement();
    }

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

    private void disableSaveButtonIfAlreadySaved(MediaItem mediaItem) {
        // disable the save button if the media is already saved
        CheckMediaItemExistsTask mediaExistsTask = new CheckMediaItemExistsTask(this, mediaItem);
        mediaExistsTask.execute();
    }
}
