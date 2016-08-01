package edu.udacity.android.contentfinder.util;

import android.net.Uri;
import android.util.Log;

/**
 * Created by shamim on 8/1/16.
 */
public class AppUtils {
    private static final String TAG = AppUtils.class.getSimpleName();

    public static String getSource(String urlStr) {
        String source = null;

        try {
            Uri uri = Uri.parse(urlStr);
            source = uri.getAuthority();
        } catch (Exception ex) {
            Log.i(TAG, "error while parsing source", ex);
        }

        return source;
    }
    private AppUtils() {
    }
}
