package edu.udacity.android.contentfinder.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import edu.udacity.android.contentfinder.model.ContentType;
import edu.udacity.android.contentfinder.util.StringUtils;

/**
 * Created by shamim on 5/9/16.
 */
public class ContentFinderDataProvider extends ContentProvider {
    private static final String LOG_ID = ContentFinderDataProvider.class.getSimpleName();

    public static final int TAG = 100;
    public static final int TAG_WITH_NAME = 101;
    private static final int CONTENT = 201;
    private static final int CONTENT_WITH_ID = 202;
    private static final int CONTENT_WITH_TAG = 203;
    private static final int CONTENT_WITH_TYPE_AND_TAG = 204;

    private static final String TAG_NAME_SELECTION = "tag_name = ?";
    private static final String CONTENT_ID_SELECTION = "content_id = ?";
    private static final String CONTENT_TAG_SELECTION = "tag = ?";
    private static final String CONTENT_TYPE_AND_TAG_SELECTION = "type = ? and tag = ";

    private static final SQLiteQueryBuilder sTagQueryBuilder = new SQLiteQueryBuilder();
    private static final SQLiteQueryBuilder sConentQueryBuilder = new SQLiteQueryBuilder();

    static {
        sTagQueryBuilder.setTables(ContentFinderContract.TagEntry.TABLE_NAME);
        sConentQueryBuilder.setTables(ContentFinderContract.ContentEntry.TABLE_NAME);
    }

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ContentFinderDbHelper dbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.TagEntry.PATH_TAG, TAG);
        // TODO figure out if this is correct
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.TagEntry.PATH_TAG + "/*", TAG_WITH_NAME);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.ContentEntry.PATH_CONTENT, CONTENT);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.ContentEntry.PATH_CONTENT + "/*", CONTENT_WITH_ID);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.ContentEntry.PATH_CONTENT + "/" + ContentFinderContract.TagEntry.PATH_TAG + "/*", CONTENT_WITH_TAG);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.ContentEntry.PATH_CONTENT + "/" + ContentFinderContract.ContentEntry.PATH_TYPE + "/*/" + ContentFinderContract.TagEntry.PATH_TAG + "/*", CONTENT_WITH_TYPE_AND_TAG);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ContentFinderDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        int matchValue = sUriMatcher.match(uri);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (matchValue) {
            case TAG: {
                cursor = sTagQueryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case TAG_WITH_NAME: {
                String tagName = ContentFinderContract.TagEntry.getTagNameFromUri(uri);

                if (StringUtils.isNotBlank(tagName)) {
                    cursor = sTagQueryBuilder.query(database, projection, TAG_NAME_SELECTION, new String[]{tagName}, null, null, sortOrder);
                }

                break;
            }
            case CONTENT: {
                cursor = sConentQueryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case CONTENT_WITH_ID: {
                Long contentId = ContentFinderContract.ContentEntry.getContentIdFromUri(uri);

                if (contentId != null) {
                    cursor = sTagQueryBuilder.query(database, projection, CONTENT_ID_SELECTION, new String[]{contentId.toString()}, null, null, sortOrder);
                }

                break;
            }
            case CONTENT_WITH_TAG: {
                String tagName = ContentFinderContract.ContentEntry.getTagNamFromUri(uri);

                if (StringUtils.isNotBlank(tagName)) {
                    cursor = sTagQueryBuilder.query(database, projection, CONTENT_TAG_SELECTION, new String[]{tagName}, null, null, sortOrder);
                }

                break;
            }
            case CONTENT_WITH_TYPE_AND_TAG:
                ContentType contentType = ContentFinderContract.ContentEntry.getContentTypeFromUri(uri);
                String tagName = ContentFinderContract.ContentEntry.getTagNamFromUri(uri);

                if (contentType != null && StringUtils.isNotBlank(tagName)) {
                    cursor = sTagQueryBuilder.query(database, projection, CONTENT_TYPE_AND_TAG_SELECTION, new String[]{contentType.toString(), tagName}, null, null, sortOrder);
                }

                break;
            default: {
                Log.w(LOG_ID, String.format("No match found for uri : %s", uri));
                break;
            }
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String result = null;
        int matchValue = sUriMatcher.match(uri);

        switch (matchValue) {
            case TAG:
                result = ContentFinderContract.TagEntry.CONTENT_TYPE;
                break;
            case TAG_WITH_NAME:
                result = ContentFinderContract.TagEntry.CONTENT_ITEM_TYPE;
                break;
            case CONTENT:
                result = ContentFinderContract.ContentEntry.CONTENT_TYPE;
                break;
            case CONTENT_WITH_ID:
                ;
                result = ContentFinderContract.ContentEntry.CONTENT_ITEM_TYPE;
                break;
            case CONTENT_WITH_TAG:
                result = ContentFinderContract.ContentEntry.CONTENT_TYPE;
                break;
            case CONTENT_WITH_TYPE_AND_TAG:
                result = ContentFinderContract.ContentEntry.CONTENT_TYPE;
                break;
        }

        return result;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
