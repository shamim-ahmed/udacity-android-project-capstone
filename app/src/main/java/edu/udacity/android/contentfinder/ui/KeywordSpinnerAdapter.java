package edu.udacity.android.contentfinder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 6/26/16.
 */
public class KeywordSpinnerAdapter extends ArrayAdapter<Keyword> {
    public KeywordSpinnerAdapter(Context context) {
        super(context, R.layout.spinner_item);
    }

    @Override
    public View getView(int position, View containerView, ViewGroup parent) {
        Context context = getContext();
        Keyword keyword  = getItem(position);

        if (containerView == null) {
            containerView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        }

        TextView spinnerTextView = (TextView) containerView.findViewById(R.id.spinner_item_text);
        spinnerTextView.setText(keyword.getWord());

        return containerView;
    }
}
