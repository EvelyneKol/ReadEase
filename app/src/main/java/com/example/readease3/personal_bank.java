package com.example.readease3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readease3.databinding.EventsScreenBinding;
import com.example.readease3.databinding.PersonalBankBinding;

import java.util.List;

public class personal_bank extends AppCompatActivity {
    private DBHandler dbHandler;
    private PersonalBankBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.personal_bank);
        binding = PersonalBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        dbHandler = new DBHandler(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Search and display coupons
        //Coupons();
        // You may call getPoints() here if needed
    }

    /*@SuppressLint("SetTextI18n")
    private void Coupons() {
        List<coupons> couponsList = dbHandler.returnCoupons();

        if (!couponsList.isEmpty()) {
            coupons coupon = couponsList.get(0);
            String eventDetails1 = coupon.getCoupontype();
            binding.textView7.setText(eventDetails1);
        } else {
            binding.textView7.setText("No events found");
        }
    }*/
}
