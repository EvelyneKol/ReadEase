package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class not_enough_capacity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_enough_capacity);

        Button returnButton = findViewById(R.id.returnbutton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the events_screen activity
                Intent intent = new Intent(not_enough_capacity.this, events_screen.class);
                startActivity(intent);
                // Finish the current activity
                finish();
            }
        });
    }
}
