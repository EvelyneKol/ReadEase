package com.example.readease3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;


public class ad_options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ad_options);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goToSellingAdd(View view) {
        // Create an Intent to start the SellingAddActivity
        Intent intent = new Intent(this, selling_add.class);
        startActivity(intent);
    }

    public void goToBorrowAdd(View view) {
        // Create an Intent to start the SellingAddActivity
        Intent intent = new Intent(this, borrow_add.class);
        startActivity(intent);
    }
    public void gotoQuiz(View view) {
        // Create an Intent to start the SellingAddActivity
        Intent intent = new Intent(this, available_quiz.class);
        startActivity(intent);
    }
}
