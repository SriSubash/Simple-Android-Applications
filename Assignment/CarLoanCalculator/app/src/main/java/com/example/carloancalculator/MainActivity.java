package com.example.carloancalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCarPrice, editTextDownPayment;
    private Spinner spinnerTerm;
    private Button buttonGenerateReport;
    private static final double SALES_TAX_RATE = 0.07;
    private static final double INTEREST_RATE = 0.09;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCarPrice = findViewById(R.id.editTextCarPrice);
        editTextDownPayment = findViewById(R.id.editTextDownPayment);
        spinnerTerm = findViewById(R.id.spinnerTerm);
        buttonGenerateReport = findViewById(R.id.buttonGenerateReport);

        String[] loanTerms = {"2 years", "3 years", "4 years"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, loanTerms);
        spinnerTerm.setAdapter(adapter);

        buttonGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoan();
            }
        });
    }

    private void calculateLoan() {
        try {
            double carPrice = Double.parseDouble(editTextCarPrice.getText().toString());
            double downPayment = Double.parseDouble(editTextDownPayment.getText().toString());
            int termInYears = Integer.parseInt(spinnerTerm.getSelectedItem().toString().split(" ")[0]);

            double loanAmount = carPrice - downPayment;
            double salesTax = loanAmount * SALES_TAX_RATE;
            double loanWithTax = loanAmount + salesTax;

            int termInMonths = termInYears * 12;
            double monthlyInterestRate = INTEREST_RATE / 12;
            double monthlyPayment = (loanWithTax * monthlyInterestRate) /
                    (1 - Math.pow(1 + monthlyInterestRate, -termInMonths));
            double totalPayment = monthlyPayment * termInMonths;

            Intent intent = new Intent(MainActivity.this, LoanSummaryActivity.class);
            intent.putExtra("monthly_payment", monthlyPayment);
            intent.putExtra("total_payment", totalPayment);
            intent.putExtra("loan_amount", loanWithTax);
            intent.putExtra("term", termInYears);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show();
        }
    }
}