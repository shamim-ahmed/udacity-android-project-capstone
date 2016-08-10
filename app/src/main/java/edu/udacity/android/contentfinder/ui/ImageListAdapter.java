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

/**
 * Created by shamim on 6/19/16.
 */
public class ImageListAdapter extends ArrayAdapter<MediaItem> {
    private final Context context;

    public ImageListAdapter(Context context) {
        super(context, R.layout.image_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        MediaItem resultItem = getItem(position);

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        }

        ImageView imageView = (ImageView) containerView.findViewById(R.id.image_summary_binary);
        TextView titleView = (TextView) containerView.findViewById(R.id.image_summary_title);
        TextView urlView = (TextView) containerView.findViewById(R.id.image_summary_url);

        Resources resources = context.getResources();
        int width = (int) resources.getDimension(R.dimen.mediaList_thumb_width);
        int height = (int) resources.getDimension(R.dimen.mediaList_thumb_height);

        Picasso.with(context)
                .load(resultItem.getThumbnailUrl())
                .noFade()
                .resize(width, height)
                .centerCrop()
                .into(imageView);

        titleView.setText(resultItem.getTitle());
        urlView.setText(resultItem.getWebUrl());

        return containerView;
    }
}