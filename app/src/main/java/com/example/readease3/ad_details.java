package com.example.readease3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ad_details extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ad_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DBHandler
        dbHandler = new DBHandler(this);

        // Retrieve ad_id from intent extras
        int adId = getIntent().getIntExtra("ad_id", -1);

        // Display ad details
        displayAdDetails(adId);
    }

    private void displayAdDetails(int adId) {
        // Find TextViews in the layout
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView pagesTextView = findViewById(R.id.pagesTextView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        TextView publisherTextView = findViewById(R.id.publisherTextView);
        Button addToCartButton = findViewById(R.id.addToCartButton);


        // Retrieve ad details based on ad_id
        AdDetails adDetails = dbHandler.getAdDetailsByAdId(adId);

        // Display ad details in TextViews
        descriptionTextView.setText("Περίληψη: " + adDetails.getDescription());
        pagesTextView.setText("Σελίδες Βιβλίου: " + adDetails.getPages());
        titleTextView.setText("Τίτλος Βιβλίου: " + adDetails.getTitle());
        priceTextView.setText("Τιμή: " + adDetails.getPrice());
        publisherTextView.setText("Αγγελία από " + adDetails.getPublisherName());


    }
}
