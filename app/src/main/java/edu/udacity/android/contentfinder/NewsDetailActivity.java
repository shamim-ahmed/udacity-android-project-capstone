package edu.udacity.android.contentfinder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.udacity.android.contentfinder.task.db.SaveMediaItemTask;
import edu.udacity.android.contentfinder.model.MediaItem;

public class NewsDetailActivity extends AbstractMediaItemDetailActivity {

    @Override
    protected void loadImage(MediaItem mediaItem) {
        ImageView imageView = (ImageView) findViewById(R.id.mediaItem_detail_image_content);
        imageView.setVisibility(View.GONE);
    }

    @Override
    protected void configureSaveButton(final MediaItem mediaItem) {
        Button saveNewsButton = (Button) findViewById(R.id.favorite_button);
        saveNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMediaItemTask saveMediaItemTask = new SaveMediaItemTask(NewsDetailActivity.this, mediaItem);
                saveMediaItemTask.execute();
            }
        });

        disableSaveButtonIfAlreadySaved(mediaItem);
    }
}
