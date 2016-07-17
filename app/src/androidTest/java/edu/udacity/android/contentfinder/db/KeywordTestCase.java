package edu.udacity.android.contentfinder.db;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;

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

        ContentFinderDataProvider provider = getProvider();

        for (ContentValues values : keywordDataList) {
            Uri uri = provider.insert(ContentFinderContract.KeywordEntry.CONTENT_URI, values);
            assertNotNull("URI is null", uri);
            assertEquals("uri type is different than expected", ContentFinderDataProvider.KEYWORD_WITH_ID, sUriMatcher.match(uri));
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
