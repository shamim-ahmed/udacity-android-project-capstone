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
import edu.udacity.android.contentfinder.service.BingImageSearchService;
import edu.udacity.android.contentfinder.service.SearchService;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

public class ImageSearchActivity extends AppCompatActivity implements KeywordAware {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView newsList = (ListView) findViewById(R.id.image_list);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult selectedResult = (SearchResult) parent.getItemAtPosition(position);
                Intent intent = new Intent(ImageSearchActivity.this, ImageDetailActivity.class);
                intent.putExtra(Constants.SELECTED_IMAGE_KEY, selectedResult);
                startActivity(intent);
            }
        });

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.image_keyword_spinner);

        final Button searchButton = (Button) findViewById(R.id.image_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword selectedKeyword = (Keyword) keywordSpinner.getSelectedItem();

                if (selectedKeyword != null) {
                    SearchService searchService = BingImageSearchService.getInstance();
                    searchService.performSearch(selectedKeyword.getWord(), ImageSearchActivity.this);
                }
            }
        });

        SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
        searchKeywordTask.execute();
    }

    @Override
    public void loadKeywords(List<Keyword> keywordList) {
        final Spinner keywordSpinner = (Spinner) findViewById(R.id.image_keyword_spinner);
        ArrayAdapter<Keyword> adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(keywordList);

        keywordSpinner.setAdapter(adapter);

        if (adapter.getCount() > 0) {
            keywordSpinner.setSelection(0);
        }

        final Button searchButton = (Button) findViewById(R.id.image_search_button);
        searchButton.performClick();
    }
}
