package com.example.readease3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class audio_book extends AppCompatActivity {

    private TextInputEditText isbnEditText;
    private TextInputEditText bookTitleEditText;
    private TextInputEditText pageNumberEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText dateEditText;
    private Button checkButton;
    private Button createButton;
    private Button calculateButton;
    private Button englishButton;
    private Button greekButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.audio_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        isbnEditText = findViewById(R.id.isbn_edit_text);
        bookTitleEditText = findViewById(R.id.book_title_edit_text);
        pageNumberEditText = findViewById(R.id.page_number_edit_text);
        priceEditText = findViewById(R.id.price_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);
        checkButton = findViewById(R.id.check);
        createButton = findViewById(R.id.create);
        calculateButton = findViewById(R.id.calculate);
        englishButton = findViewById(R.id.english);
        greekButton = findViewById(R.id.greek);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = isbnEditText.getText().toString();
                checkIsbn(isbn);
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePrice();
            }
        });
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguageButtonState(englishButton, greekButton);
            }
        });

        greekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguageButtonState(greekButton, englishButton);
            }
        });
    }

    private void checkIsbn(String isbn) {
        SQLiteOpenHelper dbHelper = new DBHandler(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT title, pages FROM book WHERE isbn = ?", new String[]{isbn});
        if (cursor.moveToFirst()) {
            Pair<String, Integer> bookData = getBookData(cursor);
            fillFields(bookData.first, bookData.second);
            unlockFields();
        } else {
            // ISBN not found, unlock fields and show a pop-up message
            unlockFields();

            showNoBookMessage();
        }

        cursor.close();
        db.close();
    }

    private Pair<String, Integer> getBookData(Cursor cursor) {
        int titleIndex = cursor.getColumnIndexOrThrow("title");
        int pagesIndex = cursor.getColumnIndexOrThrow("pages");

        String title = cursor.getString(titleIndex);
        int pages = cursor.getInt(pagesIndex);

        return new Pair<>(title, pages);
    }
    private void fillFields(String title, int pages) {
        bookTitleEditText.setText(title);
        pageNumberEditText.setText(String.valueOf(pages));
    }
    private void unlockFields() {
        bookTitleEditText.setEnabled(true);
        pageNumberEditText.setEnabled(true);
        priceEditText.setEnabled(true);
        dateEditText.setEnabled(true);
        calculateButton.setEnabled(true);
        createButton.setEnabled(true);
        englishButton.setEnabled(true);
        greekButton.setEnabled(true);
    }

    private void calculatePrice() {
        String isbn = isbnEditText.getText().toString();
        if (isbn.isEmpty()) {
            // Show a pop-up message if ISBN field is empty
            new AlertDialog.Builder(this)
                    .setTitle("Σφάλμα")
                    .setMessage("Παρακαλώ εισάγετε το ISBN του βιβλίου.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }

        SQLiteOpenHelper dbHelper = new DBHandler(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT pages FROM book WHERE isbn = ?", new String[]{isbn});
        if (cursor.moveToFirst()) {
            int pagesIndex = cursor.getColumnIndexOrThrow("pages");
            int pages = cursor.getInt(pagesIndex);
            double price = pages * 0.10;
            fillFieldsPrice(price);
        } else {
            showNoBookMessage();
        }

        cursor.close();
        db.close();
    }

    private void fillFieldsPrice(double price) {
        priceEditText.setText(String.format("%.2f", price));
    }

    private void showNoBookMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Σφάλμα")
                .setMessage("Δεν βρέθηκε βιβλίο με το συγκεκριμένο ISBN.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void setLanguageButtonState(Button selectedButton, Button unselectedButton) {
        // Αλλάξτε το χρώμα του επιλεγμένου κουμπιού σε μαύρο και το χρώμα του κειμένου σε άσπρο
        selectedButton.setBackgroundColor(getResources().getColor(R.color.selectedButtonColor));
        selectedButton.setTextColor(getResources().getColor(R.color.white));

        // Αλλάξτε το χρώμα του μη επιλεγμένου κουμπιού στο default και το χρώμα του κειμένου σε μαύρο
        unselectedButton.setBackgroundColor(getResources().getColor(R.color.unselectedButtonColor));
        unselectedButton.setTextColor(getResources().getColor(R.color.black));
    }

}