package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Map;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.task.db.CheckMediaItemExistsTask;
import edu.udacity.android.contentfinder.task.db.SaveMediaItemTask;
import edu.udacity.android.contentfinder.util.AppUtils;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.model.MediaItem;

public class VideoDetailActivity extends AbstractMediaDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            return;
        }

        Map<String, Parcelable> dataMap = getMediaItemAndKeyword(savedInstanceState);
        final MediaItem mediaItem = (MediaItem) dataMap.get(Constants.SELECTED_MEDIA_ITEM);
        final Keyword keyword = (Keyword) dataMap.get(Constants.SELECTED_KEYWORD);

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
        videoDescription.setText(mediaItem.getDescription());
        videoSource.setText(AppUtils.getSource(mediaItem.getWebUrl()));

        Button saveButton = (Button) findViewById(R.id.favorite_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMediaItemTask saveMediaItemTask = new SaveMediaItemTask(VideoDetailActivity.this, mediaItem);
                saveMediaItemTask.execute();
            }
        });

        // disable the save button if the media is already saved
        CheckMediaItemExistsTask mediaExistsTask = new CheckMediaItemExistsTask(this, mediaItem);
        mediaExistsTask.execute();

        loadAdvertisement();
    }
}
