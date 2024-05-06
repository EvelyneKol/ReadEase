package com.example.readease3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class available_quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.available_quiz);

        // Set window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Assign quiz names to TextViews
        TextView quiz1 = findViewById(R.id.quiz1);
        TextView quiz2 = findViewById(R.id.quiz2);
        TextView quiz3 = findViewById(R.id.quiz3);

        quiz1.setText("Πόσο καλά ξέρεις τον 'Harry Potter'?");
        quiz2.setText("Πόσο μεγάλος ReadEaser είσαι;");
        quiz3.setText("Μάντεψε τον συγγραφέα των αγαπημένων βιβλίων των Read Easers;");
    }
}
