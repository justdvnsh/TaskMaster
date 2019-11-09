package com.example.taskmaster.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class todoContract {

    public static final String AUTHORITY = "com.example.taskmaster";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TODOS = "todos";

    public static final class todoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODOS).build();

        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

}
