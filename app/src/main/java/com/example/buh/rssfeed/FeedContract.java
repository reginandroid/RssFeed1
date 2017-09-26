package com.example.buh.rssfeed;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;



public class FeedContract {
    private FeedContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.buh.rssfeed";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ENTRIES = "articles";

    public static class Entry implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.rssfeed.articles";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.rssfeed.article";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

        public static final String TABLE_NAME = "article";

        public static final String _ID = "_id";

        public static final String COLUMN_NAME_TITLE = " title";

        public static final String COLUMN_NAME_DESCRIPTION = " description";

        public static final String COLUMN_NAME_AUTHOR = " author";

        public static final String COLUMN_NAME_DATETIME = " datetime";

        public static final String COLUMN_NAME_URL = " url";

        public static final String COLUMN_IMAGE_URL = " imageurl";
    }
}
