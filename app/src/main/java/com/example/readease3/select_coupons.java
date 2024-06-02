package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;

import com.example.readease3.databinding.SelectCouponsBinding;

public class select_coupons extends AppCompatActivity {
    private DBHandler dbHandler;
    private SelectCouponsBinding binding;
    private RadioButton lastCheckedRadioButton = null;
    private int redeempoints;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Initialize binding
        binding = SelectCouponsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database handler
        dbHandler = new DBHandler(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupRadioButtons();
        redeem();
    }

    private void setupRadioButtons() {
        RadioButton radioButton1 = findViewById(R.id.radioButton1);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);
        RadioButton radioButton3 = findViewById(R.id.radioButton3);

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton clickedRadioButton = (RadioButton) v;

                if (lastCheckedRadioButton != null && lastCheckedRadioButton == clickedRadioButton) {
                    clickedRadioButton.setChecked(false);
                    lastCheckedRadioButton = null;
                } else {
                    if (lastCheckedRadioButton != null) {
                        lastCheckedRadioButton.setChecked(false);
                    }
                    clickedRadioButton.setChecked(true);
                    lastCheckedRadioButton = clickedRadioButton;
                }
            }
        };

        radioButton1.setOnClickListener(radioButtonClickListener);
        radioButton2.setOnClickListener(radioButtonClickListener);
        radioButton3.setOnClickListener(radioButtonClickListener);
    }

    private void redeem() {
        binding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedRadioButton != null) {
                    if (lastCheckedRadioButton.getId() == R.id.radioButton1) {
                        Toast.makeText(select_coupons.this, "To 1o κουπόνι επιλέγχθηκε!", Toast.LENGTH_SHORT).show();
                        // Perform actions for RadioButton 1
                        String type="50% Έκπτωση σε αγορά άνω των 30€";
                        setNewcoupon(type);
                        updatePoints();
                        returnhome();
                    } else if (lastCheckedRadioButton.getId() == R.id.radioButton2) {
                        Toast.makeText(select_coupons.this, "To 2o κουπόνι επιλέγχθηκε!", Toast.LENGTH_SHORT).show();
                        // Perform actions for RadioButton 2
                        String type="Δωρεάν μεταφορικά σε αγορά άνω των 25€";
                        setNewcoupon(type);
                        updatePoints();
                        returnhome();
                    } else if (lastCheckedRadioButton.getId() == R.id.radioButton3) {
                        Toast.makeText(select_coupons.this, "To 3o κουπόνι επιλέγχθηκε!", Toast.LENGTH_SHORT).show();
                        // Perform actions for RadioButton 3
                        String type="Εξαργύρωση 1.5€ σε οποιαδήποτε παραγγελία";
                        setNewcoupon(type);
                        updatePoints();
                        returnhome();
                    }
                } else {
                    Toast.makeText(select_coupons.this, "Επιλέξτε ένα κουπόνι παρακαλώ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setNewcoupon(String type) {
        // Example usage
        String userName = "Γιώργος Παπαδόπουλος";
        String expiredDate = "2024-10-30";
        String used = "ΟΧΙ";

        // Insert a new coupon
        dbHandler.updatecoupons(type, userName, expiredDate, used);
        Toast.makeText(select_coupons.this, "Το νέο κουπόνι προστέθηκε", Toast.LENGTH_SHORT).show();
    }

    private void updatePoints() {
        // Example usage
        redeempoints = getIntent().getIntExtra("redeempoints", 0);
        points = getIntent().getIntExtra("points", 0);
        int total= points-redeempoints;
        //int total= 1000;
        String userName = "Γιώργος Παπαδόπουλος";

        // Update points for user
        dbHandler.updateUserPoints(userName, total);
        Toast.makeText(select_coupons.this, "Οι πόντοι ενημερώθηκαν", Toast.LENGTH_SHORT).show();
    }

    private void returnhome() {
        Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
                .navigate(R.id.navigation_notifications);
    }



}
