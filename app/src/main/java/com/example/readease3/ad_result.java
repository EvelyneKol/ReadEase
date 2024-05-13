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
import androidx.navigation.Navigation;

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

        // Retrieve ISBN from intent extras
        String searchedBookISBN = getIntent().getStringExtra("searched_book_isbn");

        // Display selling ads
        displaySellingAdsWithSeller(searchedBookISBN);
    }

    private void displaySellingAdsWithSeller(String isbn) {
        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        // Retrieve selling ads with seller's name
        List<sellingAd> sellingAds = dbHandler.getSellingAdByIsbn(isbn);

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
            // Set a click listener for the button
            adButton.setOnClickListener(v -> {
                // Pass the ad ID to the ad_details activity
                Intent intent = new Intent(ad_result.this, ad_details.class);
                intent.putExtra("ad_id", ad.getSellingAdId());

                startActivity(intent);


            });
            adContainerLayout.addView(adButton);

            // Add the container layout to the main layout
            mainLayout.addView(adContainerLayout);
        }
    }
}
