package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;

/**
 * Created by shamim on 8/7/16.
 */
public class SavedMediaItemSearchActivity extends AbstractSearchActivity {
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

            }
        });

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO implement it
            }
        });

        SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
        searchKeywordTask.execute();

        loadAdvertisement();
    }
}
