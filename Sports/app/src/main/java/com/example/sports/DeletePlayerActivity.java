package com.example.sports;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeletePlayerActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_player);
        dbHelper = new DatabaseHelper(this);

        editTextId = findViewById(R.id.editTextId);
    }

    public void onDeleteClick(View view) {
        int id = Integer.parseInt(editTextId.getText().toString());

        boolean result = dbHelper.deletePlayer(id);
        if (result) {
            Toast.makeText(this, "Player Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Deletion Failed", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

