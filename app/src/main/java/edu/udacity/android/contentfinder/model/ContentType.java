package edu.udacity.android.contentfinder.model;

/**
 * Created by shamim on 5/4/16.
 */
public enum ContentType {
    NEWS("news"), PHOTO("photo"), TWEET("tweet"), VIDEO("video");

    private String name;

    private ContentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
