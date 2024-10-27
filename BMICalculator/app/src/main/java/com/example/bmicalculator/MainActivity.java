package com.example.bmicalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText weight;
    private Spinner height;
    private Button btn;

    private double h, w, bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        btn = findViewById(R.id.submit);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        height.setAdapter(adapter);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                w = Double.parseDouble(weight.getText().toString());
                bmi = w / ( h * h ) * 10;
                h = Double.parseDouble(height.getSelectedItem().toString());
                if(bmi <= 18.4)
                {
                    builder1.setMessage("You are UnderWeight");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else if(bmi >= 40.0)
                {
                    builder1.setMessage("You are OverWeight");
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else
                {
                    Toast t = Toast.makeText(MainActivity.this, "You are Normal", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}