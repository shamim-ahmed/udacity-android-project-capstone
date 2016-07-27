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

        for (ContentValues values : contentValueList) {
            Uri uri = provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
            assertNotNull("uri is null", uri);
            assertEquals("uri type is different than expected", ContentFinderDataProvider.MEDIA_ITEM_WITH_ID, sUriMatcher.match(uri));
        }
    }

    public void testAllMediaSearch() {
        List<ContentValues> contentValuesList = TestUtils.createMediaItemValues();
        ContentFinderDataProvider provider = getProvider();

        for (ContentValues values : contentValuesList) {
            provider.insert(ContentFinderContract.MediaItemEntry.CONTENT_URI, values);
        }

        final int n = contentValuesList.size();
        Cursor cursor = provider.query(ContentFinderContract.MediaItemEntry.CONTENT_URI, null, null, null, null);
        assertNotNull(cursor);
        assertTrue("cursor is null", cursor.moveToFirst());
        assertEquals("cursor size different than expected", n, cursor.getCount());
        cursor.close();
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
