package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readease3.databinding.PersonalBankBinding;

import java.util.List;

public class personal_bank extends AppCompatActivity {
    private DBHandler dbHandler;
    private PersonalBankBinding binding;
    private LinearLayout textViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = PersonalBankBinding.inflate(getLayoutInflater()); // Initialize binding
        setContentView(binding.getRoot()); // Use binding root view
        dbHandler = new DBHandler(this);
        textViewContainer = findViewById(R.id.textViewContainer);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Search and display coupons
        getCoupons();
        getpoints();
        viewRedeemsteps();
    }


    private void getCoupons() {
        List<coupons> couponsList = dbHandler.returnCoupons();

        if (!couponsList.isEmpty()) {
            // Clear existing views before adding new ones
            textViewContainer.removeAllViews();

            for (int i = 0; i < couponsList.size(); i++) {
                coupons coupon = couponsList.get(i);
                TextView textView = new TextView(this);
                textView.setId(ViewCompat.generateViewId()); // Generate unique ID for TextView
                textView.setText(coupon.getCoupontype());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textView.setPadding(20, 50, 20, 50);
                // Apply top margin to create padding between TextViews
                params.setMargins(150, 100, 150, 0);
                textView.setLayoutParams(params);

                // Set the background drawable
                textView.setBackgroundResource(R.drawable.textview_border);

                // Set text gravity to center
                textView.setGravity(Gravity.CENTER);

                // Add TextView to LinearLayout
                textViewContainer.addView(textView);
            }
        } else {
            // Handle case where no coupons are found
            // For example, you can display a message or hide a container
        }
    }


    private void getpoints() {
        String userName = "Γιώργος Παπαδόπουλος";

        // Call the method to get user points by name
        int points = dbHandler.getUserPointsByname(userName);

        // Set the points text to the TextView
        binding.textView7.setText(String.valueOf(points)); // Convert int to String
    }

    public void gotoQuiz(View view) {
        // Create an Intent to start the SellingAddActivity
        Intent intent = new Intent(this, available_quiz.class);
        startActivity(intent);
    }

    private void viewRedeemsteps() {
        binding.redeempoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = "John Doe";
                // Call the method to get user points by name
                int points = dbHandler.getUserPointsByname(userName);

                Intent redeemsteps = new Intent(personal_bank.this, redeem_points.class);
                // Pass the event description as an extra with the intent
                redeemsteps.putExtra("points", points);
                startActivity(redeemsteps);
            }
        });
    }



}
