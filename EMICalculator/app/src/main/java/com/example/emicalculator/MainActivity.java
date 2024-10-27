package com.example.emicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private EditText etPrincipal;
    private SeekBar seekBarYears;
    private TextView tvYears;
    private Spinner spinnerRate;
    private Button btnCalculate;
    String[] interestRates = {"5.0", "5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPrincipal = findViewById(R.id.et_principal);
        seekBarYears = findViewById(R.id.seekBar_years);
        tvYears = findViewById(R.id.tv_years);
        spinnerRate = findViewById(R.id.spinner_rate);
        btnCalculate = findViewById(R.id.btn_calculate);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interestRates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRate.setAdapter(adapter);
        seekBarYears.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvYears.setText(progress + " Year" + (progress > 1 ? "s" : ""));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEMI();
            }
        });
    }
    private void calculateEMI() {
        String principalStr = etPrincipal.getText().toString();
        if (principalStr.isEmpty()) {
            Toast.makeText(this, "Please enter the principal amount", Toast.LENGTH_SHORT).show();
            return;
        }
        double principal = Double.parseDouble(principalStr);
        int years = seekBarYears.getProgress();
        if (years == 0) {
            Toast.makeText(this, "Please select a valid number of years", Toast.LENGTH_SHORT).show();
            return;
        }
        double rate = Double.parseDouble(spinnerRate.getSelectedItem().toString());
        double monthlyRate = rate / (12 * 100);
        int months = years * 12;
        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
        Toast.makeText(this, "EMI: " + String.format("%.2f", emi), Toast.LENGTH_LONG).show();
    }
}
