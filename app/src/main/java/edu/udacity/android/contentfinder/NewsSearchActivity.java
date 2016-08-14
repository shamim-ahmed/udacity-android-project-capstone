package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.provider.BingNewsSearchServiceProvider;
import edu.udacity.android.contentfinder.ui.NewsListAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class NewsSearchActivity extends AbstractMediaItemSearchActivity {
    @Override
    protected void configureActionListeners() {
        final Spinner keywordSpinner = getKeywordSpinner();

        ListView newsListView = getMediaItemListView();
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem selectedResult = (MediaItem) parent.getItemAtPosition(position);
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                Intent intent = new Intent(NewsSearchActivity.this, NewsDetailActivity.class);
                intent.putExtra(Constants.SELECTED_MEDIA_ITEM, selectedResult);
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
                    BingNewsSearchServiceProvider searchService = BingNewsSearchServiceProvider.getInstance();
                    searchService.performSearch(selectedKeyword.getWord(), NewsSearchActivity.this);
                }
            }
        });
    }

    @Override
    protected ArrayAdapter<MediaItem> createMediaItemListAdapter() {
        return new NewsListAdapter(this);
    }
}
