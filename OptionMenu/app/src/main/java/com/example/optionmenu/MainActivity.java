package com.example.optionmenu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> itemList;
    private List<String> selectedItems;
    private EditText editTextItemName;
    private EditText editTextItemRate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonDelete = findViewById(R.id.button_delete);
        editTextItemName = findViewById(R.id.editText_item_name);
        editTextItemRate = findViewById(R.id.editText_item_rate);

        itemList = new ArrayList<>();
        selectedItems = new ArrayList<>();

        // Initialize ListView with some default items
        initializeDefaultItems();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        buttonAdd.setOnClickListener(v -> showAddItemDialog());
        buttonDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void initializeDefaultItems() {
        itemList.add("Apple - $1.00");
        itemList.add("Banana - $0.50");
        itemList.add("Orange - $0.75");
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Item");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        EditText inputName = view.findViewById(R.id.editText_item_name);
        EditText inputRate = view.findViewById(R.id.editText_item_rate);

        builder.setView(view);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = inputName.getText().toString().trim();
            String rate = inputRate.getText().toString().trim();
            if (!name.isEmpty() && !rate.isEmpty()) {
                String item = name + " - $" + rate;
                itemList.add(item);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter both name and rate", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Items")
                .setMessage("Are you sure you want to delete selected items?")
                .setPositiveButton("Yes", (dialog, which) -> deleteSelectedItems())
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void deleteSelectedItems() {
        selectedItems.clear();
        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                selectedItems.add(adapter.getItem(i));
            }
        }
        itemList.removeAll(selectedItems);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Selected items deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showAddItemDialog();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}