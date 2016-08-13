package edu.udacity.android.contentfinder;

import android.content.res.Resources;
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

        TextView videoTitle = (TextView) findViewById(R.id.video_detail_title);
        videoTitle.setText(mediaItem.getTitle());

        TextView videoDescription = (TextView) findViewById(R.id.video_detail_description);
        videoDescription.setText(mediaItem.getDescription());

        TextView videoSource = (TextView) findViewById(R.id.video_detail_source);
        videoSource.setText(AppUtils.getSource(mediaItem.getWebUrl()));

        ImageView imageView = (ImageView) findViewById(R.id.video_detail_image);
        Resources resources = getResources();

        int width = (int) resources.getDimension(R.dimen.mediaDetail_image_width);
        int height = (int) resources.getDimension(R.dimen.mediaDetail_image_height);

        Picasso.with(this)
                .load(mediaItem.getWebUrl())
                .noFade()
                .resize(width, height)
                .centerInside()
                .into(imageView);

        Button saveButton = (Button) findViewById(R.id.favorite_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMediaItemTask saveMediaItemTask = new SaveMediaItemTask(VideoDetailActivity.this, mediaItem);
                saveMediaItemTask.execute();
            }
        });

        disableSaveButtonIfAlreadySaved(mediaItem);
        loadAdvertisement();
    }
}
