package edu.udacity.android.contentfinder.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.List;

import edu.udacity.android.contentfinder.model.MediaItemType;

/**
 * Created by shamim on 5/6/16.
 */
public class ContentFinderContract {
    private static final String TAG = ContentFinderContract.class.getSimpleName();

    public static final String CONTENT_AUTHORITY = "edu.udacity.android.contentfinder";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class KeywordEntry implements BaseColumns {
        public static final String TABLE_NAME = "Keyword";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_CREATED_DATE = "created_date";

        public static final String PATH_KEYWORD = "keyword";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_KEYWORD).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_KEYWORD;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PATH_KEYWORD;

        public static Long getKeywordIdFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            Long resultId = null;

            try {
                resultId = Long.valueOf(segmentList.get(1));
            } catch (Exception ex) {
                Log.e(TAG, "error while parsing keyword id from URL");
            }

            return resultId;
        }

        public static Uri buildUriFromKeywordId(Long keywordId) {
            if (keywordId == null) {
                return null;
            }

            return CONTENT_URI.buildUpon().appendPath(keywordId.toString()).build();
        }
    }

    public static class MediaItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "MediaItem";
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_CONTENT_TYPE_ID = "media_type_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_PUBLISH_DATE = "publish_date";
        public static final String COLUMN_KEYWORD_ID = "keyword_id";

        public static final String PATH_MEDIA_ITEM = "media_item";
        public static final String PATH_TYPE = "type";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MEDIA_ITEM).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_MEDIA_ITEM;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PATH_MEDIA_ITEM;

        /**
         * @param uri the input URI of the form /media_item/123
         * @return the id of the media item
         */
        public static Long getMediaItemIdFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            Long mediaItemId = null;

            try {
                mediaItemId = Long.valueOf(segmentList.get(1));
            } catch (Exception ex) {
                Log.e(TAG, "error while parsing media item id");
            }

            return mediaItemId;
        }

        /**
         * @param uri the input URI of the form /media_item/type/photo.
         *            NOTE: additional path segments may be present, but they are ignored.
         * @return media item type
         */
        public static MediaItemType getMediaItemTypeFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 3) {
                return null;
            }

            return MediaItemType.fromId(Long.valueOf(segmentList.get(2)));
        }

        /**
         * @param uri the input URI of the form /media_item/type/photo/keyword/123
         * @return the keyword
         */
        public static Long getKeywordIdFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 3) {
                return null;
            }

            String mediaItemSegment = segmentList.get(0);
            String keywordSegment = segmentList.get(1);

            if (!mediaItemSegment.equals(MediaItemEntry.PATH_MEDIA_ITEM) || !keywordSegment.equals(KeywordEntry.PATH_KEYWORD)) {
                return null;
            }

            Long keywordId = null;

            try {
                keywordId = Long.valueOf(segmentList.get(2));
            } catch (Exception ex) {
                Log.e(TAG, "error while parsing keyword id");
            }

            return keywordId;
        }

        public static Uri buildUriFromMediaItemId(Long itemId) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MEDIA_ITEM)
                    .appendPath(itemId.toString())
                    .build();
        }

        public static Uri buildUriFromMediaItemType(MediaItemType itemType) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MEDIA_ITEM)
                    .appendPath(PATH_TYPE)
                    .appendPath(itemType.getId().toString())
                    .build();
        }

        public static Uri buildUriFromKeywordId(Long keywordId) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MEDIA_ITEM)
                    .appendPath(KeywordEntry.PATH_KEYWORD)
                    .appendPath(keywordId.toString())
                    .build();
        }

        public static Uri buildUriFromMediaItemTypeAndKeywordId(MediaItemType itemType, Long keywordId) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MEDIA_ITEM)
                    .appendPath(KeywordEntry.PATH_KEYWORD)
                    .appendPath(keywordId.toString())
                    .appendPath(PATH_TYPE)
                    .appendPath(itemType.getId().toString())
                    .build();
        }
    }
}
