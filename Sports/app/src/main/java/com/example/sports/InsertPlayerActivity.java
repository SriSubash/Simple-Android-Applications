package com.example.sports;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class InsertPlayerActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextId, editTextName, editTextCategory, editTextMatchesPlayed, editTextScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_player);
        dbHelper = new DatabaseHelper(this);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextMatchesPlayed = findViewById(R.id.editTextMatchesPlayed);
        editTextScores = findViewById(R.id.editTextScores);
    }

    public void onInsertClick(View view) {
        int id = Integer.parseInt(editTextId.getText().toString());
        String name = editTextName.getText().toString();
        String category = editTextCategory.getText().toString();
        int matchesPlayed = Integer.parseInt(editTextMatchesPlayed.getText().toString());
        int scores = Integer.parseInt(editTextScores.getText().toString());

        boolean result = dbHelper.insertPlayer(id, name, category, matchesPlayed, scores);
        if (result) {
            Toast.makeText(this, "Player Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

