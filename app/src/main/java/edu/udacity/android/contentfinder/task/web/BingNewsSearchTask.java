package edu.udacity.android.contentfinder.task.web;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.ui.NewsListAdapter;
import edu.udacity.android.contentfinder.model.MediaItemType;
import edu.udacity.android.contentfinder.model.MediaItem;
import edu.udacity.android.contentfinder.util.AppUtils;
import edu.udacity.android.contentfinder.util.DateUtils;
import edu.udacity.android.contentfinder.util.StringUtils;

public class BingNewsSearchTask extends BingSearchTask {
    private static final String TAG = BingNewsSearchTask.class.getSimpleName();

    private static final String JSON_ROOT_FIELD = "d";
    private static final String JSON_FIELD_RESULTS = "results";
    private static final String JSON_FIELD_TITLE = "Title";
    private static final String JSON_FIELD_DESCRIPTION = "Description";
    private static final String JSON_FIELD_SOURCE = "Source";
    private static final String JSON_FIELD_URL = "Url";
    private static final String JSON_FIELD_DATE = "Date";

    public BingNewsSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    protected List<MediaItem> parseResponse(String jsonStr) {
        List<MediaItem> resultList = new ArrayList<>();

        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                JSONObject searchResult = new JSONObject(jsonStr);
                JSONObject resultRoot = searchResult.getJSONObject(JSON_ROOT_FIELD);

                if (resultRoot == null) {
                    return resultList;
                }

                JSONArray jsonArray = resultRoot.getJSONArray(JSON_FIELD_RESULTS);

                if (jsonArray == null || jsonArray.length() == 0) {
                    return resultList;
                }

                final int n = jsonArray.length();

                for (int i = 0; i < n; i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String title = item.getString(JSON_FIELD_TITLE);
                    String description = item.getString(JSON_FIELD_DESCRIPTION);
                    String webUrl = item.getString(JSON_FIELD_URL);
                    Date publishDate = parseDate(item.getString(JSON_FIELD_DATE));

                    String source = item.getString(JSON_FIELD_SOURCE);
                    if (StringUtils.isBlank(source)) {
                        source = AppUtils.getSource(webUrl);
                    }

                    MediaItem result = new MediaItem();
                    result.setItemId(webUrl);
                    result.setContentType(MediaItemType.NEWS);
                    result.setTitle(title);
                    result.setDescription(description);
                    result.setWebUrl(webUrl);
                    result.setSource(source);
                    result.setPublishDate(publishDate);
                    resultList.add(result);
                }

            } catch (JSONException ex) {
                Log.e(TAG, String.format("Error while parsing json : %s", jsonStr), ex);
            }
        }

        return resultList;
    }

    @Override
    protected void onPostExecute(List<MediaItem> resultList) {
        ListView listView = (ListView) activity.findViewById(R.id.mediaItem_list);
        ArrayAdapter<MediaItem> adapter = new NewsListAdapter(activity);
        listView.setAdapter(adapter);

        adapter.addAll(resultList);
    }

    private Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        Date resultDate = null;
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.BING_API_DATE_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            resultDate = dateFormat.parse(dateStr);
        } catch (Exception ex) {
            Log.e(TAG, "error while parsing date", ex);
        }

        return resultDate;
    }
}
