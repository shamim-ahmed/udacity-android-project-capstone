package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.service.BingNewsSearchService;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

public class NewsSearchActivity extends AppCompatActivity implements KeywordAware {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.news_keyword_spinner);

        ListView newsList = (ListView) findViewById(R.id.news_list);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult selectedResult = (SearchResult) parent.getItemAtPosition(position);
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                Intent intent = new Intent(NewsSearchActivity.this, NewsDetailActivity.class);
                intent.putExtra(Constants.SELECTED_NEWS_KEY, selectedResult);
                intent.putExtra(Constants.SELECTED_NEWS_KEYWORD, selectedKeyword);
                startActivity(intent);
            }
        });

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

        SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
        searchKeywordTask.execute();
    }

    @Override
    public void loadKeywords(List<Keyword> keywordList) {
        ArrayAdapter<Keyword> adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(keywordList);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.news_keyword_spinner);
        keywordSpinner.setAdapter(adapter);

        if (adapter.getCount() > 0) {
            keywordSpinner.setSelection(0);
        }

        Button searchButton = (Button) findViewById(R.id.news_search_button);
        searchButton.performClick();
    }
}
