package com.example.taskmaster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class notesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public notesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " +
                notesContract.notesEntry.TABLE_NAME + " (" +
                notesContract.notesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                notesContract.notesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                notesContract.notesEntry.COLUMN_BODY + " TEXT NOT NULL," +
                notesContract.notesEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + notesContract.notesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
