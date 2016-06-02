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

        public static final String PATH_KEYWORD = "keyword";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_KEYWORD).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_KEYWORD;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PATH_KEYWORD;

        public static String getKeywordFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            return segmentList.get(1);
        }

        public static Uri buildUriFromKeyword(String keyword) {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_KEYWORD).appendPath(keyword).build();
        }
    }

    public static class MediaItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "MediaItem";
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_CONTENT_TYPE = "media_type";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_PHOTO_URL = "photo_url";

        public static final String PATH_MEDIA_ITEM = "mediaItem";
        public static final String PATH_TYPE = "type";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MEDIA_ITEM).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_MEDIA_ITEM;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PATH_MEDIA_ITEM;

        /**
         * @param uri the input URI of the form /mediaItem/123
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
         * @param uri the input URI of the form /mediaItem/photo or /mediaItem/photo/keyword/cat
         * @return media item type
         */
        public static MediaItemType getMediaItemTypeFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            return MediaItemType.valueOf(segmentList.get(1));
        }

        /**
         * @param uri the input URI of the form /mediaItem/photo/keyword/cat
         * @return the keyword
         */
        public static String getKeywordFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 4) {
                return null;
            }

            return segmentList.get(3);
        }

        public static Uri buildUriFromMediaItemId(String mediaItemId) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MEDIA_ITEM)
                    .appendPath(mediaItemId)
                    .build();
        }

        public static Uri buildUriFromMediaItemTypeAndKeyword(MediaItemType mediaItemType, String keyword) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(mediaItemType.toString())
                    .appendPath(KeywordEntry.PATH_KEYWORD)
                    .appendPath(keyword)
                    .build();
        }
    }

    public static class MediaItem_Keyword_Entry implements BaseColumns {
        public static final String TABLE_NAME = "MediaItem_Keyword";
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_KEYWORD = "keyword";
    }
}
