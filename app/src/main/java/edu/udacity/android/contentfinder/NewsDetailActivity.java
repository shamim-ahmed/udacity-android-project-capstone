package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            SearchResult resultItem = (SearchResult) bundle.get(Constants.SELECTED_NEWS_KEY);

            if (resultItem != null) {
                TextView titleView = (TextView) findViewById(R.id.news_detail_title);
                titleView.setText(resultItem.getTitle());

                TextView descriptionView = (TextView) findViewById(R.id.news_detail_description);
                descriptionView.setText(resultItem.getDescription());

                TextView sourceView = (TextView) findViewById(R.id.news_detail_source);
                sourceView.setText(String.format("Source : %s", resultItem.getSource()));

                TextView webUrlView = (TextView) findViewById(R.id.news_detail_web_url);
                webUrlView.setText(Html.fromHtml(String.format("Read the full article <a href=\"%s\">here</a>", resultItem.getWebUrl())));
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
