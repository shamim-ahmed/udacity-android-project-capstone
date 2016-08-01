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

/**
 * Created by shamim on 6/18/16.
 */
public class BingImageSearchTask extends BingSearchTask {
    private static final String TAG = BingSearchTask.class.getSimpleName();

    private static final String SEARCH_RESULT_ROOT = "d";
    private static final String JSON_FIELD_RESULTS = "results";
    private static final String JSON_FIELD_ID = "ID";
    private static final String JSON_FIELD_TITLE = "Title";
    private static final String JSON_FIELD_THUMBNAIL = "Thumbnail";
    private static final String JSON_FIELD_MEDIAURL = "MediaUrl";

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
                String id = photoObject.getString(JSON_FIELD_ID);
                String title = photoObject.getString(JSON_FIELD_TITLE);
                JSONObject thumbnailObject = photoObject.getJSONObject(JSON_FIELD_THUMBNAIL);
                String mediaUrl = thumbnailObject.getString(JSON_FIELD_MEDIAURL);

                MediaItem result = new MediaItem();
                result.setItemId(id);
                result.setTitle(title);
                result.setContentType(MediaItemType.PHOTO);
                result.setWebUrl(mediaUrl);
                // TODO  find photo source by parsing web URL
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
