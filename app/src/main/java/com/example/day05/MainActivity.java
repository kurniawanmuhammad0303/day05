package com.example.day05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText materialEditTextName;
    private Spinner spinnerItems;
    private TextInputEditText editTextQuantity;
    private RadioGroup radioGroup;
    private Button buttonProcess;
    private Spinner languageSpinner;

    private Map<String, Integer> itemPrices;

    private static final double REGULAR_DISCOUNT_RATE = 0.02;
    private static final double SILVER_DISCOUNT_RATE = 0.05;
    private static final double GOLD_DISCOUNT_RATE = 0.10;

    private static final String MEMBERSHIP_GOLD = "Gold";
    private static final String MEMBERSHIP_SILVER = "Silver";
    private static final String MEMBERSHIP_REGULAR = "Regular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialEditTextName = findViewById(R.id.materialEditTextName);
        spinnerItems = findViewById(R.id.spinnerItems);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        radioGroup = findViewById(R.id.radioGroup);
        buttonProcess = findViewById(R.id.buttonProcess);
        languageSpinner = findViewById(R.id.languageSpinner);

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        itemPrices = new HashMap<>();
        itemPrices.put("SSG", 12999999);
        itemPrices.put("IPX", 5725300);
        itemPrices.put("PCO", 2730551);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);

        buttonProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    processTransaction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void processTransaction() {
        String name = materialEditTextName.getText().toString().trim();
        String selectedItem = spinnerItems.getSelectedItem().toString();
        String membershipType = getMembershipType();
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());

        int price = itemPrices.get(selectedItem);

        int totalPrice = price * quantity;

        double discountRate = getDiscountRate(membershipType);

        int discount = (int) (totalPrice * discountRate);
        int finalPrice = totalPrice - discount;

        Intent intent = new Intent(MainActivity.this, TransactionDetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("selectedItem", selectedItem);
        intent.putExtra("price", price);
        intent.putExtra("membershipType", membershipType);
        intent.putExtra("quantity", quantity);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("discount", discount);
        intent.putExtra("finalPrice", finalPrice);
        startActivity(intent);
    }

    private String getMembershipType() {
        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        return selectedRadioButton.getText().toString();
    }

    private double getDiscountRate(String membershipType) {
        switch (membershipType) {
            case MEMBERSHIP_GOLD:
                return GOLD_DISCOUNT_RATE;
            case MEMBERSHIP_SILVER:
                return SILVER_DISCOUNT_RATE;
            default:
                return REGULAR_DISCOUNT_RATE;
        }
    }
}

