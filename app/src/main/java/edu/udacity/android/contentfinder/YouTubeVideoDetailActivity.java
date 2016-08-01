package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.task.db.SaveMediaItemTask;
import edu.udacity.android.contentfinder.util.AppUtils;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

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

        if (bundle == null) {
            return;
        }

        final MediaItem mediaItem = (MediaItem) bundle.get(Constants.SELECTED_VIDEO_KEY);
        final Keyword keyword = (Keyword) bundle.get(Constants.SELECTED_VIDEO_KEYWORD);

        if (mediaItem == null || keyword == null) {
            return;
        }

        mediaItem.setKeywordId(keyword.getId());

        ImageView imageView = (ImageView) findViewById(R.id.video_detail_image);
        TextView videoTitle = (TextView) findViewById(R.id.video_detail_title);
        TextView videoDescription = (TextView) findViewById(R.id.video_detail_description);
        TextView videoSource = (TextView) findViewById(R.id.video_detail_source);

        Picasso.with(this)
                .load(mediaItem.getWebUrl())
                .noFade()
                .resize(1000, 600)
                .centerInside()
                .into(imageView);

        videoTitle.setText(mediaItem.getTitle());
        videoDescription.setText(mediaItem.getSummary());
        videoSource.setText(AppUtils.getSource(mediaItem.getWebUrl()));

        Button saveButton = (Button) findViewById(R.id.video_favorite_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMediaItemTask saveMediaItemTask = new SaveMediaItemTask(YouTubeVideoDetailActivity.this, mediaItem);
                saveMediaItemTask.execute();
            }
        });
    }
}
