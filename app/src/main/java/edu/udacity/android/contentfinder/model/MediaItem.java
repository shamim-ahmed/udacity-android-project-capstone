package edu.udacity.android.contentfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by shamim on 5/06/16.
 */

public class MediaItem implements Parcelable {
    private Long id;
    private String itemId;
    private MediaItemType contentType;
    private String title;
    private String description;
    private String webUrl;
    private String thumbnailUrl;
    private String source;
    private Date publishDate;
    private Long keywordId;

    public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator<MediaItem>() {

        @Override
        public MediaItem createFromParcel(Parcel source) {
            return new MediaItem(source);
        }

        @Override
        public MediaItem[] newArray(int size) {
            return new MediaItem[size];
        }
    };

    public MediaItem() {
    }

    public MediaItem(Parcel parcel) {
        Object[] fieldValues = parcel.readArray(ClassLoader.getSystemClassLoader());
        id = (Long) fieldValues[0];
        itemId = (String) fieldValues[1];
        contentType = (MediaItemType) fieldValues[2];
        title = (String) fieldValues[3];
        description = (String) fieldValues[4];
        webUrl = (String) fieldValues[5];
        thumbnailUrl = (String) fieldValues[6];
        source = (String) fieldValues[7];
        publishDate = (Date) fieldValues[8];
        keywordId = (Long) fieldValues[9];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaItemType getContentType() {
        return contentType;
    }

    public void setContentType(MediaItemType contentType) {
        this.contentType = contentType;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        Object[] fields = {id, itemId, contentType, title, description, webUrl, thumbnailUrl, source, publishDate, keywordId};
        dest.writeArray(fields);
    }
}
