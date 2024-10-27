package com.example.cabbooking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    private EditText from, to, date, time;
    private Button book;
    private Spinner type;
    String[] types = {"Sedan","SUV","Luxury","Elegant"};

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_booking);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        book = findViewById(R.id.book);
        type = findViewById(R.id.type);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, types);
        type.setAdapter(adapter);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f = from.getText().toString();
                String too = to.getText().toString();
                String d = date.getText().toString();
                String t = time.getText().toString();
                String ty = type.getSelectedItem().toString();
                Intent i = new Intent(BookingActivity.this, ConfirmationActivity.class);
                i.putExtra("from", f);
                i.putExtra("to", too);
                i.putExtra("date", d);
                i.putExtra("time", t);
                i.putExtra("type", ty);
                startActivity(i);
            }
        });
    }
}
