package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ad_result extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ad_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DBHandler
        dbHandler = new DBHandler(this);

        // Retrieve action and ISBN from intent extras
        String action = getIntent().getStringExtra("action");
        String searchedBookISBN = getIntent().getStringExtra("searched_book_isbn");

        // Display ads based on the action
        if ("buy".equals(action)) {
            adExists(searchedBookISBN);
        } else if ("borrow".equals(action)) {
            displayBorrowAds(searchedBookISBN);
        }
    }

    private void adExists(String isbn) {
        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        // Retrieve selling ads with seller's name
        List<sellingAd> sellingAds = dbHandler.getSellingAdByIsbn(isbn);

        if (sellingAds.isEmpty()) {
            showNoAdsMessage(mainLayout);
        } else {
            getAds(sellingAds, mainLayout);
        }
    }



    private void getAds(List<sellingAd> sellingAds, LinearLayout mainLayout) {
        // Create a container layout for each ad and its button
        for (sellingAd ad : sellingAds) {
            // Create a new linear layout for each ad and its button
            LinearLayout adContainerLayout = new LinearLayout(this);
            adContainerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            adContainerLayout.setOrientation(LinearLayout.VERTICAL);

            // Create a text view for the ad
            TextView adTextView = new TextView(this);
            adTextView.setText("Name: " + dbHandler.getUserNameById(ad.getSellingPublisher()) + "\n" + "Condition: " + ad.getSellingStatus() + "\n" + "Price: " + ad.getSellingPrice());
            adContainerLayout.addView(adTextView);

            // Create a button for the ad
            Button adButton = new Button(this);
            adButton.setText("Δες αγγελία");
            // Set the click listener for the button
            showAdDetails(adButton, ad);

            adContainerLayout.addView(adButton);

            // Add the container layout to the main layout
            mainLayout.addView(adContainerLayout);
        }
    }
    private void showAdDetails(Button adButton, sellingAd ad) {
        adButton.setOnClickListener(v -> {
            // Pass the ad ID to the ad_details activity
            Intent intent = new Intent(ad_result.this, ad_details.class);
            intent.putExtra("ad_id", ad.getSellingAdId());
            startActivity(intent);
        });
    }
    private void displayBorrowAds (String isbn) {
        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        // Retrieve selling ads with seller's name
        List<BorrowAd> borrowAds = dbHandler.getBorrowAdByIsbn(isbn);

        if (borrowAds.isEmpty()) {
            // If there are no selling ads for this ISBN, display a message
            TextView messageTextView = new TextView(this);
            messageTextView.setText("Δεν υπάρχουν αγγελίες για αυτό το βιβλίο");
            mainLayout.addView(messageTextView);
        } else {
            // Create a container layout for each ad and its button
            for (BorrowAd ad : borrowAds) {
                // Create a new linear layout for each ad and its button
                LinearLayout adContainerLayout = new LinearLayout(this);
                adContainerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                adContainerLayout.setOrientation(LinearLayout.VERTICAL);

                // Create a text view for the ad
                TextView adTextView = new TextView(this);
                adTextView.setText("Name: " + dbHandler.getUserNameById(ad.getBorrowPublisher()) + "\n" + "Condition: " + ad.getBorrowStatus() + "\n" + "Location: " + ad.getBorrowLocation());
                adContainerLayout.addView(adTextView);

                // Create a button for the ad
                Button adButton = new Button(this);
                adButton.setText("Δες αγγελία");
                // Set a click listener for the button
                adButton.setOnClickListener(v -> {
                    // Pass the ad ID to the ad_details activity
                    Intent intent = new Intent(ad_result.this, ad_details_borrow.class);
                    intent.putExtra("ad_id", ad.getBorrowAdId());
                    startActivity(intent);
                });
                adContainerLayout.addView(adButton);

                // Add the container layout to the main layout
                mainLayout.addView(adContainerLayout);
            }
        }
    }
    private void showNoAdsMessage(LinearLayout mainLayout) {
        // If there are no selling ads for this ISBN, display a message
        TextView messageTextView = new TextView(this);
        messageTextView.setText("Δεν υπάρχουν αγγελίες για αυτό το βιβλίο");
        mainLayout.addView(messageTextView);
    }

}
