package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.taskmaster.Adapters.noteAdapter;
import com.example.taskmaster.Adapters.todoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements noteAdapter.ListItemOnClickListener, todoAdapter.ListItemOnClickListener{

    RecyclerView mRecycleNotes;
    RecyclerView mRecycleTodo;
    noteAdapter mAdapterNotes;
    todoAdapter mAdapterTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecycleNotes = (RecyclerView) findViewById(R.id.note_recycler_view);
        mRecycleTodo = (RecyclerView) findViewById(R.id.todo_recycler_view);
        TextView mSomething = (TextView) findViewById(R.id.something);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(getString(R.string.pref_taskmaster_key), false)) {
            mRecycleNotes.setVisibility(View.VISIBLE);
            mRecycleTodo.setVisibility(View.GONE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            mRecycleTodo.setLayoutManager(gridLayoutManager);
            mRecycleTodo.setHasFixedSize(true);

            mAdapterNotes = new noteAdapter(this);
            mRecycleNotes.setAdapter(mAdapterNotes);
            mSomething.setText("Somthing false");
        } else {
            mRecycleNotes.setVisibility(View.GONE);
            mRecycleTodo.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecycleTodo.setLayoutManager(layoutManager);

            mAdapterTodo = new todoAdapter(this);
            mRecycleTodo.setAdapter(mAdapterTodo);
            mSomething.setText("Something true");
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
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

    @Override
    public void onClickNotes(HashMap<String, String> note) {

    }

    @Override
    public void onClickTodo(HashMap<String, String> todo) {

    }
}
