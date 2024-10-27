package com.example.sports;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewPlayersActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_players);
        dbHelper = new DatabaseHelper(this);

        listView = findViewById(R.id.listViewPlayers);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        // Load players based on selected category
        findViewById(R.id.buttonLoad).setOnClickListener(v -> loadPlayers());
    }

    private void loadPlayers() {
        String category = spinnerCategory.getSelectedItem().toString();
        Cursor cursor = dbHelper.getPlayersByCategory(category);

        ArrayList<String> playerList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String categoryText = cursor.getString(2);
            int matchesPlayed = cursor.getInt(3);
            int scores = cursor.getInt(4);

            playerList.add("ID: " + id + ", Name: " + name + ", Matches: " + matchesPlayed + ", Scores: " + scores);
        }

        if (playerList.isEmpty()) {
            Toast.makeText(this, "No players found", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerList);
            listView.setAdapter(adapter);
        }
    }
}
