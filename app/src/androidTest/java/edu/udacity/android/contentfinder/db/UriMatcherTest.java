package edu.udacity.android.contentfinder.db;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.model.MediaItemType;

/**
 * Created by shamim on 6/3/16.
 */
public class UriMatcherTest extends AndroidTestCase {
    private static final UriMatcher sUriMatcher = ContentFinderDataProvider.buildUriMatcher();

    public void testKeywordUris() {
        Uri uri1 = ContentFinderContract.KeywordEntry.CONTENT_URI;
        assertEquals(ContentFinderDataProvider.KEYWORD, sUriMatcher.match(uri1));

        Uri uri2 = ContentFinderContract.KeywordEntry.CONTENT_URI.buildUpon().appendPath("android").build();
        assertEquals(ContentFinderDataProvider.KEYWORD_WITH_NAME, sUriMatcher.match(uri2));
    }

    public void testMediaItemUris() {
        Uri uri1 = ContentFinderContract.MediaItemEntry.CONTENT_URI;
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM, sUriMatcher.match(uri1));

        Uri uri2 = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemId("abcdefgh");
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM_WITH_ID, sUriMatcher.match(uri2));

        Uri uri3 = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemTypeAndKeyword(MediaItemType.VIDEO, "uvwxyz");
        System.out.println(uri3);
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM_WITH_TYPE_AND_KEYWORD, sUriMatcher.match(uri3));
    }
}
