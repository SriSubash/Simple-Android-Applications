package com.example.numberconversion;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText amountEditText;
    private Button submitButton;
    private static final double EXCHANGE_RATE = 0.011; // 1 Rupee = 0.011 Euro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEditText = findViewById(R.id.amountEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View anchor) {
        double rupees = Double.parseDouble(amountEditText.getText().toString());
        double euros = rupees * EXCHANGE_RATE;

        PopupMenu popupMenu = new PopupMenu(this, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Update the titles with calculated values
        popupMenu.getMenu().findItem(R.id.rupees).setTitle("Amount in Rupees: " + rupees);
        popupMenu.getMenu().findItem(R.id.euros).setTitle("Equivalent in Euros: " + String.format("%.2f", euros));

        popupMenu.show();
    }
}