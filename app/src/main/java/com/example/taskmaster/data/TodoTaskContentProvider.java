package com.example.taskmaster.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.taskmaster.data.todoContract.todoEntry.CONTENT_URI;
import static com.example.taskmaster.data.todoContract.todoEntry.TABLE_NAME;

public class TodoTaskContentProvider extends ContentProvider {

    public static final int TODOS = 100;
    public static final int TODOS_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(todoContract.AUTHORITY, todoContract.PATH_TODOS, TODOS);
        uriMatcher.addURI(todoContract.AUTHORITY, todoContract.PATH_TODOS + "/#", TODOS_WITH_ID);

        return uriMatcher;
    }

    private todoDbHelper mtodoDbHelper;

    @Override
    public boolean onCreate() {
        mtodoDbHelper = new todoDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mtodoDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch(match) {
            case TODOS:
                retCursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null ,sortOrder);
                break;

            case TODOS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknwon Uri" + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mtodoDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {

            case TODOS:
                long id = db.insert(TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("failed to insert in db with uri " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unkown Uri" + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String projection, @Nullable String[] sortOrder) {
        final SQLiteDatabase db = mtodoDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {
            case TODOS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                retCursor = db.delete(TABLE_NAME, mSelection, mSelectionArgs);
                break;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
