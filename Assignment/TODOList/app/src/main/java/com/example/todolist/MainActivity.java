package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TODO LIST");
        setSupportActionBar(toolbar);
        addTask();
        listViewTasks = findViewById(R.id.listViewTasks);

        // Initialize the tasks list and adapter
        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listViewTasks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task:
                addTask();
                return true;
            case R.id.delete_task:
                deleteTask();
                return true;
            case R.id.update_task:
                updateTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addTask() {
        final EditText taskInput = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add New Task");
        dialog.setView(taskInput);
        dialog.setPositiveButton("Add", (dialogInterface, i) -> {
            String task = taskInput.getText().toString().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    private void deleteTask() {
        final EditText taskNumberInput = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete Task");
        dialog.setMessage("Enter task number to delete:");
        dialog.setView(taskNumberInput);
        dialog.setPositiveButton("Delete", (dialogInterface, i) -> {
            String input = taskNumberInput.getText().toString().trim();
            if (!input.isEmpty()) {
                int taskNumber = Integer.parseInt(input);
                if (taskNumber > 0 && taskNumber <= tasks.size()) {
                    tasks.remove(taskNumber - 1);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid task number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Task number cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    private void updateTask() {
        final EditText taskNumberInput = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Update Task");
        dialog.setMessage("Enter task number to update:");
        dialog.setView(taskNumberInput);
        dialog.setPositiveButton("Next", (dialogInterface, i) -> {
            String input = taskNumberInput.getText().toString().trim();
            if (!input.isEmpty()) {
                int taskNumber = Integer.parseInt(input);
                if (taskNumber > 0 && taskNumber <= tasks.size()) {
                    showUpdateTaskDialog(taskNumber - 1);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid task number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Task number cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    private void showUpdateTaskDialog(final int index) {
        final EditText taskInput = new EditText(this);
        taskInput.setText(tasks.get(index));
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Update Task");
        dialog.setView(taskInput);
        dialog.setPositiveButton("Update", (dialogInterface, i) -> {
            String task = taskInput.getText().toString().trim();
            if (!task.isEmpty()) {
                tasks.set(index, task);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }
}