package edu.udacity.android.contentfinder.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.List;

import edu.udacity.android.contentfinder.model.ContentType;

/**
 * Created by shamim on 5/6/16.
 */
public class ContentFinderContract {
    private static final String TAG = ContentFinderContract.class.getSimpleName();

    public static final String CONTENT_AUTHORITY = "edu.udacity.android.contentfinder";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class TagEntry implements BaseColumns {
        public static final String TABLE_NAME = "Tag";
        public static final String COLUMN_TAG_NAME = "name";

        public static final String PATH_TAG = "tag";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAG).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_TAG;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PATH_TAG;

        public static String getTagNameFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            return segmentList.get(1);
        }

        public static Uri buildUriFromTagName(String tagName) {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAG).appendPath(tagName).build();
        }
    }

    public static class ContentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Content";
        public static final String COLUMN_CONTENT_ID = "content_id";
        public static final String COLUMN_CONTENT_TYPE = "type";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_PHOTO_URL = "photo_url";

        public static final String PATH_CONTENT = "content";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_CONTENT).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_CONTENT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PATH_CONTENT;

        /**
         * @param uri the input URI of the form /content/123
         * @return the id of the content
         */
        public static Long getContentIdFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            Long contentId = null;

            try {
                contentId = Long.valueOf(segmentList.get(1));
            } catch (Exception ex) {
                Log.e(TAG, "error while parsing content id");
            }

            return contentId;
        }

        /**
         * @param uri the input URI of the form /content/photo or /content/photo/tag/cat
         * @return the content type
         */
        public static ContentType getContentTypeFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 2) {
                return null;
            }

            return ContentType.valueOf(segmentList.get(1));
        }

        /**
         * @param uri the input URI of the form /content/photo/tag/cat
         * @return the tag name
         */
        public static String getTagNamFromUri(Uri uri) {
            List<String> segmentList = uri.getPathSegments();

            if (segmentList.size() < 4) {
                return null;
            }

            return segmentList.get(3);
        }

        public static Uri buildUriFromContentId(Long contentId) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_CONTENT)
                    .appendPath(contentId.toString())
                    .build();
        }

        public static Uri buildUriFromContentTypeAndTagName(ContentType contentType, String tagName) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(contentType.toString())
                    .appendPath(TagEntry.PATH_TAG)
                    .appendPath(tagName)
                    .build();
        }
    }
}
