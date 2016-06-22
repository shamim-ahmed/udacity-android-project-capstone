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
import edu.udacity.android.contentfinder.util.SearchResult;

/**
 * Created by shamim on 6/19/16.
 */
public class ImageListAdapter extends ArrayAdapter<SearchResult> {
    private final Context context;

    public ImageListAdapter(Context context) {
        super(context, R.layout.image_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        SearchResult resultItem = getItem(position);

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        }

        ImageView imageView = (ImageView) containerView.findViewById(R.id.image_binary);
        TextView titleView = (TextView) containerView.findViewById(R.id.image_title);
        TextView sourceView = (TextView) containerView.findViewById(R.id.image_source);

        // TODO use the right URL here and distinguish between web and bing URL
        Picasso.with(context)
                .load(resultItem.getWebUrl())
                .noFade()
                .into(imageView);

        titleView.setText(resultItem.getTitle());
        sourceView.setText(resultItem.getSource());

        return containerView;
    }
}