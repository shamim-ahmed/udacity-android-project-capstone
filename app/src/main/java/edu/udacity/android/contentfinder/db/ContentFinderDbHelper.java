package edu.udacity.android.contentfinder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shamim on 5/9/16.
 */
public class ContentFinderDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contentFinder.db";
    public static final int DATABASE_VERSION = 1;

    public ContentFinderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_KEYWORD_TABLE_SQL = "CREATE TABLE " + ContentFinderContract.KeywordEntry.TABLE_NAME + "("
            + ContentFinderContract.KeywordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ContentFinderContract.KeywordEntry.COLUMN_WORD + " TEXT NOT NULL UNIQUE, "
            + ContentFinderContract.KeywordEntry.COLUMN_CREATED_DATE + " TEXT"
            + ")";

    public static final String CREATE_MEDIA_ITEM_TABLE_SQL = "CREATE TABLE " + ContentFinderContract.MediaItemEntry.TABLE_NAME + "("
            + ContentFinderContract.MediaItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ContentFinderContract.MediaItemEntry.COLUMN_ITEM_ID + " TEXT NOT NULL UNIQUE, "
            + ContentFinderContract.MediaItemEntry.COLUMN_CONTENT_TYPE_ID + " INTEGER NOT NULL, "
            + ContentFinderContract.MediaItemEntry.COLUMN_TITLE + " TEXT NOT NULL, "
            + ContentFinderContract.MediaItemEntry.COLUMN_SUMMARY + " TEXT, "
            + ContentFinderContract.MediaItemEntry.COLUMN_URL + " TEXT NOT NULL, "
            + ContentFinderContract.MediaItemEntry.COLUMN_PHOTO_URL + " TEXT, "
            + ContentFinderContract.MediaItemEntry.COLUMN_THUMBNAIL_URL + " TEXT, "
            + ContentFinderContract.MediaItemEntry.COLUMN_KEYWORD_ID + " INTEGER"
            + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KEYWORD_TABLE_SQL);
        db.execSQL(CREATE_MEDIA_ITEM_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
