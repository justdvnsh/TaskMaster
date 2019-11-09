package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.taskmaster.data.notesContract;
import com.example.taskmaster.data.todoContract;

public class addTodoActivity extends AppCompatActivity {

    EditText mAddTodo;
    RadioButton mRadBtn1;
    RadioButton mRadBtn2;
    RadioButton mRadBtn3;
    String priority;

    public void onPrioritySelected(View view) {

        if (mRadBtn1.isChecked()) {
            priority = "High";
        } else if (mRadBtn2.isChecked()) {
            priority = "Medium";
        } else if (mRadBtn3.isChecked()) {
            priority = "Low";
        }

    }

    private void addNewTodo(String body, String priority) {
        ContentValues cv = new ContentValues();
        cv.put(todoContract.todoEntry.COLUMN_BODY, body);
        cv.put(todoContract.todoEntry.COLUMN_PRIORITY, priority);

        Uri uri = getContentResolver().insert(todoContract.todoEntry.CONTENT_URI, cv);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        mAddTodo = (EditText) findViewById(R.id.add_todo);
        mRadBtn1 = (RadioButton) findViewById(R.id.radButton1);
        mRadBtn2 = (RadioButton) findViewById(R.id.radButton2);
        mRadBtn3 = (RadioButton) findViewById(R.id.radButton3);

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
            if (priority == null || priority == "") {
                Toast.makeText(getApplicationContext(), "You need to set in a priority", Toast.LENGTH_SHORT).show();
            } else {
                addNewTodo(mAddTodo.getText().toString(), priority);
//                MainActivity.mAdapterTodo.swapCursor(MainActivity.getAllTodos());
//                NavUtils.navigateUpFromSameTask(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
