package com.example.taskmaster.data;

import android.provider.BaseColumns;

public class notesContract {

    public static final class notesEntry implements BaseColumns {

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

}
