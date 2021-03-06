package edu.udacity.android.contentfinder.util;

import android.net.Uri;
import android.util.Log;

public class AppUtils {
    private static final String TAG = AppUtils.class.getSimpleName();

    public static String getSource(String urlStr) {
        if (StringUtils.isBlank(urlStr)) {
            return null;
        }

        String source = null;

        try {
            Uri uri = Uri.parse(urlStr);
            source = uri.getAuthority();
        } catch (Exception ex) {
            Log.i(TAG, "error while parsing source", ex);
        }

        return source;
    }

    // private constructor to prevent instantiation
    private AppUtils() {
    }
}
