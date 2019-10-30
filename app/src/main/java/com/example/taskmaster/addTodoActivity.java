package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.taskmaster.data.notesContract;
import com.example.taskmaster.data.todoContract;

public class addTodoActivity extends AppCompatActivity {

    EditText mAddTodo;
    String todo;
    String priority = "high";

    private long addNewTodo(String body, String priority) {
        ContentValues cv = new ContentValues();
        cv.put(todoContract.todoEntry.COLUMN_BODY, body);
        cv.put(todoContract.todoEntry.COLUMN_PRIORITY, priority);
        return MainActivity.mDbTodo.insert(todoContract.todoEntry.TABLE_NAME, null, cv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        mAddTodo = (EditText) findViewById(R.id.add_todo);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
            addNewTodo(mAddTodo.getText().toString(), priority);
            MainActivity.mAdapterNotes.swapCursor(MainActivity.getAllTodos());
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

}
