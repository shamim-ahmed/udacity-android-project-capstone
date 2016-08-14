package edu.udacity.android.contentfinder.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();

    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
    public static final String BING_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String YOUTUBE_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        String result = null;

        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_SHORT, Locale.US);
            result = dateFormat.format(date);
        } catch (Exception ex) {
            Log.e(TAG, "Error while formatting date");
        }

        return result;
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
