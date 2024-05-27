package com.example.readease3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
public class selling_add extends AppCompatActivity {

    private TextInputEditText isbnEditText;
    private TextInputEditText bookTitleEditText;
    private TextInputEditText pageNumberEditText;
    private TextInputEditText valueEditText;
    private RadioGroup radioGroup;
    private Button checkButton;
    private Button createButton;
    private Button priceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.selling_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        isbnEditText = findViewById(R.id.isbn_edit_text);
        bookTitleEditText = findViewById(R.id.book_title_edit_text);
        pageNumberEditText = findViewById(R.id.page_number_edit_text);
        valueEditText = findViewById(R.id.value_edit_text);
        radioGroup = findViewById(R.id.radioGroup);
        checkButton = findViewById(R.id.check);
        createButton = findViewById(R.id.create);
        priceButton = findViewById(R.id.price);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = isbnEditText.getText().toString();
                checkIsbn(isbn);
            }
        });

        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdsAndMeanPrice();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = isbnEditText.getText().toString();
                float price = Float.parseFloat(valueEditText.getText().toString());
                String status = getStatusFromRadioButton();
                int publisher = 1; // Ορίζουμε τον εκδότη ως 1

                // Εισαγωγή νέας αγγελίας στη βάση δεδομένων
                SQLiteOpenHelper dbHelper = new DBHandler(selling_add.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ((DBHandler) dbHelper).insertSellingAd(db, isbn, price, publisher, status);

                // Εμφάνιση μηνύματος επιτυχίας με Toast
                Toast.makeText(selling_add.this, "Η αγγελία καταχωρήθηκε με επιτυχία.", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void checkIsbn(String isbn) {
        SQLiteOpenHelper dbHelper = new DBHandler(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT title, pages FROM book WHERE isbn = ?", new String[]{isbn});
        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndexOrThrow("title");
            int pagesIndex = cursor.getColumnIndexOrThrow("pages");

            String title = cursor.getString(titleIndex);
            int pages = cursor.getInt(pagesIndex);

            bookTitleEditText.setText(title);
            pageNumberEditText.setText(String.valueOf(pages));

            bookTitleEditText.setEnabled(true);
            pageNumberEditText.setEnabled(true);
            valueEditText.setEnabled(true);
            radioGroup.setEnabled(true);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(true);
            }
            createButton.setEnabled(true);
            priceButton.setEnabled(true);
        } else {
            // ISBN not found, unlock fields and show a pop-up message
            bookTitleEditText.setEnabled(true);
            pageNumberEditText.setEnabled(true);
            valueEditText.setEnabled(true);
            radioGroup.setEnabled(true);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(true);
            }
            createButton.setEnabled(true);
            priceButton.setEnabled(true);
            // Show the pop-up message
            new AlertDialog.Builder(this)
                    .setTitle("Πληροφορία")
                    .setMessage("Το βιβλίο δεν υπάρχει στην βάση, συμπληρώστε τα στοιχεία του.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

        cursor.close();
        db.close();
    }


    private boolean checkIsbnExistsInSellingAd(String isbn) {
        // Εκτελούμε τον κώδικα για να ελέγξουμε αν το ISBN υπάρχει στον πίνακα selling_ad
        SQLiteOpenHelper dbHelper = new DBHandler(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Ορίζουμε το ερώτημα για τον έλεγχο της ύπαρξης του ISBN στον πίνακα selling_ad
        String query = "SELECT * FROM selling_ad WHERE selling_ad_isbn = ?";
        Cursor cursor = db.rawQuery(query, new String[]{isbn});

        // Εάν το cursor δεν είναι κενό, το ISBN υπάρχει στον πίνακα selling_ad
        boolean isbnExists = cursor.moveToFirst();

        // Κλείνουμε το cursor και τη σύνδεση με τη βάση
        cursor.close();
        db.close();

        // Επιστρέφουμε true εάν το ISBN υπάρχει, αλλιώς false
        return isbnExists;
    }

    private void showNoAdsMessage() {
        // Δημιουργούμε ένα AlertDialog για να εμφανίσουμε το μήνυμα
        new AlertDialog.Builder(selling_add.this)
                .setTitle("Πληροφορία")
                .setMessage("Δεν υπάρχουν αγγελίες για αυτό το βιβλίο στη βάση.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void showAdsAndMeanPrice() {
        String isbn = isbnEditText.getText().toString();
        String status = getStatusFromRadioButton(); // Λάβετε την κατάσταση από τα RadioButtons

        if (!isbn.isEmpty()) {
            // Ελέγξτε αν υπάρχει το ISBN στον πίνακα selling_ad
            boolean isbnExists = checkIsbnExistsInSellingAd(isbn);
            if (isbnExists) {
                // Αν υπάρχει, μεταβείτε στη σελίδα ads_and_mean_price και περάστε τα δεδομένα
                Intent intent = new Intent(selling_add.this, ads_and_mean_price.class);
                intent.putExtra("ISBN", isbn);
                intent.putExtra("STATUS", status); // Περάστε την κατάσταση στο intent
                startActivity(intent);
            } else {
                showNoAdsMessage();
            }
        } else {
            // Αν το ISBN είναι κενό, εμφανίστε μήνυμα
            new AlertDialog.Builder(selling_add.this)
                    .setTitle("Πληροφορία")
                    .setMessage("Παρακαλώ εισάγετε ISBN πριν συνεχίσετε.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    private String getStatusFromRadioButton() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        } else {
            return ""; // ή κάποια προκαθορισμένη τιμή αν δεν έχει επιλεγεί κάποιο RadioButton
        }

    }
}