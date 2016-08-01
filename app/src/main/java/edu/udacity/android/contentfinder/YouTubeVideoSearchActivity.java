package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.service.YouTubeVideoSearchService;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class YouTubeVideoSearchActivity extends AppCompatActivity implements KeywordAware {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.video_keyword_spinner);

        final ListView videoList = (ListView) findViewById(R.id.video_list);
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem searchResult = (MediaItem) videoList.getItemAtPosition(position);
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                Intent intent = new Intent(YouTubeVideoSearchActivity.this, YouTubeVideoDetailActivity.class);
                intent.putExtra(Constants.SELECTED_VIDEO_KEY, searchResult);
                intent.putExtra(Constants.SELECTED_NEWS_KEYWORD, selectedKeyword);
                startActivity(intent);
            }
        });

        Button searchButton = (Button) findViewById(R.id.video_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                if (selectedKeyword != null) {
                    YouTubeVideoSearchService searchService = YouTubeVideoSearchService.getInstance();
                    searchService.performSearch(selectedKeyword.getWord(), YouTubeVideoSearchActivity.this);
                }
            }
        });

        SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
        searchKeywordTask.execute();
    }

    @Override
    public void loadKeywords(List<Keyword> keywordList) {
        KeywordSpinnerAdapter adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(keywordList);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.video_keyword_spinner);
        keywordSpinner.setAdapter(adapter);

        if (keywordSpinner.getCount() > 0) {
            keywordSpinner.setSelection(0);
        }

        Button searchButton = (Button) findViewById(R.id.video_search_button);
        if (keywordSpinner.getCount() > 0) {
            searchButton.performClick();
        }
    }
}
