package edu.udacity.android.contentfinder;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;

/**
 * Created by shamim on 8/7/16.
 */
public abstract class AbstractSearchActivity extends AbstractActivity {

    public void loadKeywords(List<Keyword> keywordList, boolean mediaSearchFlag) {
        ArrayAdapter<Keyword> adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(keywordList);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.keyword_spinner);
        keywordSpinner.setAdapter(adapter);

        if (adapter.getCount() > 0) {
            keywordSpinner.setSelection(0);
        }

        if (mediaSearchFlag) {
            Button searchButton = (Button) findViewById(R.id.search_button);
            searchButton.performClick();
        }
    }
}
