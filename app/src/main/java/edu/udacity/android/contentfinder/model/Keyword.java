package edu.udacity.android.contentfinder.model;

import java.util.Date;

/**
 * Created by shamim on 5/31/16.
 */
public class Keyword {
    private Long id;
    private String word;
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return word;
    }
}
