package com.example.readease3;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ads_and_mean_price extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_and_mean_price);

        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        // Find the NumberPicker
        NumberPicker numberPicker = findViewById(R.id.number_picker);

        // Ανάκτηση του ISBN από το Intent
        String isbn = getIntent().getStringExtra("ISBN");

        // Ανακτήστε τις αγγελίες από τη βάση δεδομένων
        DBHandler dbHandler = new DBHandler(this);
        List<sellingAd> sellingAds = dbHandler.getSellingAdByIsbn(isbn);

        // Υπολογίστε τον μέσο όρο τιμής
        float total = 0;
        for (sellingAd ad : sellingAds) {
            total += ad.getSellingPrice();
        }
        float meanPrice = total / sellingAds.size();

        // Εμφάνιση των δεδομένων στην οθόνη
        TextView meanPriceTextView = findViewById(R.id.mean_price_text_view);
        meanPriceTextView.setText(String.format("Μέση τιμή: %.2f", meanPrice));

        // Ρύθμιση του NumberPicker
        numberPicker.setMinValue(0); // Ελάχιστη τιμή
        numberPicker.setMaxValue(10000); // Μέγιστη τιμή (100.00)
        numberPicker.setValue(Math.round(meanPrice * 100)); // Αρχική τιμή η μέση τιμή σε cents

        numberPicker.setFormatter(value -> String.format("%d,%02d", value / 100, value % 100));

        for (sellingAd ad : sellingAds) {
            // Create a new linear layout for each ad with a black border
            LinearLayout adContainerLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16); // Add margin at the bottom for spacing between ads
            adContainerLayout.setLayoutParams(params);
            adContainerLayout.setOrientation(LinearLayout.VERTICAL);
            adContainerLayout.setPadding(8, 8, 8, 8);
            adContainerLayout.setBackgroundResource(R.drawable.ad_border);

            TextView adTextView = new TextView(this);
            adTextView.setText("Name: " + dbHandler.getUserNameById(ad.getSellingPublisher()) + "\n" + "Condition: " + ad.getSellingStatus() + "\n" + "Price: " + ad.getSellingPrice());
            adContainerLayout.addView(adTextView);

            // Add the container layout to the main layout
            mainLayout.addView(adContainerLayout);
        }
    }
}
