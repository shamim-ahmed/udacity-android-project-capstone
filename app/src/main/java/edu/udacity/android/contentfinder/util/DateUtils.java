package edu.udacity.android.contentfinder.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shamim on 7/31/16.
 */
public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
    public static final String BING_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String YOUTUBE_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_SHORT, Locale.US);
        return dateFormat.format(date);
    }

    public static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        Date result = null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_SHORT, Locale.US);

        try {
            result = dateFormat.parse(dateStr);
        } catch (Exception ex) {
            Log.e(TAG, String.format("Error while parsing string: %s", dateStr));
        }

        return result;
    }

    private DateUtils() {
    }
}
