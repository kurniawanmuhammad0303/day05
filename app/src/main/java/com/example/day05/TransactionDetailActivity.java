package com.example.day05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class TransactionDetailActivity extends AppCompatActivity {

    private TextView textViewDetails; // Deklarasikan textViewDetails sebagai variabel kelas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytransactiondetail);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewDetails = findViewById(R.id.textViewDetails); // Tetapkan textViewDetails di sini
        TextView textViewThankYou = findViewById(R.id.textViewThankYou); // Tambahkan deklarasi untuk textViewThankYou
        Button buttonShare = findViewById(R.id.buttonShare);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String selectedItem = intent.getStringExtra("selectedItem");
            int price = intent.getIntExtra("price", 0);
            String membershipType = intent.getStringExtra("membershipType");
            int totalPrice = intent.getIntExtra("totalPrice", 0);
            int discount = intent.getIntExtra("discount", 0);
            int finalPrice = intent.getIntExtra("finalPrice", 0);

            DecimalFormat formatter = new DecimalFormat("#,###");
            String formattedPrice = formatter.format(price);
            String formattedTotalPrice = formatter.format(totalPrice);
            String formattedDiscount = formatter.format(discount);
            String formattedFinalPrice = formatter.format(finalPrice);

            // Strings for different languages
            String welcomeMessageEnglish = "Welcome, ";
            String welcomeMessageArabic = "مرحبًا، ";
            String thankYouMessageEnglish = "Thank you!";
            String thankYouMessageArabic = "شكرا لك!";
            String transactionDetailEnglish = "Transaction Detail:";
            String transactionDetailArabic = "تفاصيل العملية:";

            String message = "Customer Name: " + name +
                    "\nMembership Type: " + membershipType +
                    "\nItem Code: " + selectedItem +
                    "\nItem Price: Rp " + formattedPrice +
                    "\nTotal Price: Rp " + formattedTotalPrice +
                    "\nDiscount: Rp " + formattedDiscount +
                    "\nFinal Price: Rp " + formattedFinalPrice;

            textViewWelcome.setText(welcomeMessageEnglish + name + "!");
            textViewDetails.setText(transactionDetailEnglish + "\n" + message);
            textViewThankYou.setText(thankYouMessageEnglish);
        }

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTransactionDetails();
            }
        });
    }

    private void shareTransactionDetails() {
        if (textViewDetails != null) { // Periksa jika textViewDetails tidak null
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textViewDetails.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
}
