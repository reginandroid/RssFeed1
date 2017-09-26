package com.example.buh.rssfeed;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

    public class FeedDBHelper {
    FeedDatabase openHelper;
    NewsItem item;
    private SQLiteDatabase db;
    private String [] columnItemName = {FeedContract.Entry._ID, FeedContract.Entry.COLUMN_NAME_TITLE,
        FeedContract.Entry.COLUMN_NAME_DESCRIPTION, FeedContract.Entry.COLUMN_NAME_AUTHOR,
        FeedContract.Entry.COLUMN_NAME_DATETIME, FeedContract.Entry.COLUMN_NAME_URL, FeedContract.Entry.COLUMN_IMAGE_URL};
    public FeedDBHelper(Context context) {
        openHelper = new FeedDatabase(context);
        db = openHelper.getWritableDatabase();
    }

    public void saveArticleItem(ArrayList<NewsItem> list){

    for (int i=0; i<list.size(); i++){
    ContentValues cv = new ContentValues();
        cv.put(FeedContract.Entry.COLUMN_NAME_TITLE, list.get(i).getNewsHeading());
        cv.put(FeedContract.Entry.COLUMN_NAME_DESCRIPTION, list.get(i).getNewsDesc());
        cv.put(FeedContract.Entry.COLUMN_NAME_AUTHOR, list.get(i).getAuthor());
        cv.put(FeedContract.Entry.COLUMN_NAME_DATETIME, list.get(i).getDateTime());
        cv.put(FeedContract.Entry.COLUMN_NAME_URL, list.get(i).getUrl());
        cv.put(FeedContract.Entry.COLUMN_IMAGE_URL, list.get(i).getImageurl());
        db.insert(FeedContract.Entry.TABLE_NAME, null, cv);
    }}

    public boolean close() {
        db.close();
        return true;
    }
    public ArrayList<NewsItem> getArticleList() {

            ArrayList<NewsItem> items = new ArrayList<NewsItem>();

        db = openHelper.getWritableDatabase();
            Cursor cursor = db.query(FeedContract.Entry.TABLE_NAME,
                    columnItemName, null, null, null, null, null);
       if (cursor.moveToFirst()){
       do{

                NewsItem item = new NewsItem();
                item.setNewsHeading(cursor.getString(1));
                item.setNewsDescSmall(cursor.getString(2));
                item.setAuthor(cursor.getString(3));
                item.setDateTime(cursor.getString(4));
                item.setUrl(cursor.getString(5));
                item.setImageurl(cursor.getString(6));
                items.add(item);

            } while( cursor.moveToNext());
       }
            cursor.close();
            return items;
        }

    public Cursor queryDB(){
    db = openHelper.getWritableDatabase();
        Cursor c = db.query(FeedContract.Entry.TABLE_NAME, null, null,null,null,null,null);
        return c;
    }

    public boolean isHasRow(){
        db = openHelper.getWritableDatabase();
        boolean result = false;
        Cursor c = db.query(FeedContract.Entry.TABLE_NAME,
                null, null, null, null, null, null);
        if (c.getCount()>0) result = true;
        c.close();
        db.close();
        return result;
    }
    public void deleteAllrows( ){
        db.delete(FeedContract.Entry.TABLE_NAME, null, null);

    }
    public void deleteDB() {

db = openHelper.getWritableDatabase();
       db.delete(FeedContract.Entry.TABLE_NAME, null, null);
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
                        FeedContract.Entry.COLUMN_NAME_URL + TYPE_TEXT + COMMA_SEP +
                        FeedContract.Entry.COLUMN_IMAGE_URL + TYPE_TEXT +")";

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
