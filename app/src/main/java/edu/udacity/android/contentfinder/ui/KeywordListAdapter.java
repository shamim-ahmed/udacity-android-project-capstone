package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 6/26/16.
 */
public class KeywordListAdapter extends ArrayAdapter<Keyword> {

    public KeywordListAdapter(Context context) {
        super(context, R.layout.keyword);
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        Context context = getContext();
        Keyword keyword = getItem(position);

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.keyword, parent, false);
        }

        TextView wordView = (TextView) containerView.findViewById(R.id.word);
        TextView createdDateView = (TextView) containerView.findViewById(R.id.created_date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(keyword.getCreatedDate());

        wordView.setText(keyword.getWord());
        createdDateView.setText(String.format("Created on : %s", dateStr));

        return containerView;
    }
}
