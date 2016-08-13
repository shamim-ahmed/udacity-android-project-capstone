package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.Keyword;

public class KeywordListAdapter extends ArrayAdapter<Keyword> {
    private static final String DATE_FORMAT_STR = "yyyy-MM-dd";

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

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR, Locale.US);
        Date createdDate = keyword.getCreatedDate();

        if (createdDate != null) {
            String dateStr = dateFormat.format(createdDate);
            wordView.setText(keyword.getWord());
            createdDateView.setText(context.getString(R.string.keyword_created_on, dateStr));
        }

        return containerView;
    }
}
