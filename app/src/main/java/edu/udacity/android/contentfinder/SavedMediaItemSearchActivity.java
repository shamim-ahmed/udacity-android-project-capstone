package edu.udacity.android.contentfinder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.task.db.SearchMediaItemTask;
import edu.udacity.android.contentfinder.ui.MediaItemListAdapter;
import edu.udacity.android.contentfinder.util.Constants;

public class SavedMediaItemSearchActivity extends AbstractMediaItemSearchActivity implements MediaItemListContainer {
    @Override
    protected void configureActionListeners() {
        final Spinner keywordSpinner = getKeywordSpinner();

        final ListView savedMediaItemListView = getMediaItemListView();
        savedMediaItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem mediaItem = (MediaItem) savedMediaItemListView.getItemAtPosition(position);
                Keyword keyword = (Keyword) keywordSpinner.getSelectedItem();
                Class<? extends Activity> activityClass = null;

                switch (mediaItem.getContentType()) {
                    case NEWS: {
                        activityClass = NewsDetailActivity.class;
                        break;
                    }
                    case PHOTO: {
                        activityClass = ImageDetailActivity.class;
                        break;
                    }
                    case VIDEO: {
                        activityClass = VideoDetailActivity.class;
                        break;
                    }
                    default: {
                        break;
                    }
                }

                Intent intent = new Intent(SavedMediaItemSearchActivity.this, activityClass);
                intent.putExtra(Constants.SELECTED_MEDIA_ITEM, mediaItem);
                intent.putExtra(Constants.SELECTED_KEYWORD, keyword);
                startActivity(intent);
            }
        });

        final Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword keyword = (Keyword) keywordSpinner.getSelectedItem();

                if (keyword != null) {
                    // search for media items associated with the keyword
                    SearchMediaItemTask searchTask = new SearchMediaItemTask(SavedMediaItemSearchActivity.this, keyword);
                    searchTask.execute();
                }
            }
        });
    }

    @Override
    public void loadMediaItems(List<MediaItem> mediaItems) {
        MediaItemListAdapter adapter = new MediaItemListAdapter(this);
        adapter.addAll(mediaItems);

        ListView mediaItemListView = getMediaItemListView();
        mediaItemListView.setAdapter(adapter);
    }

    @Override
    protected Spinner getKeywordSpinner() {
        return (Spinner) findViewById(R.id.keyword_spinner);
    }

    @Override
    protected ListView getMediaItemListView() {
        return (ListView) findViewById(R.id.mediaItem_list);
    }

    @Override
    protected ArrayAdapter<MediaItem> createMediaItemListAdapter() {
        return new MediaItemListAdapter(this);
    }
}
