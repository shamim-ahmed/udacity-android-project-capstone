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
    public static final int KEYWORD_WITH_ID = 101;

    public static final int MEDIA_ITEM = 201;
    public static final int MEDIA_ITEM_WITH_ID = 202;
    public static final int MEDIA_ITEM_WITH_TYPE_ID = 203;
    public static final int MEDIA_ITEM_WITH_KEYWORD_ID = 204;
    public static final int MEDIA_ITEM_WITH_KEYWORD_ID_AND_TYPE_ID = 205;

    private static final String KEYWORD_ID_SELECTION = "_id = ?";
    private static final String MEDIA_ID_SELECTION = "_id = ?";
    private static final String MEDIA_ITEM_KEYWORD_ID_SELECTION = "keyword_id = ?";
    private static final String MEDIA_ITEM_TYPE_AND_KEYWORD_ID_SELECTION = "type = ? and keyword_id = ?";

    private static final SQLiteQueryBuilder sKeywordQueryBuilder = new SQLiteQueryBuilder();
    private static final SQLiteQueryBuilder sConentQueryBuilder = new SQLiteQueryBuilder();

    static {
        sKeywordQueryBuilder.setTables(ContentFinderContract.KeywordEntry.TABLE_NAME);
        sConentQueryBuilder.setTables(ContentFinderContract.MediaItemEntry.TABLE_NAME);
    }

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ContentFinderDbHelper dbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.KeywordEntry.PATH_KEYWORD, KEYWORD);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.KeywordEntry.PATH_KEYWORD + "/#", KEYWORD_WITH_ID);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM, MEDIA_ITEM);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/#", MEDIA_ITEM_WITH_ID);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/" + ContentFinderContract.MediaItemEntry.PATH_TYPE + "/#", MEDIA_ITEM_WITH_TYPE_ID);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/" + ContentFinderContract.KeywordEntry.PATH_KEYWORD + "/#", MEDIA_ITEM_WITH_KEYWORD_ID);
        matcher.addURI(ContentFinderContract.CONTENT_AUTHORITY, ContentFinderContract.MediaItemEntry.PATH_MEDIA_ITEM + "/" + ContentFinderContract.KeywordEntry.PATH_KEYWORD + "/#/" + ContentFinderContract.MediaItemEntry.PATH_TYPE + "/#", MEDIA_ITEM_WITH_KEYWORD_ID_AND_TYPE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ContentFinderDbHelper(getContext());
        return true;
    }

    public ContentFinderDbHelper getDbHelper() {
        return dbHelper;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        int matchValue = sUriMatcher.match(uri);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (matchValue) {
            case KEYWORD: {
                cursor = sKeywordQueryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case KEYWORD_WITH_ID: {
                Long keywordId = ContentFinderContract.KeywordEntry.getKeywordIdFromUri(uri);

                if (keywordId != null) {
                    cursor = sKeywordQueryBuilder.query(database, projection, KEYWORD_ID_SELECTION, new String[]{keywordId.toString()}, null, null, sortOrder);
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
                    cursor = sConentQueryBuilder.query(database, projection, MEDIA_ID_SELECTION, new String[]{contentId.toString()}, null, null, sortOrder);
                }

                break;
            }
            case MEDIA_ITEM_WITH_KEYWORD_ID: {
                Long keywordId = ContentFinderContract.MediaItemEntry.getKeywordIdFromUri(uri);

                if (keywordId != null) {
                    cursor = sConentQueryBuilder.query(database, projection, MEDIA_ITEM_KEYWORD_ID_SELECTION, new String[]{keywordId.toString()}, null, null, sortOrder);
                }

                break;
            }
            case MEDIA_ITEM_WITH_KEYWORD_ID_AND_TYPE_ID: {
                MediaItemType contentType = ContentFinderContract.MediaItemEntry.getMediaItemTypeFromUri(uri);
                Long keywordId = ContentFinderContract.MediaItemEntry.getKeywordIdFromUri(uri);

                if (contentType != null && keywordId != null) {
                    cursor = sConentQueryBuilder.query(database, projection, MEDIA_ITEM_TYPE_AND_KEYWORD_ID_SELECTION, new String[]{contentType.toString(), keywordId.toString()}, null, null, sortOrder);
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
            case KEYWORD_WITH_ID:
                result = ContentFinderContract.KeywordEntry.CONTENT_ITEM_TYPE;
                break;
            case MEDIA_ITEM:
                result = ContentFinderContract.MediaItemEntry.CONTENT_TYPE;
                break;
            case MEDIA_ITEM_WITH_ID:;
                result = ContentFinderContract.MediaItemEntry.CONTENT_ITEM_TYPE;
                break;
            case MEDIA_ITEM_WITH_KEYWORD_ID:
                result = ContentFinderContract.MediaItemEntry.CONTENT_TYPE;
                break;
            case MEDIA_ITEM_WITH_KEYWORD_ID_AND_TYPE_ID:
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
                    result = ContentFinderContract.KeywordEntry.buildUriFromKeywordId(_id);
                }

                break;
            }

            case MEDIA_ITEM: {
                long _id = database.insert(ContentFinderContract.MediaItemEntry.TABLE_NAME, null, values);

                if (_id != -1) {
                    result = ContentFinderContract.MediaItemEntry.buildUriFromMediaItemId(_id);
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
        Long id = ContentFinderContract.MediaItemEntry.getMediaItemIdFromUri(uri);

        if (id == null) {
            return -1;
        }

        switch (matchType) {
            case KEYWORD_WITH_ID: {
                tableName = ContentFinderContract.KeywordEntry.TABLE_NAME;
                break;
            }
            case MEDIA_ITEM_WITH_ID: {
                tableName = ContentFinderContract.MediaItemEntry.TABLE_NAME;
                break;
            }
            default: {
                Log.w(TAG, String.format("Invalid URI for deletion: %s", uri.toString()));
                break;
            }
        }

        int result = -1;

        if (StringUtils.isNotBlank(tableName)) {
            result = database.delete(tableName, MEDIA_ID_SELECTION, new String[]{id.toString()});
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.w(TAG, "the update method is not implemented");
        return -1;
    }
}
