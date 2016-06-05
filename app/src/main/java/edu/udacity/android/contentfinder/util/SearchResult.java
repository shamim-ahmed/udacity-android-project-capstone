package edu.udacity.android.contentfinder.util;

import java.util.Date;

import edu.udacity.android.contentfinder.model.MediaItemType;

/**
 * Created by shamim on 5/06/16.
 */
public class SearchResult {
    private String title;
    private MediaItemType itemType;
    private Date publishDate;
    private String webUrl;
    private String source;

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

    @Override
    public String toString() {
        return title;
    }
}
