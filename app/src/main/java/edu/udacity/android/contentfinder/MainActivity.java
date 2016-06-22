package edu.udacity.android.contentfinder;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import edu.udacity.android.contentfinder.service.BingNewsSearchService;
import edu.udacity.android.contentfinder.service.BingImageSearchService;
import edu.udacity.android.contentfinder.ui.NewsSearchResultFragment;
import edu.udacity.android.contentfinder.ui.ImageSearchResultFragment;
import edu.udacity.android.contentfinder.ui.TweetSearchResultFragment;
import edu.udacity.android.contentfinder.ui.VideoSearchResultFragment;
import edu.udacity.android.contentfinder.ui.ViewPagerAdapter;

/**
 * TODO check the example here: http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
 */
public class MainActivity extends AppCompatActivity {
    private static final String NEWS_TAB_TITLE = "News";
    private static final String PHOTOS_TAB_TITLE = "Photos";
    private static final String VIDEOS_TAB_TITLE = "Videos";
    private static final String TWEETS_TAB_TITLE = "Tweets";

    private Map<String, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Fragment newsFragment = fragmentMap.get(NEWS_TAB_TITLE);
        BingNewsSearchService.getInstance().performSearch("us election 2016", this, newsFragment);

        Fragment photosFragment = fragmentMap.get(PHOTOS_TAB_TITLE);
        BingImageSearchService.getInstance().performSearch("Daisy", this, photosFragment);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment newsFragment = new NewsSearchResultFragment();
        fragmentMap.put(NEWS_TAB_TITLE, newsFragment);
        pagerAdapter.addFragmentWithTitle(newsFragment, NEWS_TAB_TITLE);

        Fragment photosFragment = new ImageSearchResultFragment();
        fragmentMap.put(PHOTOS_TAB_TITLE, photosFragment);
        pagerAdapter.addFragmentWithTitle(photosFragment, PHOTOS_TAB_TITLE);

        Fragment videosFragment = new VideoSearchResultFragment();
        fragmentMap.put(VIDEOS_TAB_TITLE, videosFragment);
        pagerAdapter.addFragmentWithTitle(videosFragment, VIDEOS_TAB_TITLE);

        Fragment tweetsFragment = new TweetSearchResultFragment();
        fragmentMap.put(TWEETS_TAB_TITLE, tweetsFragment);
        pagerAdapter.addFragmentWithTitle(tweetsFragment, TWEETS_TAB_TITLE);

        viewPager.setAdapter(pagerAdapter);
    }
}
