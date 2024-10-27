package com.example.sports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onInsertClick(View view) {
        startActivity(new Intent(this, InsertPlayerActivity.class));
    }

    public void onViewClick(View view) {
        startActivity(new Intent(this, ViewPlayersActivity.class));
    }

    public void onUpdateClick(View view) {
        startActivity(new Intent(this, UpdatePlayerActivity.class));
    }

    public void onDeleteClick(View view) {
        startActivity(new Intent(this, DeletePlayerActivity.class));
    }
}
