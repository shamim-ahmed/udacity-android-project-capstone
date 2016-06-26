package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.udacity.android.contentfinder.service.BingImageSearchService;
import edu.udacity.android.contentfinder.service.SearchService;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

public class ImageSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
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

        SearchService searchService = BingImageSearchService.getInstance();
        searchService.performSearch("tulip", this);
    }

}
