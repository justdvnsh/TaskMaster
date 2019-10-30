package com.example.taskmaster.data;

import android.provider.BaseColumns;

public class todoContract {

    public static final class todoEntry implements BaseColumns {

        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

}
