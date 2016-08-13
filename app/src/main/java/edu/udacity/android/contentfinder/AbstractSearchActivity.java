package edu.udacity.android.contentfinder;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.ui.KeywordSpinnerAdapter;

public abstract class AbstractSearchActivity extends AbstractActivity {

    public void loadKeywords(List<Keyword> keywordList, boolean mediaSearchFlag) {
        ArrayAdapter<Keyword> adapter = new KeywordSpinnerAdapter(this);
        adapter.addAll(keywordList);

        final Spinner keywordSpinner = (Spinner) findViewById(R.id.keyword_spinner);
        keywordSpinner.setAdapter(adapter);

        Button searchButton = (Button) findViewById(R.id.search_button);

        if (adapter.getCount() > 0) {
            keywordSpinner.setSelection(0);
        } else {
            String dummyStr = getString(R.string.placeholder_keyword_text);
            Keyword dummyKeyword = new Keyword();
            dummyKeyword.setWord(dummyStr);
            adapter.add(dummyKeyword);

            // disable the UI items
            keywordSpinner.setEnabled(false);
            searchButton.setEnabled(false);
        }

        if (mediaSearchFlag) {
            searchButton.performClick();
        }
    }
}
