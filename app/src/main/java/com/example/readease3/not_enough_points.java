package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class not_enough_points extends AppCompatActivity {

    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.not_enough_points);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the points from the intent
        points = getIntent().getIntExtra("points", 0);

        Button returnButton = findViewById(R.id.returnbutton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the redeem_points activity and pass the points back
                Intent intent = new Intent(not_enough_points.this, redeem_points.class);
                intent.putExtra("points", points);
                startActivity(intent);
                // Finish the current activity
                finish();
            }
        });
    }
}
