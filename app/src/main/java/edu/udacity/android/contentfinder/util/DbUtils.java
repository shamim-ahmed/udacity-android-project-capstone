package edu.udacity.android.contentfinder.util;

import android.content.ContentValues;
import android.database.Cursor;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;

public class DbUtils {
    public static ContentValues convertKeyword(Keyword keyword) {
        ContentValues values = new ContentValues();
        values.put(ContentFinderContract.KeywordEntry.COLUMN_WORD, keyword.getWord());
        values.put(ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE, DateUtils.formatDate(keyword.getCreatedDate()));
        return values;
    }

    public static ContentValues convertMediaItem(MediaItem mediaItem) {
        ContentValues values = new ContentValues();
        values.put(ContentFinderContract.MediaItemEntry._ID, mediaItem.getId());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_ITEM_ID, mediaItem.getItemId());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_CONTENT_TYPE_ID, mediaItem.getContentType().getId());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_TITLE, mediaItem.getTitle());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_DESCRIPTION, mediaItem.getDescription());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_URL, mediaItem.getWebUrl());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_THUMBNAIL_URL, mediaItem.getThumbnailUrl());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_SOURCE, mediaItem.getSource());
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_PUBLISH_DATE, DateUtils.formatDate(mediaItem.getPublishDate()));
        values.put(ContentFinderContract.MediaItemEntry.COLUMN_KEYWORD_ID, mediaItem.getKeywordId());

        return values;
    }

    public static void close(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception ex) {
                // ignore it
            }
        }
    }

    private DbUtils() {
    }
}
