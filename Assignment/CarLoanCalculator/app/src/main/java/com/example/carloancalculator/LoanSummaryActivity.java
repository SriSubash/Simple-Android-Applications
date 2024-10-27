package com.example.carloancalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoanSummaryActivity extends AppCompatActivity {

    private TextView textViewLoanSummary;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_summary);

        textViewLoanSummary = findViewById(R.id.textViewLoanSummary);
        buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        double monthlyPayment = intent.getDoubleExtra("monthly_payment", 0);
        double totalPayment = intent.getDoubleExtra("total_payment", 0);
        double loanAmount = intent.getDoubleExtra("loan_amount", 0);
        int term = intent.getIntExtra("term", 0);

        @SuppressLint("DefaultLocale") String loanSummary = String.format(
                "Loan Summary:\n\n" +
                        "Loan Amount (with tax): Rs.%.2f\n" +
                        "Term: %d years\n" +
                        "Monthly Payment: Rs.%.2f\n" +
                        "Total Payment: Rs.%.2f",
                loanAmount, term, monthlyPayment, totalPayment);

        textViewLoanSummary.setText(loanSummary);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
