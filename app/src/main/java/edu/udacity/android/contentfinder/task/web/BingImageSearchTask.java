package edu.udacity.android.contentfinder.task.web;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.MediaItemType;
import edu.udacity.android.contentfinder.ui.ImageListAdapter;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.util.AppUtils;

public class BingImageSearchTask extends BingSearchTask {
    private static final String TAG = BingSearchTask.class.getSimpleName();

    private static final String SEARCH_RESULT_ROOT = "d";
    private static final String JSON_FIELD_RESULTS = "results";
    private static final String JSON_FIELD_TITLE = "Title";
    private static final String JSON_FIELD_THUMBNAIL = "Thumbnail";
    private static final String JSON_FIELD_MEDIA_URL = "MediaUrl";

    public BingImageSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    protected List<MediaItem> parseResponse(String jsonStr) {
        List<MediaItem> resultList = new ArrayList<>();

        try {
            JSONObject resultObject = new JSONObject(jsonStr);
            JSONObject rootObject = resultObject.getJSONObject(SEARCH_RESULT_ROOT);
            JSONArray resultArray = rootObject.getJSONArray(JSON_FIELD_RESULTS);

            for (int i = 0, n = resultArray.length(); i < n; i++) {
                JSONObject photoObject = resultArray.getJSONObject(i);
                String title = photoObject.getString(JSON_FIELD_TITLE);
                String webUrl = photoObject.getString(JSON_FIELD_MEDIA_URL);
                JSONObject thumbnailObject = photoObject.getJSONObject(JSON_FIELD_THUMBNAIL);
                String thumbnailUrl = thumbnailObject.getString(JSON_FIELD_MEDIA_URL);
                String source = AppUtils.getSource(webUrl);

                MediaItem result = new MediaItem();
                result.setItemId(webUrl);
                result.setContentType(MediaItemType.PHOTO);
                result.setTitle(title);
                result.setDescription(webUrl);
                result.setWebUrl(webUrl);
                result.setThumbnailUrl(thumbnailUrl);
                result.setSource(source);
                resultList.add(result);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error while parsing json", ex);
        }

        return resultList;
    }

    @Override
    protected void onPostExecute(List<MediaItem> resultList) {
        ListView imageListView = (ListView) activity.findViewById(R.id.image_list);
        ArrayAdapter<MediaItem> adapter = new ImageListAdapter(activity);
        imageListView.setAdapter(adapter);

        adapter.addAll(resultList);
    }
}
