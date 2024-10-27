package com.example.pizzabooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCustomerName, editTextPhone;
    private RadioGroup radioGroupShape;
    private CheckBox checkBoxPepperoni, checkBoxMushrooms, checkBoxOnions, checkBoxSausage, checkBoxBacon;
    private Button buttonPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroupShape = findViewById(R.id.radioGroupShape);
        checkBoxPepperoni = findViewById(R.id.checkBoxPepperoni);
        checkBoxMushrooms = findViewById(R.id.checkBoxMushrooms);
        checkBoxOnions = findViewById(R.id.checkBoxOnions);
        checkBoxSausage = findViewById(R.id.checkBoxSausage);
        checkBoxBacon = findViewById(R.id.checkBoxBacon);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    private void placeOrder() {
        String customerName = editTextCustomerName.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();

        if (customerName.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please provide customer name and phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedShapeId = radioGroupShape.getCheckedRadioButtonId();
        RadioButton selectedShapeRadioButton = findViewById(selectedShapeId);
        String pizzaShape = selectedShapeRadioButton.getText().toString();

        StringBuilder toppings = new StringBuilder();
        if (checkBoxPepperoni.isChecked()) toppings.append("Pepperoni, ");
        if (checkBoxMushrooms.isChecked()) toppings.append("Mushrooms, ");
        if (checkBoxOnions.isChecked()) toppings.append("Onions, ");
        if (checkBoxSausage.isChecked()) toppings.append("Sausage, ");
        if (checkBoxBacon.isChecked()) toppings.append("Bacon, ");

        if (toppings.length() == 0) {
            Toast.makeText(this, "Please select at least one topping.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (toppings.length() > 0) {
            toppings.setLength(toppings.length() - 2);
        }

        String orderSummary = "Customer Name: " + customerName +
                "\nPhone Number: " + phoneNumber +
                "\nPizza Shape: " + pizzaShape +
                "\nToppings: " + toppings.toString();

        Toast.makeText(this, orderSummary, Toast.LENGTH_LONG).show();
    }
}