package com.example.hallbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private Button time, date, submit;
    private TextView d, t;
    private String h = "";
    private SeekBar count;
    private Spinner hall;
    String[] depts = {"IT", "AIDS", "CSE", "EEE", "ECE", "CIVIL", "MECH"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, depts);
        AutoCompleteTextView dept = (AutoCompleteTextView)findViewById(R.id.dept);
        dept.setThreshold(3);
        dept.setAdapter(adapter);

        date = findViewById(R.id.datebtn);
        d = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                d.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        time = findViewById(R.id.timebtn);
        t = findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        hall = findViewById(R.id.hall);
        ArrayList<String> halls = new ArrayList<>();
        halls.add("MCC");
        halls.add("Valluvar Arangam");
        halls.add("Mech Seminar Hall");
        halls.add("ECE Seminar Hall");
        halls.add("S&H Seminar Hall");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, halls);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        hall.setAdapter(adapter2);

        count = findViewById(R.id.count);

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
        submit = findViewById(R.id.book);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n, de, da, ti;
                n = name.getText().toString();
                de = dept.getText().toString();
                da = d.getText().toString();
                ti = t.getText().toString();
                hall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        h = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                int c = count.getProgress();
                builder1.setMessage("Name : "+n+"\n"+
                                    "Department : "+de+"\n"+
                                    "Date : "+da+"\n"+
                                    "Time : "+ti+"\n"+
                                    "Hall : "+h+"\n"+
                                    "Count : "+c+"\n");
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
}