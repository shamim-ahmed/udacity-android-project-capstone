package edu.udacity.android.contentfinder;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;

public abstract class AbstractSearchActivity extends AbstractActivity {
    public abstract void loadKeywords(List<Keyword> keywordList, boolean mediaSearchFlag);
}
