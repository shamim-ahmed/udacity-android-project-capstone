package edu.udacity.android.capstone.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    // private constructor to prevent instantiation
    private StringUtils() {
    }
}

