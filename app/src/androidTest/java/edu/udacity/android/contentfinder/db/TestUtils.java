package edu.udacity.android.contentfinder.db;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import edu.udacity.android.contentfinder.model.MediaItemType;

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
        List<ContentValues> resultList = new ArrayList<>();

        ContentValues mediaValues1 = new ContentValues();
        mediaValues1.put(ContentFinderContract.MediaItemEntry.COLUMN_ITEM_ID, "abcd");
        mediaValues1.put(ContentFinderContract.MediaItemEntry.COLUMN_CONTENT_TYPE_ID, MediaItemType.NEWS.getId());
        mediaValues1.put(ContentFinderContract.MediaItemEntry.COLUMN_KEYWORD_ID, 1L);
        mediaValues1.put(ContentFinderContract.MediaItemEntry.COLUMN_TITLE, "This is a test title");
        mediaValues1.put(ContentFinderContract.MediaItemEntry.COLUMN_SUMMARY, "This is a test summary");
        mediaValues1.put(ContentFinderContract.MediaItemEntry.COLUMN_URL, "https://www.google.com");


        ContentValues mediaValues2 = new ContentValues();
        mediaValues2.put(ContentFinderContract.MediaItemEntry.COLUMN_ITEM_ID, "efgh");
        mediaValues2.put(ContentFinderContract.MediaItemEntry.COLUMN_CONTENT_TYPE_ID, MediaItemType.NEWS.getId());
        mediaValues2.put(ContentFinderContract.MediaItemEntry.COLUMN_KEYWORD_ID, 1L);
        mediaValues2.put(ContentFinderContract.MediaItemEntry.COLUMN_TITLE, "This is a test title 2");
        mediaValues2.put(ContentFinderContract.MediaItemEntry.COLUMN_SUMMARY, "This is a test summary 2");
        mediaValues2.put(ContentFinderContract.MediaItemEntry.COLUMN_URL, "https://www.facebook.com");

        resultList.add(mediaValues1);
        resultList.add(mediaValues2);

        return resultList;
    }

    // private constructor to prevent instantiation
    private TestUtils() {
    }
}
