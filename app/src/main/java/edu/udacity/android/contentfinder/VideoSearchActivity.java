package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.provider.YouTubeVideoSearchServiceProvider;
import edu.udacity.android.contentfinder.ui.VideoListAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class VideoSearchActivity extends AbstractMediaItemSearchActivity {

    @Override
    protected void configureActionListeners() {
        final Spinner keywordSpinner = getKeywordSpinner();

        final ListView videoList = getMediaItemListView();
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem searchResult = (MediaItem) videoList.getItemAtPosition(position);
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                Intent intent = new Intent(VideoSearchActivity.this, VideoDetailActivity.class);
                intent.putExtra(Constants.SELECTED_MEDIA_ITEM, searchResult);
                intent.putExtra(Constants.SELECTED_KEYWORD, selectedKeyword);
                startActivity(intent);
            }
        });

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                if (selectedKeyword != null) {
                    YouTubeVideoSearchServiceProvider searchService = YouTubeVideoSearchServiceProvider.getInstance();
                    searchService.performSearch(selectedKeyword.getWord(), VideoSearchActivity.this);
                }
            }
        });
    }

    @Override
    protected ArrayAdapter<MediaItem> createMediaItemListAdapter() {
        return new VideoListAdapter(this);
    }
}
