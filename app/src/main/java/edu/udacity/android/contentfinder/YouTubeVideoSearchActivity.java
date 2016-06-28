package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.service.YouTubeVideoSearchService;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;

public class YouTubeVideoSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.video_keyword_spinner);
        loadKeywords(keywordSpinner);

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
    }

    private void loadKeywords(Spinner keywordSpinner) {
        ContentFinderApplication application = (ContentFinderApplication) getApplication();
        KeywordSpinnerAdapter adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(application.getKeyWords());

        keywordSpinner.setAdapter(adapter);

        if (keywordSpinner.getCount() > 0) {
            keywordSpinner.setSelection(0);
        }
    }
}
