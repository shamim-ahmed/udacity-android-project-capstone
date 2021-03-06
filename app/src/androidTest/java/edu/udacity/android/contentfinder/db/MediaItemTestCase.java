package edu.udacity.android.contentfinder.db;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;

import java.util.ArrayList;
import java.util.List;

import edu.udacity.android.contentfinder.model.MediaItemType;

/**
 * Created by shamim on 6/2/16.
 */
public class MediaItemTestCase extends ProviderTestCase2<ContentFinderDataProvider> {

    private static final UriMatcher sUriMatcher = ContentFinderDataProvider.buildUriMatcher();

    public MediaItemTestCase() {
        super(ContentFinderDataProvider.class, "edu.udacity.android.contentfinder");
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        clearTables();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        clearTables();
    }

    public void testMediaInsertAndDelete() {
        List<ContentValues> contentValueList = TestUtils.createMediaItemValues();
        ContentFinderDataProvider provider = getProvider();
        List<Uri> resultUriList = new ArrayList<>();

        for (ContentValues values : contentValueList) {
            Uri uri = provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
            assertNotNull("uri is null", uri);
            assertEquals("uri type is different than expected", ContentFinderDataProvider.MEDIA_ITEM_WITH_ID, sUriMatcher.match(uri));
            resultUriList.add(uri);

            Long id = ContentFinderContract.MediaItemEntry.getMediaItemIdFromUri(uri);
            assertNotNull("id is null", id);

            Uri constructedUri = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemId(id);
            assertEquals("constructed uri is different than expected", uri.toString(), constructedUri.toString());
        }

        // now delete all the media items
        for (Uri uri : resultUriList) {
            Long id = ContentFinderContract.MediaItemEntry.getMediaItemIdFromUri(uri);
            assertNotNull("id is null", id);
            int n = provider.delete(uri, ContentFinderDataProvider.MEDIA_ID_SELECTION, new String[] {id.toString()});
            assertTrue("deletion was not successful", n == 1);
        }
    }

    public void testAllMediaSearch() {
        List<ContentValues> contentValuesList = TestUtils.createMediaItemValues();
        ContentFinderDataProvider provider = getProvider();

        for (ContentValues values : contentValuesList) {
            provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
        }

        final int n = contentValuesList.size();
        Cursor cursor = provider.query(ContentFinderContract.MediaItemEntry.CONTENT_URI, null, null, null, ContentFinderContract.MediaItemEntry.COLUMN_CONTENT_TYPE_ID);
        assertNotNull(cursor);
        assertTrue("cursor is null", cursor.moveToFirst());
        assertEquals("cursor size is different than expected", n, cursor.getCount());
        cursor.close();
    }

    public void testIndividualMediaSearch() {
        List<ContentValues> contentValuesList = TestUtils.createMediaItemValues();
        ContentFinderDataProvider provider = getProvider();
        List<Uri> resultUriList = new ArrayList<>();

        for (ContentValues values : contentValuesList) {
            Uri uri = provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
            resultUriList.add(uri);
        }

        for (Uri uri : resultUriList) {
            Long mediaItemId = ContentFinderContract.MediaItemEntry.getMediaItemIdFromUri(uri);
            assertNotNull(mediaItemId);
            Cursor cursor = provider.query(uri, null, ContentFinderDataProvider.MEDIA_ID_SELECTION, new String[] {mediaItemId.toString()}, ContentFinderContract.MediaItemEntry._ID);
            assertNotNull(cursor);
            assertTrue("cursor is empty", cursor.moveToFirst());
            assertEquals("cursor size is different than expected", 1, cursor.getCount());
            cursor.close();
        }
    }

    public void testMediaWithKeywordSearch() {
        ContentFinderDataProvider provider = getProvider();
        List<ContentValues> keywordContentValueList = TestUtils.createKeywordValues();

        for (ContentValues values : keywordContentValueList) {
            provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
        }

        List<ContentValues> mediaContentValueList = TestUtils.createMediaItemValues();

        for (ContentValues values : mediaContentValueList) {
            provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
        }

        Uri keywordSearchUri = ContentFinderContract.MediaItemEntry.buildUriFromKeywordId(1L);
        Long keywordId = ContentFinderContract.MediaItemEntry.getKeywordIdFromUri(keywordSearchUri);
        assertNotNull(keywordId);
        Cursor cursor = provider.query(keywordSearchUri, null, ContentFinderDataProvider.MEDIA_ITEM_KEYWORD_ID_SELECTION, new String[] {keywordId.toString()}, ContentFinderContract.KeywordEntry._ID);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        assertEquals("cursor size is different than expected", mediaContentValueList.size(), cursor.getCount());
    }

    public void testMediaWithTypeSearch() {
        ContentFinderDataProvider provider = getProvider();
        List<ContentValues> keywordContentValueList = TestUtils.createKeywordValues();

        for (ContentValues values : keywordContentValueList) {
            provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
        }

        List<ContentValues> mediaContentValueList = TestUtils.createMediaItemValues();

        for (ContentValues values : mediaContentValueList) {
            provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
        }

        Uri mediaTypeSearchUri = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemType(MediaItemType.NEWS);
        MediaItemType mediaItemType = ContentFinderContract.MediaItemEntry.getMediaItemTypeFromUri(mediaTypeSearchUri);
        assertNotNull(mediaItemType);
        Cursor cursor = provider.query(mediaTypeSearchUri, null, ContentFinderDataProvider.MEDIA_ITEM_WITH_TYPE_ID_SELECTION, new String[] {mediaItemType.getId().toString()}, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        assertEquals("cursor size is different than expected", mediaContentValueList.size(), cursor.getCount());
    }

    public void testMediaWithTypeAndKeywordSearch() {
        ContentFinderDataProvider provider = getProvider();
        List<ContentValues> keywordContentValueList = TestUtils.createKeywordValues();

        for (ContentValues values : keywordContentValueList) {
            provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
        }

        List<ContentValues> mediaContentValueList = TestUtils.createMediaItemValues();

        for (ContentValues values : mediaContentValueList) {
            provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
        }

        Uri mediaTypeAndKeywordUri = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemTypeAndKeywordId(MediaItemType.NEWS, 1L);
        MediaItemType mediaItemType = ContentFinderContract.MediaItemEntry.getMediaItemTypeFromUri(mediaTypeAndKeywordUri);
        Long keywordId = ContentFinderContract.MediaItemEntry.getKeywordIdFromUri(mediaTypeAndKeywordUri);
        assertNotNull(mediaItemType);
        assertNotNull(keywordId);
        Cursor cursor = provider.query(mediaTypeAndKeywordUri, null, ContentFinderDataProvider.MEDIA_ITEM_TYPE_AND_KEYWORD_ID_SELECTION,
                new String[] {mediaItemType.getId().toString(), keywordId.toString()}, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        assertEquals("cursor size is different than expected", mediaContentValueList.size(), cursor.getCount());
    }

    private void clearTables() {
        ContentFinderDataProvider provider = getProvider();
        ContentFinderDbHelper dbHelper = provider.getDbHelper();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.execSQL("DELETE FROM " + ContentFinderContract.KeywordEntry.TABLE_NAME);
            database.execSQL("DELETE FROM " + ContentFinderContract.MediaItemEntry.TABLE_NAME);
        } catch (Exception ex) {
            // ignore it
        }
    }
}
