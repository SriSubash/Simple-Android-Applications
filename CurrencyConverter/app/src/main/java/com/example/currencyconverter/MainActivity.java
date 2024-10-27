package com.example.currencyconverter;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private Button buttonFromCurrency;
    private Button buttonToCurrency;
    private Button buttonConvert;
    private TextView textViewResult;

    private String fromCurrency = "USD";
    private String toCurrency = "USD";
    private Map<String, Double> exchangeRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAmount = findViewById(R.id.editTextAmount);
        buttonFromCurrency = findViewById(R.id.buttonFromCurrency);
        buttonToCurrency = findViewById(R.id.buttonToCurrency);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("INR", 74.0);

        buttonFromCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, true);
            }
        });

        buttonToCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, false);
            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency();
            }
        });
    }

    private void showPopupMenu(View view, final boolean isFromCurrency) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_currency, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                if (isFromCurrency) {
                    fromCurrency = item.getTitle().toString();
                    buttonFromCurrency.setText(fromCurrency);
                } else {
                    toCurrency = item.getTitle().toString();
                    buttonToCurrency.setText(toCurrency);
                }
                return true;
            }
        });
        popup.show();
    }

    private void convertCurrency() {
        String amountStr = editTextAmount.getText().toString();
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            textViewResult.setText("Invalid input");
            return;
        }

        double fromRate = exchangeRates.getOrDefault(fromCurrency, 1.0);
        double toRate = exchangeRates.getOrDefault(toCurrency, 1.0);
        double result = (amount / fromRate) * toRate;
        textViewResult.setText(String.valueOf(result));
    }
}