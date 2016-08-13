package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.task.db.CheckMediaItemExistsTask;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.StringUtils;

public abstract class AbstractMediaDetailActivity extends AbstractActivity {
    private static final String TAG = AbstractMediaDetailActivity.class.getSimpleName();
    private static final String TEXT_PLAIN_MIME_TYPE = "text/plain";

    private String shareUrl;

    protected abstract void loadImage(MediaItem mediaItem);
    protected abstract void configureSaveButton(MediaItem mediaItem);

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
        shareUrl = mediaItem.getWebUrl();

        // populate the views
        TextView titleView = (TextView) findViewById(R.id.mediaItem_detail_title);
        titleView.setText(mediaItem.getTitle());

        TextView descriptionView = (TextView) findViewById(R.id.mediaItem_detail_description);
        descriptionView.setText(mediaItem.getDescription());

        TextView sourceView = (TextView) findViewById(R.id.mediaItem_detail_source);
        sourceView.setText(getString(R.string.content_source, mediaItem.getSource()));

        loadImage(mediaItem);
        configureOpenButton(mediaItem);
        configureSaveButton(mediaItem);
        loadAdvertisement();
    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_item_detail, menu);
        MenuItem shareMenuItem = menu.findItem(R.id.share_link);

        if (shareMenuItem != null && StringUtils.isNotBlank(shareUrl)) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(TEXT_PLAIN_MIME_TYPE);
            intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
            shareActionProvider.setShareIntent(intent);
        }

        return true;
    }

    protected void disableSaveButtonIfAlreadySaved(MediaItem mediaItem) {
        // disable the save button if the media is already saved
        CheckMediaItemExistsTask mediaExistsTask = new CheckMediaItemExistsTask(this, mediaItem);
        mediaExistsTask.execute();
    }

    private Map<String, Parcelable> getMediaItemAndKeyword(Bundle savedInstanceState) {
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

    private void configureOpenButton(final MediaItem mediaItem) {
        Button openLinkButton = (Button) findViewById(R.id.open_link_button);
        openLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mediaItem.getWebUrl()));
                startActivity(intent);
            }
        });
    }
}
