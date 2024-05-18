package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readease3.databinding.RedeemPointsBinding;

public class redeem_points extends AppCompatActivity {

    private RedeemPointsBinding binding;
    private int points;
    private int insertedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Initialize binding
        binding = RedeemPointsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Restore points from savedInstanceState or intent
        if (savedInstanceState != null) {
            points = savedInstanceState.getInt("points");
        } else {
            points = getIntent().getIntExtra("points", 0);
        }

        String redeemPoints = String.valueOf(points);
        binding.points.setText(redeemPoints); // Convert int to String

        checkpoints();
    }

    private void checkpoints() {
        binding.checkpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example of retrieving and converting user input to an integer
                EditText editText = findViewById(R.id.redeempoints);
                String insertedNumberStr = editText.getText().toString();

                // Validate the input before converting it to an integer
                if (!TextUtils.isEmpty(insertedNumberStr)) {
                    insertedNumber = Integer.parseInt(insertedNumberStr);
                    // Now you can use the insertedNumber variable
                    if (insertedNumber >= 50 && insertedNumber <=points) {
                        viewNewcoupons();
                    } else {
                        notEnoughpoints();
                    }
                }
            }
        });
    }

    private void viewNewcoupons() {
        Intent checkout = new Intent(redeem_points.this, select_coupons.class);
        // Pass the event description as an extra with the intent
        checkout.putExtra("redeempoints", insertedNumber);
        checkout.putExtra("points", points);
        startActivity(checkout);
    }

    private void notEnoughpoints() {
        Intent notEnoughpoints = new Intent(redeem_points.this, not_enough_points.class);
        // Pass the current points as an extra with the intent
        notEnoughpoints.putExtra("points", points);
        startActivity(notEnoughpoints);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current points
        outState.putInt("points", points);
    }
}
