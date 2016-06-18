package edu.udacity.android.contentfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.util.SearchResult;

/**
 * Created by shamim on 6/17/16.
 */
public class NewsListAdapter extends ArrayAdapter<SearchResult> {
    private final Context context;

    public NewsListAdapter(Context context) {
        super(context, R.layout.news_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        SearchResult newsItem = getItem(position);

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        }

        TextView titleView = (TextView) containerView.findViewById(R.id.news_title);
        titleView.setText(newsItem.getTitle());

        TextView descriptionView = (TextView) containerView.findViewById(R.id.news_description);
        descriptionView.setText(newsItem.getDescription());

        TextView sourceView = (TextView) containerView.findViewById(R.id.news_source);
        sourceView.setText(String.format("Source : %s", newsItem.getSource()));

        return containerView;
    }
}