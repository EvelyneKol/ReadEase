package com.example.readease3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class available_quiz extends AppCompatActivity {

    private ListView listViewQuizzes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_quiz);

        listViewQuizzes = findViewById(R.id.listViewQuizzes);

        try {
            // Get the quiz list from the database
            DBHandler dbHandler = new DBHandler(this);
            List<Quiz> quizList = dbHandler.getQuizList(); // No parameter

            // Extract titles from the Quiz objects
            List<String> quizTitles = new ArrayList<>();
            for (Quiz quiz : quizList) {
                quizTitles.add(quiz.getTitle());
            }

            // Create an ArrayAdapter to show the titles
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quizTitles);

            // Set the adapter to the ListView
            listViewQuizzes.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("available_quiz", "Error in onCreate", e);
        }
    }
}
