package edu.udacity.android.contentfinder;

import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import edu.udacity.android.contentfinder.task.db.SaveMediaItemTask;
import edu.udacity.android.contentfinder.model.MediaItem;

public class ImageDetailActivity extends AbstractMediaDetailActivity {

    @Override
    public void loadImage(MediaItem mediaItem) {
        ImageView imageView = (ImageView) findViewById(R.id.mediaItem_detail_image_content);

        Resources resources = getResources();
        int width = (int) resources.getDimension(R.dimen.mediaDetail_image_width);
        int height = (int) resources.getDimension(R.dimen.mediaDetail_image_height);

        Picasso.with(this)
                .load(mediaItem.getThumbnailUrl())
                .noFade()
                .resize(width, height)
                .centerInside()
                .into(imageView);
    }

    @Override
    public void configureButtons(final MediaItem mediaItem) {
        final Button saveButton = (Button) findViewById(R.id.favorite_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMediaItemTask saveMediaItemTask = new SaveMediaItemTask(ImageDetailActivity.this, mediaItem);
                saveMediaItemTask.execute();
            }
        });
    }
}
