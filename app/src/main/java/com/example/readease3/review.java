package com.example.readease3;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.readease3.ui.search.search_fragment;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;

import java.util.List;

public class review extends AppCompatActivity {

    private EditText reviewEditText;
    private String searchedBookISBN; // Declare variable to store ISBN
    private int currentReviewId = -1; // Initialize currentReviewId with -1, indicating no review has been submitted yet

    private Button submitButton;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.review);

        // Retrieve the ISBN passed from search_fragment
        searchedBookISBN = getIntent().getStringExtra("searched_book_isbn");

        // Find views
        reviewEditText = findViewById(R.id.editText);
        submitButton = findViewById(R.id.submit1);
        editButton = findViewById(R.id.button9); // ΕΠΕΞΕΡΓΑΣΙΑ button


        storeReview();
        updateReview();

        // Apply window insets listener
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void storeReview(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = reviewEditText.getText().toString().trim();

                if (reviewText.length() > 300) {
                    reviewText = reviewText.substring(0, 300);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (!reviewText.isEmpty()) {
                        // Use the retrieved ISBN in the insertReview method
                        DBHandler dbHandler = new DBHandler(review.this);
                        long insertedReviewId = dbHandler.insertReview(1, reviewText, searchedBookISBN); // Store the inserted review ID
                        currentReviewId = (int) insertedReviewId; // Store the review ID
                        showSuccessfulMessage();
                        // Enable the edit button and disable the submit button
                        editButton.setEnabled(true);
                        submitButton.setEnabled(false);
                    } else {
                        Toast.makeText(review.this, "Πληκτρολόγησε την αξιολόγηση σου", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void updateReview(){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure there's a review to update
                if (currentReviewId != -1) {
                    // Proceed with updating the review
                    // You can use currentReviewId here to update the corresponding review in the database
                    DBHandler dbHandler = new DBHandler(review.this);
                    String updatedReviewText = reviewEditText.getText().toString().trim();

                    if (updatedReviewText.length() > 300) {
                        updatedReviewText = updatedReviewText.substring(0, 300);
                    }

                    if (!updatedReviewText.isEmpty()) {
                        dbHandler.updateReview(currentReviewId, 1, updatedReviewText);
                        showSuccessfulMessage();
                    } else {
                        Toast.makeText(review.this, "Πληκτρολόγησε την νέα αξιολόγηση σου", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(review.this, "Δεν υπάρχει αξιολόγηση προς ανανέωση", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showSuccessfulMessage(){
        Toast.makeText(review.this, "Επιτυχής καταχώρηση εξιολόγησης", Toast.LENGTH_SHORT).show();
    }

}

