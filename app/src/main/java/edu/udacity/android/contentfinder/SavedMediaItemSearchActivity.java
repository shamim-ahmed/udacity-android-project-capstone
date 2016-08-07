package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.task.db.SearchMediaItemTask;
import edu.udacity.android.contentfinder.ui.MediaItemListAdapter;

/**
 * Created by shamim on 8/7/16.
 */
public class SavedMediaItemSearchActivity extends AbstractSearchActivity implements MediaItemListContainer {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_mediaitem_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ListView savedMediaItemListView = (ListView) findViewById(R.id.saved_mediaItem_list);
        savedMediaItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO open new activity
            }
        });

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.keyword_spinner);
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

        // search for keywords and load them in spinner
        SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
        searchKeywordTask.execute();

        // load advertisement
        loadAdvertisement();
    }

    @Override
    public void loadMediaItems(List<MediaItem> mediaItems) {
        MediaItemListAdapter adapter = new MediaItemListAdapter(this);
        adapter.addAll(mediaItems);

        ListView mediaItemListView = (ListView) findViewById(R.id.saved_mediaItem_list);
        mediaItemListView.setAdapter(adapter);
    }
}
