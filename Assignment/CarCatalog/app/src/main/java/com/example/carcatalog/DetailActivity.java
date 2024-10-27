package com.example.carcatalog;

import  android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewSpecs;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewSpecs = findViewById(R.id.textViewSpecs);
        buttonBack = findViewById(R.id.buttonBack);

        // Get the selected vehicle name from the intent
        Intent intent = getIntent();
        String vehicleName = intent.getStringExtra("vehicle_name");

        // Display the specifications for the selected vehicle
        String vehicleSpecs = getVehicleSpecs(vehicleName);
        textViewSpecs.setText(vehicleSpecs);

        // Set up the back button to return to MainActivity
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the current activity and returns to the previous one
            }
        });
    }

    // Method to return specifications for each vehicle
    private String getVehicleSpecs(String vehicleName) {
        switch (vehicleName) {
            case "Honda Accord":
                return "2018 Honda Accord\nEngine: 1.5L I4 Turbo\nHorsepower: 192 hp\nFuel Economy: 30/38 mpg";
            case "Honda Civic":
                return "2018 Honda Civic\nEngine: 2.0L I4\nHorsepower: 158 hp\nFuel Economy: 28/40 mpg";
            case "Toyota Corolla":
                return "2018 Toyota Corolla\nEngine: 1.8L I4\nHorsepower: 132 hp\nFuel Economy: 28/36 mpg";
            case "Chevrolet Corvette":
                return "2018 Chevrolet Corvette\nEngine: 6.2L V8\nHorsepower: 455 hp\nFuel Economy: 16/25 mpg";
            case "Ford Taurus":
                return "2018 Ford Taurus\nEngine: 3.5L V6\nHorsepower: 288 hp\nFuel Economy: 18/27 mpg";
            default:
                return "No specifications available.";
        }
    }
}