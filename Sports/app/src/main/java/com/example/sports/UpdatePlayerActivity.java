package com.example.sports;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdatePlayerActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextId, editTextScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_player);
        dbHelper = new DatabaseHelper(this);

        editTextId = findViewById(R.id.editTextId);
        editTextScores = findViewById(R.id.editTextScores);
    }

    public void onUpdateClick(View view) {
        int id = Integer.parseInt(editTextId.getText().toString());
        int scores = Integer.parseInt(editTextScores.getText().toString());

        boolean result = dbHelper.updatePlayerScores(id, scores);
        if (result) {
            Toast.makeText(this, "Scores Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
