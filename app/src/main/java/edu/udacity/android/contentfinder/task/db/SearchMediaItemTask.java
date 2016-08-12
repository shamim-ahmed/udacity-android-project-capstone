package edu.udacity.android.contentfinder.task.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.udacity.android.contentfinder.SavedMediaItemSearchActivity;
import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.model.MediaItemType;
import edu.udacity.android.contentfinder.util.DateUtils;

/**
 * Created by shamim on 8/7/16.
 */
public class SearchMediaItemTask extends AsyncTask<Void, Void, List<MediaItem>> {
    private final Activity activity;
    private final Keyword keyword;

    public SearchMediaItemTask(Activity activity, Keyword keyword) {
        this.activity = activity;
        this.keyword = keyword;
    }

    @Override
    protected List<MediaItem> doInBackground(Void... params) {
        ContentResolver contentResolver = activity.getContentResolver();
        Uri searchUri = ContentFinderContract.MediaItemEntry.buildUriFromKeywordId(keyword.getId());
        Cursor cursor = contentResolver.query(searchUri, null, null, null, null);

        if (cursor == null) {
            return Collections.emptyList();
        }

        List<MediaItem> resultList = new ArrayList<>();

        while (cursor.moveToNext()) {
            MediaItem item = readMediaItem(cursor);
            resultList.add(item);
        }

        return resultList;
    }

    @Override
    public void onPostExecute(List<MediaItem> resultList) {
        ((SavedMediaItemSearchActivity) activity).loadMediaItems(resultList);
    }

    private MediaItem readMediaItem(Cursor cursor) {
        int itemIdIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_ITEM_ID);
        int mediaTypeIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_CONTENT_TYPE_ID);
        int titleIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_TITLE);
        int summaryIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_DESCRIPTION);
        int urlIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_URL);
        int thumbnailIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_THUMBNAIL_URL);
        int sourceIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_SOURCE);
        int publishDateIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_PUBLISH_DATE);
        int keywordIdx = cursor.getColumnIndex(ContentFinderContract.MediaItemEntry.COLUMN_KEYWORD_ID);

        MediaItem mediaItem = new MediaItem();
        mediaItem.setItemId(cursor.getString(itemIdIdx));
        mediaItem.setContentType(MediaItemType.fromId(cursor.getLong(mediaTypeIdx)));
        mediaItem.setTitle(cursor.getString(titleIdx));
        mediaItem.setDescription(cursor.getString(summaryIdx));
        mediaItem.setWebUrl(cursor.getString(urlIdx));
        mediaItem.setThumbnailUrl(cursor.getString(thumbnailIdx));
        mediaItem.setSource(cursor.getString(sourceIdx));
        mediaItem.setPublishDate(DateUtils.parseDate(cursor.getString(publishDateIdx)));
        mediaItem.setKeywordId(cursor.getLong(keywordIdx));

        return mediaItem;
    }
}
