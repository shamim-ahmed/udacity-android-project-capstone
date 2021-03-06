package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.MediaItem;

public class NewsListAdapter extends ArrayAdapter<MediaItem> {

    public NewsListAdapter(Context context) {
        super(context, R.layout.news_item);
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        Context context = getContext();
        MediaItem newsItem = getItem(position);

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        }

        TextView titleView = (TextView) containerView.findViewById(R.id.news_item_title);
        titleView.setText(newsItem.getTitle());

        TextView descriptionView = (TextView) containerView.findViewById(R.id.news_item_description);
        descriptionView.setText(newsItem.getDescription());

        TextView sourceView = (TextView) containerView.findViewById(R.id.news_item_source);
        sourceView.setText(context.getString(R.string.content_source, newsItem.getSource()));

        return containerView;
    }
}