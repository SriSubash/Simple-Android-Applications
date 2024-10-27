package com.example.burgercalorie;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupPatty;
    private CheckBox checkCheese, checkBacon, checkAvocado;
    private SeekBar seekBarSauce;
    private TextView textViewCalories;

    private int pattyCalories = 0;
    private int toppingCalories = 0;
    private int sauceCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupPatty = findViewById(R.id.radioGroupPatty);
        checkCheese = findViewById(R.id.checkCheese);
        checkBacon = findViewById(R.id.checkBacon);
        checkAvocado = findViewById(R.id.checkAvocado);
        seekBarSauce = findViewById(R.id.seekBarSauce);
        textViewCalories = findViewById(R.id.textViewCalories);

        radioGroupPatty.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioBeef:
                    pattyCalories = 300;
                    break;
                case R.id.radioChicken:
                    pattyCalories = 250;
                    break;
                case R.id.radioVeggie:
                    pattyCalories = 200;
                    break;
            }
            updateCalories();
        });

        checkCheese.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toppingCalories += isChecked ? 100 : -100;
            updateCalories();
        });

        checkBacon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toppingCalories += isChecked ? 150 : -150;
            updateCalories();
        });

        checkAvocado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toppingCalories += isChecked ? 80 : -80;
            updateCalories();
        });

        seekBarSauce.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sauceCalories = progress * 10; // Assuming 10 calories per 10g
                updateCalories();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateCalories() {
        int totalCalories = pattyCalories + toppingCalories + sauceCalories;
        textViewCalories.setText("Total Calories: " + totalCalories);
    }
}