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
 * Created by shamim on 8/7/16.
 */
public class MediaItemListAdapter extends ArrayAdapter<MediaItem> {

    public MediaItemListAdapter(Context context) {
        super(context, R.layout.saved_media_item);
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        MediaItem mediaItem = getItem(position);
        Context context = getContext();

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.saved_media_item, parent, false);
        }

        ImageView iconView = (ImageView) containerView.findViewById(R.id.saved_mediaItem_icon);
        TextView titleView = (TextView) containerView.findViewById(R.id.saved_mediaITem_title);
        TextView sourceView = (TextView) containerView.findViewById(R.id.saved_mediaITem_source);

        int iconId;

        switch (mediaItem.getContentType()) {
            case NEWS:
                iconId = R.drawable.news_icon;
                break;
            case PHOTO:
                iconId = R.drawable.image_icon;
                break;
            case VIDEO:
                iconId = R.drawable.video_icon;
                break;
            default:
                iconId = R.drawable.generic_media_icon;
                break;
        }

        Resources resources = context.getResources();
        int width = (int) resources.getDimension(R.dimen.mediaList_thumb_width);
        int height = (int) resources.getDimension(R.dimen.mediaList_thumb_height);

        Picasso.with(context)
                .load(iconId)
                .noFade()
                .resize(width, height)
                .centerCrop()
                .into(iconView);

        titleView.setText(mediaItem.getTitle());
        sourceView.setText(mediaItem.getSource());

        return containerView;
    }
}
