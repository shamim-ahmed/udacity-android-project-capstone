package edu.udacity.android.contentfinder.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    // private constructor to prevent instantiation
    private StringUtils() {
    }
}

