package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.readease3.databinding.AdDetailsBinding;
import com.example.readease3.ui.cart.cart;


public class ad_details extends AppCompatActivity {

    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ad_details);

        // Initialize DBHandler
        dbHandler = new DBHandler(this);

        // Retrieve ad_id from intent extras
        int adId = getIntent().getIntExtra("ad_id", -1);

        // Display ad details
        displayAdDetails(adId);

        // Find the "Προσθήκη στο καλάθι" button
        Button addToCartButton = findViewById(R.id.addToCartButton);

        // Set OnClickListener for the button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Αποθηκεύουμε τα στοιχεία στο καλάθι όταν πατηθεί το κουμπί
                addToCart(adId);
            }
        });
    }

    private void displayAdDetails(int adId) {
        // Find TextViews and Button in the layout
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView pagesTextView = findViewById(R.id.pagesTextView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        TextView publisherTextView = findViewById(R.id.publisherTextView);
        Button addToCartButton = findViewById(R.id.addToCartButton);

        // Retrieve ad details based on ad_id
        sellingAd adDetails = dbHandler.getAdDetailsByAdId(adId);

        // Display ad details in TextViews
        descriptionTextView.setText("Περίληψη: " + adDetails.getDescription());
        pagesTextView.setText("Σελίδες Βιβλίου: " + adDetails.getPages());
        titleTextView.setText("Τίτλος Βιβλίου: " + adDetails.getTitle());
        priceTextView.setText("Τιμή: " + adDetails.getSellingPrice());
        publisherTextView.setText("Αγγελία από " + adDetails.getPublisherName());



    }
    private void addToCart(int adId) {
        // Λαμβάνουμε τα στοιχεία της αγγελίας από τη βάση δεδομένων
        sellingAd adDetails = dbHandler.getAdDetailsByAdId(adId);

        // Δημιουργούμε ένα νέο αντικείμενο cart με τα στοιχεία της αγγελίας
        Cart newCartItem = new Cart(adDetails.getSellingAdId(), adDetails.getTitle(), adDetails.getSellingPrice());

        // Προσθέτουμε το αντικείμενο στον CartManager
        cartManager.getInstance().addToCart(newCartItem);

        // Εμφάνιση Toast μηνύματος
        Toast.makeText(this, "Το βιβλίο προστέθηκε στο καλάθι", Toast.LENGTH_SHORT).show();
    }


}
