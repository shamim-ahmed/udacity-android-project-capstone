package edu.udacity.android.contentfinder.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import edu.udacity.android.contentfinder.model.MediaItemType;

/**
 * Created by shamim on 5/06/16.
 */

/**
 * TODO create a hierarchy of classes. Common fields should be at the root class. Subclasses will
 * add more specific fields
 */

public class SearchResult implements Parcelable {
    private String itemId;
    private String title;
    private String description;
    private MediaItemType itemType;
    private String webUrl;
    private String source;
    private Date publishDate;

    public static final Parcelable.Creator<SearchResult> CREATOR = new Parcelable.Creator<SearchResult>() {

        @Override
        public SearchResult createFromParcel(Parcel source) {
            return new SearchResult(source);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public SearchResult() {
    }

    public SearchResult(Parcel parcel) {
        Object[] fieldValues = parcel.readArray(ClassLoader.getSystemClassLoader());
        itemId = (String) fieldValues[0];
        title = (String) fieldValues[1];
        description = (String) fieldValues[2];
        itemType = (MediaItemType) fieldValues[3];
        webUrl = (String) fieldValues[4];
        source = (String) fieldValues[5];
        publishDate = (Date) fieldValues[6];
    }

    public MediaItemType getItemType() {
        return itemType;
    }

    public void setItemType(MediaItemType itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Object[] fields = {itemId, title, description, itemType, webUrl, source, publishDate};
        dest.writeArray(fields);
    }
}
