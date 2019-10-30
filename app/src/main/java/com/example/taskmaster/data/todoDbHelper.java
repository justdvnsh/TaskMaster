package com.example.taskmaster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class todoDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public todoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " +
                todoContract.todoEntry.TABLE_NAME + " (" +
                todoContract.todoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                todoContract.todoEntry.COLUMN_BODY + " TEXT NOT NULL," +
                todoContract.todoEntry.COLUMN_PRIORITY + " TEXT NOT NULL," +
                todoContract.todoEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + todoContract.todoEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
