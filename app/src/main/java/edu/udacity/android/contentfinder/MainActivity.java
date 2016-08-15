package edu.udacity.android.contentfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.task.db.SearchKeywordTask;
import edu.udacity.android.contentfinder.ui.KeywordListAdapter;
import edu.udacity.android.contentfinder.util.Constants;

public class MainActivity extends AbstractSearchActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button addKeywordButton = (Button) findViewById(R.id.add_keyword_button);
        addKeywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddKeywordActivity.class);
                startActivity(intent);
            }
        });

        Parcelable[] keywords = null;

        if (savedInstanceState != null) {
            keywords = savedInstanceState.getParcelableArray(Constants.KEYWORD_ARRAY);
        }

        if (keywords != null && keywords.length > 0) {
            Log.i(TAG, "Restoring keywords from bundle...");
            List<Keyword> keywordList = new ArrayList<>();

            for (Parcelable p : keywords) {
                keywordList.add((Keyword) p);
            }

            loadKeywords(keywordList, false);
        } else {
            Log.i(TAG, "Loading keywords from database...");
            SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
            searchKeywordTask.execute();
        }

        loadAdvertisement();
    }

    public void onRestart() {
        super.onRestart();

        SearchKeywordTask searchKeywordTask = new SearchKeywordTask(this);
        searchKeywordTask.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, DisplayInfoActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news_search) {
            Intent intent = new Intent(this, NewsSearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_image_search) {
            Intent intent = new Intent(this, ImageSearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_video_search) {
            Intent intent = new Intent(this, VideoSearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_keyword) {
            Intent intent = new Intent(this, AddKeywordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_saved_content) {
            Intent intent = new Intent(this, SavedMediaItemSearchActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadKeywords(List<Keyword> keywordList, boolean mediaSearchFlag) {
        ListView listView = (ListView) findViewById(R.id.keyword_list);
        ArrayAdapter<Keyword> adapter = new KeywordListAdapter(this);
        adapter.addAll(keywordList);
        listView.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.add_keyword_button);

        if (adapter.getCount() == 0) {
            addButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ListView listView = (ListView) findViewById(R.id.keyword_list);
        @SuppressWarnings("unchecked")
        ArrayAdapter<Keyword> adapter = (ArrayAdapter<Keyword>) listView.getAdapter();
        final int n = adapter.getCount();
        Keyword[] keywordArray = new Keyword[n];

        for (int i = 0; i < n; i++) {
            keywordArray[i] = adapter.getItem(i);
        }

        outState.putParcelableArray(Constants.KEYWORD_ARRAY, keywordArray);
    }
}
