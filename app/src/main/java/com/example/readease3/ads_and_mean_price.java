package com.example.readease3;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import android.os.Bundle;


public class ads_and_mean_price extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_and_mean_price);

        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);


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

        for (sellingAd ad : sellingAds) {
            // Create a new linear layout for each ad and its button
            LinearLayout adContainerLayout = new LinearLayout(this);
            adContainerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            adContainerLayout.setOrientation(LinearLayout.VERTICAL);
            TextView adTextView = new TextView(this);
            adTextView.setText("Name: " + dbHandler.getUserNameById(ad.getSellingPublisher()) + "\n" + "Condition: " + ad.getSellingStatus() + "\n" + "Price: " + ad.getSellingPrice());
            adContainerLayout.addView(adTextView);

            // Add the container layout to the main layout
            mainLayout.addView(adContainerLayout);
        }


    }
}
