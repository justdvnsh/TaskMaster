package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.taskmaster.data.notesContract;
import com.example.taskmaster.data.notesDbHelper;

public class addTitleActivity extends AppCompatActivity {

    EditText mAddTitle;
    String note;

    private long addNewNote(String body, String title) {
        ContentValues cv = new ContentValues();
        cv.put(notesContract.notesEntry.COLUMN_TITLE, title);
        cv.put(notesContract.notesEntry.COLUMN_BODY, body);
        return MainActivity.mDb.insert(notesContract.notesEntry.TABLE_NAME, null, cv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);

        mAddTitle = (EditText) findViewById(R.id.add_title);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        note = intent.getStringExtra("note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_title, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        if (id == R.id.save_note) {
            addNewNote(note, mAddTitle.getText().toString());
            MainActivity.mAdapterNotes.swapCursor(MainActivity.getAllNotes());
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
