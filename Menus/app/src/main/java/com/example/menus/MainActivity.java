package com.example.menus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Contact> contactList;
    private ArrayAdapter<String> adapter;
    private List<String> contactNames;
    private List<String> filteredContactNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);

        contactList = new ArrayList<>();
        contactList.add(new Contact("John Doe", "1234567890"));
        contactList.add(new Contact("Jane Smith", "0987654321"));
        // Add more contacts as needed

        contactNames = new ArrayList<>();
        for (Contact contact : contactList) {
            contactNames.add(contact.getName() + "\n" + contact.getPhone());
        }

        filteredContactNames = new ArrayList<>(contactNames);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredContactNames);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact contact = contactList.get(info.position);

        if (item.getItemId() == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getPhone()));
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                showAddContactDialog();
                return true;
            case R.id.search_contact:
                showSearchContactDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);

        EditText nameInput = view.findViewById(R.id.contactNameInput);
        EditText phoneInput = view.findViewById(R.id.contactPhoneInput);

        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    Contact newContact = new Contact(name, phone);
                    contactList.add(newContact);
                    String contactString = name + "\n" + phone;
                    contactNames.add(contactString);
                    filteredContactNames.add(contactString);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void showSearchContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search Contact");

        View view = getLayoutInflater().inflate(R.layout.dialog_search_contact, null);

        EditText searchInput = view.findViewById(R.id.contactSearchInput);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredContactNames.clear();
                for (String contact : contactNames) {
                    if (contact.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredContactNames.add(contact);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        builder.setView(view);
        builder.setNeutralButton("Clear Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchInput.setText("");
                filteredContactNames.clear();
                filteredContactNames.addAll(contactNames);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setPositiveButton("Close", null);
        builder.create().show();
    }
}