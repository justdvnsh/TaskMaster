package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.example.taskmaster.Adapters.noteAdapter;
import com.example.taskmaster.Adapters.todoAdapter;
import com.example.taskmaster.data.notesContract;
import com.example.taskmaster.data.notesDbHelper;
import com.example.taskmaster.data.todoContract;
import com.example.taskmaster.data.todoDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements noteAdapter.ListItemOnClickListener, todoAdapter.ListItemOnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Cursor> {


    SharedPreferences sharedPreferences;
    RecyclerView mRecycleNotes;
    RecyclerView mRecycleTodo;
    static noteAdapter mAdapterNotes;
    static todoAdapter mAdapterTodo;

    private static final int TODO_LOADER_ID = 1;

    protected static SQLiteDatabase mDb;
    protected static SQLiteDatabase mDbTodo;

    protected static Cursor getAllNotes() {
        return mDb.query(
                notesContract.notesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                notesContract.notesEntry.COLUMN_TIMESTAMP
        );
    }

    protected static Cursor getAllTodos() {
        return mDbTodo.query(
                todoContract.todoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                todoContract.todoEntry.COLUMN_TIMESTAMP
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecycleNotes = (RecyclerView) findViewById(R.id.note_recycler_view);
        mRecycleTodo = (RecyclerView) findViewById(R.id.todo_recycler_view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        if (sharedPreferences.getBoolean(getString(R.string.pref_taskmaster_key), false) == false) {
            mRecycleNotes.setVisibility(View.VISIBLE);
            mRecycleTodo.setVisibility(View.GONE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            mRecycleNotes.setLayoutManager(gridLayoutManager);
            mRecycleNotes.setHasFixedSize(true);

            notesDbHelper dbHelper = new notesDbHelper(this);
            mDb = dbHelper.getWritableDatabase();
            Cursor cursor = getAllNotes();

            mAdapterNotes = new noteAdapter(this, cursor);
            mRecycleNotes.setAdapter(mAdapterNotes);
            implementSwipeDelete("notes", mRecycleNotes);
        } else {
            mRecycleNotes.setVisibility(View.GONE);
            mRecycleTodo.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecycleTodo.setLayoutManager(layoutManager);

            todoDbHelper dbHelper = new todoDbHelper(this);
            mDbTodo = dbHelper.getWritableDatabase();
            Cursor cursor = getAllTodos();

            mAdapterTodo = new todoAdapter(this, cursor);
            mRecycleTodo.setAdapter(mAdapterTodo);
            implementSwipeDelete("todos", mRecycleTodo);

            getSupportLoaderManager().initLoader(TODO_LOADER_ID, null, this);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getBoolean(getString(R.string.pref_taskmaster_key), false) == false) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, addTodoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean removeNotes(long id) {
        return mDb.delete(notesContract.notesEntry.TABLE_NAME,
                            notesContract.notesEntry._ID + "=" + id, null) > 0;
    }

    private void removeTodos (long id) {
        String idString = String.valueOf(id);
        Uri uri = todoContract.todoEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();
        getContentResolver().delete(uri, null, null);
        getSupportLoaderManager().restartLoader(TODO_LOADER_ID, null, this);
    }

    private void implementSwipeDelete(final String task, RecyclerView view) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                if (task == "notes") {
                    removeNotes(id);
                    mAdapterNotes.swapCursor(MainActivity.getAllNotes());
                } else if (task == "todos") {
                    removeTodos(id);
                }
            }
        }).attachToRecyclerView(view);
    }

    @Override
    public void onClickNotes(HashMap<String, String> note) {

    }

    @Override
    public void onClickTodo(HashMap<String, String> todo) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TODO_LOADER_ID, null, this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if ( s.equals(getString(R.string.pref_taskmaster_key)) ) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mTodoData = null;

            @Override
            protected void onStartLoading() {
                if (mTodoData != null) {
                    deliverResult(mTodoData);
                } else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(todoContract.todoEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mTodoData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapterTodo.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapterTodo.swapCursor(null);
    }
}
