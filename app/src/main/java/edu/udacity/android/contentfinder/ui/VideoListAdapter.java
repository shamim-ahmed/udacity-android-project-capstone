package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.MediaItem;

/**
 * Created by shamim on 6/28/16.
 */
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

        ImageView thumbnailView = (ImageView) containerView.findViewById(R.id.video_summary_thumbnail);
        Picasso.with(context)
                .load(result.getWebUrl())
                .noFade()
                .resize(200, 120)
                .centerInside()
                .into(thumbnailView);

        TextView titleView = (TextView) containerView.findViewById(R.id.video_summary_title);
        titleView.setText(result.getTitle());

        return containerView;
    }
}
