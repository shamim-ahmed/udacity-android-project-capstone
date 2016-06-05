package edu.udacity.android.contentfinder.task;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import edu.udacity.android.contentfinder.R;
import edu.udacity.android.contentfinder.model.MediaItemType;
import edu.udacity.android.contentfinder.util.SearchResult;
import edu.udacity.android.contentfinder.util.StringUtils;

/**
 * Created by shamim on 5/3/16.
 */
public class GuardianSearchTask extends SearchTask {
    private static final String TAG = GuardianSearchTask.class.getSimpleName();

    private static final String DATE_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String JSON_FIELD_RESULTS = "results";
    private static final String JSON_FIELD_TITLE = "webTitle";
    private static final String JSON_FIELD_WEB_URL = "webUrl";
    private static final String JSON_FIELD_PUBLICATION_DATE = "webPublicationDate";

    public GuardianSearchTask(Activity activity) {
        super(activity);
    }

    @Override
    protected void onPostExecute(String jsonStr) {
        super.onPostExecute(jsonStr);
        SearchResult[] searchResults = parseResponse(jsonStr);

        if (searchResults != null) {
            Log.i(TAG, "the search result : " + Arrays.deepToString(searchResults));
        }
    }

    private SearchResult[] parseResponse(String jsonStr) {
        SearchResult[] searchResults = null;

        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray(JSON_FIELD_RESULTS);

                if (jsonArray == null || jsonArray.length() == 0) {
                    return null;
                }

                final int n = jsonArray.length();
                searchResults = new SearchResult[n];

                for (int i = 0; i < n; i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String title = item.getString(JSON_FIELD_TITLE);
                    String webUrl = item.getString(JSON_FIELD_WEB_URL);
                    Date publishDate = parseDate(item.getString(JSON_FIELD_PUBLICATION_DATE));

                    SearchResult result = new SearchResult();
                    result.setTitle(title);
                    result.setWebUrl(webUrl);
                    result.setPublishDate(publishDate);
                    result.setItemType(MediaItemType.NEWS);
                    result.setSource(activity.getString(R.string.item_source_guardian));
                    searchResults[i] = result;
                }


            } catch (JSONException ex) {
                Log.e(TAG, String.format("Error while parsing json : %s", jsonStr));
            }
        }

        return searchResults;
    }

    public Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        Date resultDate = null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR, Locale.US);

        try {
            resultDate = dateFormat.parse(dateStr);
        } catch (Exception ex) {
            Log.e(TAG, "error while parsing date", ex);
        }

        return resultDate;
    }
}
