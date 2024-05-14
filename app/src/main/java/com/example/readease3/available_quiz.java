package com.example.readease3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class available_quiz extends AppCompatActivity {

    private LinearLayout availableQuiz;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_quiz);

        availableQuiz = findViewById(R.id.quizLayout); // Assuming you have a LinearLayout with this ID in your XML layout

        try {
            // Get the quiz list from the database
            DBHandler dbHandler = new DBHandler(this);
            List<Quiz> quizList = dbHandler.getQuizList(); // No parameter

            // Add a TextView for each quiz title to the LinearLayout
            for (Quiz quiz : quizList) {
                TextView quizTextView = new TextView(this);
                quizTextView.setText(quiz.getTitle());
                quizTextView.setTextSize(18); // You can adjust the text size
                quizTextView.setPadding(16, 16, 16, 16); // You can adjust the padding
                availableQuiz.addView(quizTextView);
            }
        } catch (Exception e) {
            Log.e("available_quiz", "Error in onCreate", e);
        }
    }
}
