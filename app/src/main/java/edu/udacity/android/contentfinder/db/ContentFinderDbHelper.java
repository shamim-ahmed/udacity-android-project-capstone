package edu.udacity.android.contentfinder.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shamim on 5/9/16.
 */
public class ContentFinderDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contentFinder.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TAG_TABLE_SQL = "CREATE TABLE " + ContentFinderContract.TagEntry.TABLE_NAME + "("
            + ContentFinderContract.TagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ContentFinderContract.TagEntry.COLUMN_TAG_NAME + " TEXT NOT NULL UNIQUE"
            + ")";

    public static final String CREATE_CONTENT_TABLE_SQL = "CREATE TABLE " + ContentFinderContract.ContentEntry.TABLE_NAME + "("
            + ContentFinderContract.ContentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ContentFinderContract.ContentEntry.COLUMN_CONTENT_ID + " INTEGER NOT NULL AUTOINCREMENT, "
            + ContentFinderContract.ContentEntry.COLUMN_CONTENT_TYPE + " TEXT NOT NULL, "
            + ContentFinderContract.ContentEntry.COLUMN_TITLE + " TEXT NOT NULL, "
            + ContentFinderContract.ContentEntry.COLUMN_SUMMARY + " TEXT, "
            + ContentFinderContract.ContentEntry.COLUMN_URL + " TEXT NOT NULL, "
            + ContentFinderContract.ContentEntry.COLUMN_PHOTO_URL + " TEXT, "
            + ContentFinderContract.ContentEntry.COLUMN_THUMBNAIL_URL + " TEXT"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TAG_TABLE_SQL);
        db.execSQL(CREATE_CONTENT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
