package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.MediaItem;

public class VideoListAdapter extends ArrayAdapter<MediaItem> {
    public VideoListAdapter(Context context) {
        super(context, R.layout.video_item);
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        MediaItem result = getItem(position);
        Context context = getContext();

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        }

        // TODO investigate thumbnail resize issue
        Resources resources = context.getResources();
        int width = (int) resources.getDimension(R.dimen.mediaList_thumb_width);
        int height = (int) resources.getDimension(R.dimen.mediaList_thumb_height);

        ImageView thumbnailView = (ImageView) containerView.findViewById(R.id.video_item_thumbnail);
        Picasso.with(context)
                .load(result.getWebUrl())
                .noFade()
                .resize(width, height)
                .centerInside()
                .into(thumbnailView);

        TextView titleView = (TextView) containerView.findViewById(R.id.video_item_title);
        titleView.setText(result.getTitle());

        TextView descriptionView = (TextView) containerView.findViewById(R.id.video_item_description);
        descriptionView.setText(result.getDescription());

        return containerView;
    }
}
