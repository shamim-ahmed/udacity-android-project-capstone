package edu.udacity.android.contentfinder.db;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamim on 6/2/16.
 */
public class TestUtils {

    public static List<ContentValues> createKeywordValues() {
        List<ContentValues> resultList = new ArrayList<>();

        ContentValues keywordValues1 = new ContentValues();
        keywordValues1.put(ContentFinderContract.KeywordEntry.COLUMN_WORD, "android");
        resultList.add(keywordValues1);

        ContentValues keywordValues2 = new ContentValues();
        keywordValues2.put(ContentFinderContract.KeywordEntry.COLUMN_WORD, "nobel prize");
        resultList.add(keywordValues2);

        return resultList;
    }

    public static List<ContentValues> createMediaItemValues() {
        return null;
    }

    // private constructor to prevent instantiation
    private TestUtils() {
    }
}
