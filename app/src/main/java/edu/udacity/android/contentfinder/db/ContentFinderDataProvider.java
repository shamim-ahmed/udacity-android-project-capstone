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

import edu.udacity.android.contentfinder.model.MediaItemType;
import edu.udacity.android.contentfinder.util.StringUtils;

/**
 * Created by shamim on 5/9/16.
 */
public class ContentFinderDataProvider extends ContentProvider {
    private static final String TAG = ContentFinderDataProvider.class.getSimpleName();

    public static final int KEYWORD = 100;
    public static final int KEYWORD_WITH_NAME = 101;

    private static final int MEDIA_ITEM = 201;
    private static final int MEDIA_ITEM_WITH_ID = 202;
    private static final int MEDIA_ITEM_WITH_KEYWORD = 203;
    private static final int MEDIA_ITEM_WITH_TYPE_AND_KEYWORD = 204;

    private static final String KEYWORD_SELECTION = "word = ?";
    private static final String MEDIA_ITEM_ID_SELECTION = "item_id = ?";
    private static final String MEDIA_ITEM_KEYWORD_SELECTION = "word = ?";
    private static final String MEDIA_ITEM_TYPE_AND_KEYWORD_SELECTION = "type = ? and keyword = ?";

    private static final SQLiteQueryBuilder sTagQueryBuilder = new SQLiteQueryBuilder();
    private static final SQLiteQueryBuilder sConentQueryBuilder = new SQLiteQueryBuilder();

    static {
        sTagQueryBuilder.setTables(ContentFinderContract.KeywordEntry.TABLE_NAME);
        sConentQueryBuilder.setTables(ContentFinderContract.MediaItemEntry.TABLE_NAME);
    }

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ContentFinderDbHelper dbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.KeywordEntry.PATH_KEYWORD, KEYWORD);
        // TODO figure out if this is correct
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.KeywordEntry.PATH_KEYWORD + "/*", KEYWORD_WITH_NAME);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM, MEDIA_ITEM);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/*", MEDIA_ITEM_WITH_ID);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/" + ContentFinderContract.KeywordEntry.PATH_KEYWORD + "/*", MEDIA_ITEM_WITH_KEYWORD);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/" + ContentFinderContract.MediaItemEntry.PATH_TYPE + "/*/" + ContentFinderContract.KeywordEntry.PATH_KEYWORD + "/*", MEDIA_ITEM_WITH_TYPE_AND_KEYWORD);

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
            case KEYWORD: {
                cursor = sTagQueryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case KEYWORD_WITH_NAME: {
                String tagName = ContentFinderContract.KeywordEntry.getKeywordFromUri(uri);

                if (StringUtils.isNotBlank(tagName)) {
                    cursor = sTagQueryBuilder.query(database, projection, KEYWORD_SELECTION, new String[]{tagName}, null, null, sortOrder);
                }

                break;
            }
            case MEDIA_ITEM: {
                cursor = sConentQueryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case MEDIA_ITEM_WITH_ID: {
                Long contentId = ContentFinderContract.MediaItemEntry.getMediaItemIdFromUri(uri);

                if (contentId != null) {
                    cursor = sTagQueryBuilder.query(database, projection, MEDIA_ITEM_ID_SELECTION, new String[]{contentId.toString()}, null, null, sortOrder);
                }

                break;
            }
            case MEDIA_ITEM_WITH_KEYWORD: {
                String tagName = ContentFinderContract.MediaItemEntry.getKeywordFromUri(uri);

                if (StringUtils.isNotBlank(tagName)) {
                    cursor = sTagQueryBuilder.query(database, projection, MEDIA_ITEM_KEYWORD_SELECTION, new String[]{tagName}, null, null, sortOrder);
                }

                break;
            }
            case MEDIA_ITEM_WITH_TYPE_AND_KEYWORD: {
                MediaItemType contentType = ContentFinderContract.MediaItemEntry.getMediaItemTypeFromUri(uri);
                String tagName = ContentFinderContract.MediaItemEntry.getKeywordFromUri(uri);

                if (contentType != null && StringUtils.isNotBlank(tagName)) {
                    cursor = sTagQueryBuilder.query(database, projection, MEDIA_ITEM_TYPE_AND_KEYWORD_SELECTION, new String[]{contentType.toString(), tagName}, null, null, sortOrder);
                }

                break;
            }
            default: {
                Log.w(TAG, String.format("No match found for uri : %s", uri));
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
            case KEYWORD:
                result = ContentFinderContract.KeywordEntry.CONTENT_TYPE;
                break;
            case KEYWORD_WITH_NAME:
                result = ContentFinderContract.KeywordEntry.CONTENT_ITEM_TYPE;
                break;
            case MEDIA_ITEM:
                result = ContentFinderContract.MediaItemEntry.CONTENT_TYPE;
                break;
            case MEDIA_ITEM_WITH_ID:;
                result = ContentFinderContract.MediaItemEntry.CONTENT_ITEM_TYPE;
                break;
            case MEDIA_ITEM_WITH_KEYWORD:
                result = ContentFinderContract.MediaItemEntry.CONTENT_TYPE;
                break;
            case MEDIA_ITEM_WITH_TYPE_AND_KEYWORD:
                result = ContentFinderContract.MediaItemEntry.CONTENT_TYPE;
                break;
        }

        return result;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Uri result = null;
        int matchValue = sUriMatcher.match(uri);

        switch (matchValue) {
            case KEYWORD: {
                long _id = database.insert(ContentFinderContract.KeywordEntry.TABLE_NAME, null, values);

                if (_id != -1) {
                    String word = values.getAsString(ContentFinderContract.KeywordEntry.COLUMN_WORD);
                    result = ContentFinderContract.KeywordEntry.buildUriFromKeyword(word);
                }

                break;
            }

            case MEDIA_ITEM: {
                long _id = database.insert(ContentFinderContract.MediaItemEntry.TABLE_NAME, null, values);

                if (_id != -1) {
                    String mediaItemId = values.getAsString(ContentFinderContract.MediaItemEntry.COLUMN_ITEM_ID);
                    result = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemId(mediaItemId);
                }

                break;
            }

            default: {
                Log.w(TAG, String.format("invalid uri for insert : %s", uri.toString()));
                break;
            }
        }

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int matchType = sUriMatcher.match(uri);
        String tableName = null;
        int result = 0;

        switch (matchType) {
            case KEYWORD: {
                tableName = ContentFinderContract.KeywordEntry.TABLE_NAME;
                break;
            }
            case MEDIA_ITEM: {
                tableName = ContentFinderContract.MediaItemEntry.TABLE_NAME;
                break;
            }
            default: {
                Log.w(TAG, String.format("Invalid URI for deletion: %s", uri.toString()));
                break;
            }
        }

        if (StringUtils.isNotBlank(tableName)) {
            result = database.delete(tableName, selection, selectionArgs);
            // TODO delete the entries in the join table
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.w(TAG, "the update method is not implemented");
        return -1;
    }
}
