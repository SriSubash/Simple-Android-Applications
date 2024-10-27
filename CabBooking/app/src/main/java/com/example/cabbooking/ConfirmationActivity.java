package com.example.cabbooking;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    private EditText from, to, date, time, type;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_confirmation);
        from = findViewById(R.id.cfrom);
        to = findViewById(R.id.cto);
        date = findViewById(R.id.cdate);
        time = findViewById(R.id.ctime);
        type = findViewById(R.id.ctype);

        from.setText(getIntent().getStringExtra("from"));
        to.setText(getIntent().getStringExtra("to"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        type.setText(getIntent().getStringExtra("type"));

    }
}
