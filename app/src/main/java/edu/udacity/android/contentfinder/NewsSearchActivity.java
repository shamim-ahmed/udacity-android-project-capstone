package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.service.BingNewsSearchService;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

public class NewsSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView newsList = (ListView) findViewById(R.id.news_list);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult selectedResult = (SearchResult) parent.getItemAtPosition(position);
                Intent intent = new Intent(NewsSearchActivity.this, NewsDetailActivity.class);
                intent.putExtra(Constants.SELECTED_NEWS_KEY, selectedResult);
                startActivity(intent);
            }
        });

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.news_keyword_spinner);
        loadKeywords(keywordSpinner);

        Button searchButton = (Button) findViewById(R.id.news_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                if (selectedKeyword != null) {
                    BingNewsSearchService searchService = BingNewsSearchService.getInstance();
                    searchService.performSearch(selectedKeyword.getWord(), NewsSearchActivity.this);
                }
            }
        });

        searchButton.performClick();
    }

    private void loadKeywords(Spinner keywordSpinner) {
        ContentFinderApplication application = (ContentFinderApplication) getApplication();
        ArrayAdapter<Keyword> adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(application.getKeyWords());

        keywordSpinner.setAdapter(adapter);
        String selectedKeyword = null;

        if (adapter.getCount() > 0) {
            keywordSpinner.setSelection(0);
        }
    }
}
