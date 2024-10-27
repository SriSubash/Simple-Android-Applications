package com.example.carcatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerVehicles = findViewById(R.id.spinnerVehicles);

        // Define vehicle array
        String[] vehicles = getResources().getStringArray(R.array.vehicle_array);

        // Set up the spinner with the vehicle array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicles);
        spinnerVehicles.setAdapter(adapter);

        spinnerVehicles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) { // Skip the first item if it’s a placeholder
                    // Start DetailActivity and pass the selected vehicle name
                    String selectedVehicle = parent.getItemAtPosition(position).toString();
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("vehicle_name", selectedVehicle);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}