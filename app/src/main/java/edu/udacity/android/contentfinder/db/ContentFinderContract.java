package edu.udacity.android.contentfinder.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by shamim on 5/6/16.
 */
public class ContentFinderContract {
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
    }

    public static class ContentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Content";
        public static final String COLUMN_CONTENT_ID = "content_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_PHOTO_URL = "photo_url";

        public static final String PATH_CONTENT = "content";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_CONTENT).build();
                '


    }


}
