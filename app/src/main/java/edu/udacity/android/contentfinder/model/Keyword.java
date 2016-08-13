package edu.udacity.android.contentfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Keyword implements Parcelable {
    private Long id;
    private String word;
    private Date createdDate;

    public static final Parcelable.Creator<Keyword> CREATOR = new Parcelable.Creator<Keyword>() {

        @Override
        public Keyword createFromParcel(Parcel source) {
            return new Keyword(source);
        }

        @Override
        public Keyword[] newArray(int size) {
            return new Keyword[size];
        }
    };

    public Keyword() {
    }

    public Keyword(Parcel parcel) {
        Object[] fieldValues = parcel.readArray(ClassLoader.getSystemClassLoader());
        this.id = (Long) fieldValues[0];
        this.word = (String) fieldValues[1];
        this.createdDate = (Date) fieldValues[2];
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeArray(new Object[]{id, word, createdDate});
    }
}
