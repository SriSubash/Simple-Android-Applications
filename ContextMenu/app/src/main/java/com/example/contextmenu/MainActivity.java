package com.example.contextmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private int selectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonDelete = findViewById(R.id.button_delete);

        itemList = new ArrayList<>();
        selectedItems = new ArrayList<>();

        // Initialize ListView with some default items
        initializeDefaultItems();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        registerForContextMenu(listView);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedItemPosition = info.position;  // Save the position of the selected item
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                showEditItemDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showEditItemDialog() {
        String selectedItem = itemList.get(selectedItemPosition);
        String[] parts = selectedItem.split(" - \\$");
        String itemName = parts[0];
        String itemRate = parts[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_item, null);
        EditText inputName = view.findViewById(R.id.editText_item_name);
        EditText inputRate = view.findViewById(R.id.editText_item_rate);

        inputName.setText(itemName);
        inputRate.setText(itemRate);

        builder.setView(view);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = inputName.getText().toString().trim();
            String newRate = inputRate.getText().toString().trim();
            if (!newName.isEmpty() && !newRate.isEmpty()) {
                itemList.set(selectedItemPosition, newName + " - $" + newRate);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter both name and rate", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}