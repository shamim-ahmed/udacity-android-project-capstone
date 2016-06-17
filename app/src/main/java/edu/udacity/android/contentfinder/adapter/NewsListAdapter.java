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
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResult newsItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.news_title);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.news_description);

        titleView.setText(newsItem.getTitle());
        descriptionView.setText(newsItem.getDescription());

        return convertView;
    }
}