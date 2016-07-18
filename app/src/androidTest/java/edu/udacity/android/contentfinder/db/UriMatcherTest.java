package edu.udacity.android.contentfinder.db;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import edu.udacity.android.contentfinder.model.MediaItemType;

/**
 * Created by shamim on 6/3/16.
 */
public class UriMatcherTest extends AndroidTestCase {
    private static final UriMatcher sUriMatcher = ContentFinderDataProvider.buildUriMatcher();

    public void testKeywordUris() {
        Uri allKeywordUri = ContentFinderContract.KeywordEntry.CONTENT_URI;
        assertEquals(ContentFinderDataProvider.KEYWORD, sUriMatcher.match(allKeywordUri));

        Uri individualKeywordUri = ContentFinderContract.KeywordEntry.buildUriFromKeywordId(1L);
        assertEquals(ContentFinderDataProvider.KEYWORD_WITH_ID, sUriMatcher.match(individualKeywordUri));
    }

    public void testMediaItemUris() {
        Uri allMediaItemUri = ContentFinderContract.MediaItemEntry.CONTENT_URI;
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM, sUriMatcher.match(allMediaItemUri));

        Uri individualMediaItemUri = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemId(123L);
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM_WITH_ID, sUriMatcher.match(individualMediaItemUri));

        Uri mediaItemWithTypeUri = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemType(MediaItemType.PHOTO);
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM_WITH_TYPE_ID, sUriMatcher.match(mediaItemWithTypeUri));

        Uri mediaItemWithKeywordIdUri = ContentFinderContract.MediaItemEntry.buildUriFromKeywordId(1L);
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM_WITH_KEYWORD_ID, sUriMatcher.match(mediaItemWithKeywordIdUri));

        Uri mediaItemWithKeywordIdAndTypeIdUri = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemTypeAndKeywordId(MediaItemType.VIDEO, 123L);
        assertEquals(ContentFinderDataProvider.MEDIA_ITEM_WITH_KEYWORD_ID_AND_TYPE_ID, sUriMatcher.match(mediaItemWithKeywordIdAndTypeIdUri));
    }
}
