package com.example.geocoding;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText addressEditText;
    private Button geocodeButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressEditText = findViewById(R.id.addressEditText);
        geocodeButton = findViewById(R.id.geocodeButton);
        resultTextView = findViewById(R.id.resultTextView);

        geocodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressString = addressEditText.getText().toString();
                if (!addressString.isEmpty()) {
                    performGeocoding(addressString);
                }
            }
        });
    }

    private void performGeocoding(String addressString) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(addressString, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                resultTextView.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                resultTextView.setText("No location found for the address.");
            }
        } catch (IOException e) {
            Log.e("Geocoding", "Geocoding failed", e);
            resultTextView.setText("Geocoding failed: " + e.getMessage());
        }
    }
}