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
    private static final String DATE_FORMAT_STR = "yyyy-MM-dd:HH:mm:ss";

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR, Locale.US);
        return dateFormat.format(date);
    }

    public static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        Date result = null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR, Locale.US);

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
