package com.example.readease3;

import android.content.Intent;
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


public class ad_details_borrow extends AppCompatActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ad_details_borrow);
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
        // Προσθήκη ακροατή γεγονότος στο κουμπί "calendar"
        // Προσθήκη ακροατή γεγονότος στο κουμπί "calendar"
        Button calendar = findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Εκτέλεση του Intent για την calendar_screen με το ad_id ως extra
                Intent intent = new Intent(ad_details_borrow.this, calendar_screen.class);
                intent.putExtra("ad_id", adId); // Προσθήκη του ad_id ως extra
                startActivity(intent);
            }
        });
    }


    private void displayAdDetails(int adId) {
        // Find TextViews in the layout
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView pagesTextView = findViewById(R.id.pagesTextView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        TextView publisherTextView = findViewById(R.id.publisherTextView);
        Button calendar = findViewById(R.id.calendar);


        // Retrieve ad details based on ad_id
        adDetailsBorrow adDetailsBorrow = dbHandler.getAdDetailsBorrow(adId);

        // Display ad details in TextViews
        descriptionTextView.setText("Περίληψη: " + adDetailsBorrow.getDescription());
        pagesTextView.setText("Σελίδες Βιβλίου: " + adDetailsBorrow.getPages());
        titleTextView.setText("Τίτλος Βιβλίου: " + adDetailsBorrow.getTitle());
        priceTextView.setText("Τοποθεσια: " + adDetailsBorrow.getLocation());
        publisherTextView.setText("Αγγελία από " + adDetailsBorrow. getBorrowPublisher());


    }
}
