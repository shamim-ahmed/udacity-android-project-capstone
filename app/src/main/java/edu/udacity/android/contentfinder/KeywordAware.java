package edu.udacity.android.contentfinder;

import java.util.List;

import edu.udacity.android.contentfinder.model.Keyword;

/**
 * Created by shamim on 7/31/16.
 */
public interface KeywordAware {
    void loadKeywords(List<Keyword> keywordList);
}
