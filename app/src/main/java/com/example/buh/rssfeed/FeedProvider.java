package com.example.buh.rssfeed;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Buh on 24.04.2017.
 */

//public class FeedProvider extends ContentProvider {
//    @Override
//    public boolean onCreate() {
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues contentValues) {
//        return null;
//    }
//
//    @Override
//    public int delete(Uri uri, String s, String[] strings) {
//        return 0;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
//        return 0;
//    }
//

//}