package edu.udacity.android.contentfinder.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shamim on 7/31/16.
 */
public class DateUtils {
    private static final String DATE_FORMAT_STR = "yyyy-MM-dd:HH:mm:ss";

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR, Locale.US);
        return dateFormat.format(date);
    }

    private DateUtils() {
    }
}
