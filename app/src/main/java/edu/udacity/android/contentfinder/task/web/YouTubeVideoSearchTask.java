package edu.udacity.android.contentfinder.task.web;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.MediaItemType;
import edu.udacity.android.contentfinder.ui.VideoListAdapter;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.util.StringUtils;

/**
 * Created by shamim on 5/6/16.
 */
public class YouTubeVideoSearchTask extends SearchTask {
    private static final String TAG = YouTubeVideoSearchTask.class.getSimpleName();

    private static final String JSON_FIELD_ITEMS = "items";
    private static final String JSON_FIELD_SNIPPET = "snippet";
    private static final String JSON_FIELD_TITLE = "title";
    private static final String JSON_FIELD_DESCRIPTION = "description";
    private static final String JSON_FIELD_PUBLISHED_AT = "publishedAt";
    private static final String JSON_FIELD_THUMBNAILS = "thumbnails";
    private static final String JSON_FIELD_ID = "id";
    private static final String JSON_FIELD_VIDEO_ID = "videoId";
    private static final String JSON_FIELD_MEDIUM = "medium";
    private static final String JSON_FIELD_URL = "url";

    private static final String DATE_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public YouTubeVideoSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    protected List<MediaItem> parseResponse(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return Collections.emptyList();
        }

        List<MediaItem> resultList = new ArrayList<>();

        try {
            JSONObject resultObject = new JSONObject(jsonStr);
            JSONArray videoItems = resultObject.getJSONArray(JSON_FIELD_ITEMS);

            for (int i = 0, n = videoItems.length(); i < n; i++) {
                JSONObject videoObject = videoItems.getJSONObject(i);
                JSONObject idObject = videoObject.getJSONObject(JSON_FIELD_ID);
                String videoId = idObject.getString(JSON_FIELD_VIDEO_ID);

                JSONObject snippetObject = videoObject.getJSONObject(JSON_FIELD_SNIPPET);
                String title = snippetObject.getString(JSON_FIELD_TITLE);
                String description = snippetObject.getString(JSON_FIELD_DESCRIPTION);
                String dateStr = snippetObject.getString(JSON_FIELD_PUBLISHED_AT);
                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR, Locale.US);
                Date publishDate = null;

                try {
                    publishDate = dateFormat.parse(dateStr);
                } catch (ParseException ex) {
                    Log.e(TAG, String.format("Error while parsing date string : %s", dateStr), ex);
                }

                JSONObject thumbNailObject = snippetObject.getJSONObject(JSON_FIELD_THUMBNAILS);
                JSONObject mediumObject = thumbNailObject.getJSONObject(JSON_FIELD_MEDIUM);
                String thumbnailUri = mediumObject.getString(JSON_FIELD_URL);

                MediaItem result = new MediaItem();
                result.setItemId(videoId);
                result.setTitle(title);
                result.setSummary(description);
                result.setPublishDate(publishDate);
                result.setWebUrl(thumbnailUri);
                result.setContentType(MediaItemType.VIDEO);

                resultList.add(result);
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error while parsing youtube search result", ex);
        }

        return resultList;
    }

    @Override
    public void onPostExecute(List<MediaItem> resultList) {
        ListView videoListView = (ListView) activity.findViewById(R.id.video_list);
        VideoListAdapter adapter = new VideoListAdapter(activity);
        adapter.addAll(resultList);

        videoListView.setAdapter(adapter);
    }
}
