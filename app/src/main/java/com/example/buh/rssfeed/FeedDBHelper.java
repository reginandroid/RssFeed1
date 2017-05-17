package com.example.buh.rssfeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedDBHelper {
    FeedDatabase openHelper;
   private SQLiteDatabase db;

    public FeedDBHelper(Context context) {
        openHelper = new FeedDatabase(context);
        db = openHelper.getWritableDatabase();
    }

    public void saveArticleItem(String title, String description, String author, String datetime, String url){
        ContentValues cv = new ContentValues();
       // cv.put(FeedContract.Entry.COLUMN_NAME_IMAGE, image);
        cv.put(FeedContract.Entry.COLUMN_NAME_TITLE, title);
        cv.put(FeedContract.Entry.COLUMN_NAME_DESCRIPTION, description);
        cv.put(FeedContract.Entry.COLUMN_NAME_AUTHOR, author);
        cv.put(FeedContract.Entry.COLUMN_NAME_DATETIME, datetime);
        cv.put(FeedContract.Entry.COLUMN_NAME_URL, url);
        db.insert(FeedContract.Entry.TABLE_NAME, null, cv);
    }

    public void close() {
        db.close();
    }
    public Cursor getArticleList() {
        return db.rawQuery("select * from " + FeedContract.Entry.TABLE_NAME, null);
    }

    static class FeedDatabase extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "feed.db";

        private static final String TYPE_TEXT = " TEXT";
        private static final String TYPE_INTEGER = " INTEGER";
        private static final String TYPE_BLOB = " BLOB NOT NULL";
        private static final String COMMA_SEP = ",";

        private static final String SQL_CREATE_ARTICLES =
                "CREATE TABLE " + FeedContract.Entry.TABLE_NAME + " (" +
                        FeedContract.Entry._ID + " INTEGER PRIMARY KEY," +
                        FeedContract.Entry.COLUMN_NAME_TITLE    + TYPE_TEXT + COMMA_SEP +
                        FeedContract.Entry.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEP +
                        FeedContract.Entry.COLUMN_NAME_AUTHOR + TYPE_TEXT + COMMA_SEP +
                        FeedContract.Entry.COLUMN_NAME_DATETIME + TYPE_TEXT + COMMA_SEP +
                        FeedContract.Entry.COLUMN_NAME_URL + TYPE_TEXT + ")";

        private static final String SQL_DELETE_ARTICLES =
                "DROP TABLE IF EXISTS " + FeedContract.Entry.TABLE_NAME;

        public FeedDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ARTICLES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ARTICLES);
            onCreate(db);
        }
    }
}
