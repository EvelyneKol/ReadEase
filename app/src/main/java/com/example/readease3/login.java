package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.example.readease3.ui.home.user_home_fragment;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the login button
        Button loginButton = findViewById(R.id.button10);

        // Set OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the user home page activity
                Intent intent = new Intent(login.this, user_home_fragment.class);
                Navigation.findNavController(v).navigate(R.id.action_login_to_user_home);
                startActivity(intent);
                finish(); // Optional: Finish the login activity to prevent going back to it when pressing back button
            }
        });
    }
}
