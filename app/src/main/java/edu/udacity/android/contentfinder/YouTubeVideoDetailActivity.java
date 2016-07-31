package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.SearchResult;

public class YouTubeVideoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            SearchResult resultItem = (SearchResult) bundle.get(Constants.SELECTED_VIDEO_KEY);

            if (resultItem != null) {
                ImageView imageView = (ImageView) findViewById(R.id.video_detail_image);
                TextView videoTitle = (TextView) findViewById(R.id.video_detail_title);
                TextView videoDescription = (TextView) findViewById(R.id.video_detail_description);
                TextView videoSource = (TextView) findViewById(R.id.video_detail_source);

                Picasso.with(this)
                        .load(resultItem.getWebUrl())
                        .noFade()
                        .resize(1000, 600)
                        .centerInside()
                        .into(imageView);

                videoTitle.setText(resultItem.getTitle());
                videoDescription.setText(resultItem.getDescription());
                videoSource.setText(resultItem.getSource());
            }
        }
    }
}
