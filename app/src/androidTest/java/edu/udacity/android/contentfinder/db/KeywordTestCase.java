package edu.udacity.android.contentfinder.db;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamim on 6/2/16.
 */
public class KeywordTestCase extends ProviderTestCase2<ContentFinderDataProvider> {

    private static final UriMatcher sUriMatcher = ContentFinderDataProvider.buildUriMatcher();

    public KeywordTestCase() {
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
        ;
        clearTables();
    }

    public void testKeywordInsertAndDelete() {
        List<ContentValues> keywordDataList = TestUtils.createKeywordValues();
        assertNotNull("list is null", keywordDataList);
        assertTrue("list is empty", keywordDataList.size() > 0);

        List<Uri> resultUriList = new ArrayList<>();
        ContentFinderDataProvider provider = getProvider();

        for (ContentValues values : keywordDataList) {
            Uri uri = provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
            resultUriList.add(uri);
            assertNotNull("URI is null", uri);
            assertEquals("uri type is different than expected", ContentFinderDataProvider.KEYWORD_WITH_ID, sUriMatcher.match(uri));
            Long id = ContentFinderContract.KeywordEntry.getKeywordIdFromUri(uri);
            assertNotNull("id is null", id);

            // form the uri from the id and compare it with the original URI
            Uri constructedUri = ContentFinderContract.KeywordEntry.buildUriFromKeywordId(id);
            assertEquals("constructed uri is different than expected", uri.toString(), constructedUri.toString());
        }

        // delete all keywords
        for (Uri uri : resultUriList) {
            int n = provider.delete(uri, null, null);
            assertTrue("deletion was not successful", n == 1);
        }
    }

    public void testAllKeywordSearch() {
        List<ContentValues> keywordDataList = TestUtils.createKeywordValues();
        final int resultSetSize = keywordDataList.size();
        assertNotNull("list is null", keywordDataList);
        assertTrue("list size is different than expected", keywordDataList.size() == resultSetSize);

        ContentFinderDataProvider provider = getProvider();

        for (ContentValues values : keywordDataList) {
            provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
        }

        // search for all keywords
        Cursor cursor = provider.query(ContentFinderContract.KeywordEntry.CONTENT_URI, null, null, null, "_id");
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", Boolean.TRUE.equals(cursor.moveToFirst()));
        assertEquals("size of cursor is different than expected", resultSetSize, cursor.getCount());
        cursor.close();
    }

    public void testIndividualKeywordSearch() {
        List<ContentValues> keywordDataList = TestUtils.createKeywordValues();
        final int resultSetSize = keywordDataList.size();
        assertNotNull("list is null", keywordDataList);
        assertTrue("list size is different than expected", keywordDataList.size() == resultSetSize);

        ContentFinderDataProvider provider = getProvider();
        List<Uri> resultUriList = new ArrayList<>();

        for (ContentValues values : keywordDataList) {
            Uri uri = provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
            resultUriList.add(uri);
        }

        for (Uri uri : resultUriList) {
            Cursor cursor = provider.query(uri, null, null, null, null);
            assertNotNull("cursor is null", cursor);
            assertTrue("cursor is empty", cursor.moveToFirst());
            assertEquals("cursor size is different than expected", 1, cursor.getCount());
            cursor.close();
        }
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
