package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.task.db.CheckMediaItemExistsTask;
import edu.udacity.android.contentfinder.task.db.SaveMediaItemTask;
import edu.udacity.android.contentfinder.util.AppUtils;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class NewsDetailActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            return;
        }

        final MediaItem mediaItem = (MediaItem) bundle.get(Constants.SELECTED_NEWS_KEY);
        final Keyword keyword = (Keyword) bundle.get(Constants.SELECTED_NEWS_KEYWORD);

        if (mediaItem == null || keyword == null) {
            return;
        }

        mediaItem.setKeywordId(keyword.getId());

        TextView titleView = (TextView) findViewById(R.id.news_detail_title);
        titleView.setText(mediaItem.getTitle());

        TextView descriptionView = (TextView) findViewById(R.id.news_detail_description);
        descriptionView.setText(mediaItem.getSummary());

        TextView sourceView = (TextView) findViewById(R.id.news_detail_source);
        sourceView.setText(getString(R.string.content_source, AppUtils.getSource(mediaItem.getWebUrl())));

        TextView webUrlView = (TextView) findViewById(R.id.news_detail_web_url);
        webUrlView.setText(Html.fromHtml(String.format("%s %s", getString(R.string.news_detail_full_article), String.format("<a href=\"%s\">here</a>", mediaItem.getWebUrl()))));

        Button saveNewsButton = (Button) findViewById(R.id.favorite_button);
        saveNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMediaItemTask saveMediaItemTask = new SaveMediaItemTask(NewsDetailActivity.this, mediaItem);
                saveMediaItemTask.execute();
            }
        });

        // disable the save button if the media is already saved
        CheckMediaItemExistsTask mediaExistsTask = new CheckMediaItemExistsTask(this, mediaItem);
        mediaExistsTask.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadAdvertisement();
    }
}
