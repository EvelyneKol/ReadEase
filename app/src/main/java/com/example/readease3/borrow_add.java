package com.example.readease3;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class borrow_add extends AppCompatActivity {
    private TextInputEditText isbnEditText;
    private TextInputEditText bookTitleEditText;
    private TextInputEditText pageNumberEditText;
    private TextInputEditText startDateEditText;
    private TextInputEditText endDateEditText;
    private RadioGroup radioGroup;
    private Button checkButton;
    private Button createButton;
    private ImageView calendarImageView;
    private ImageView calendarImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.borrow_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        isbnEditText = findViewById(R.id.isbn_edit_text);
        bookTitleEditText = findViewById(R.id.book_title_edit_text);
        pageNumberEditText = findViewById(R.id.page_number_edit_text);
        startDateEditText = findViewById(R.id.start_date_edit_text);
        endDateEditText = findViewById(R.id.end_date_edit_text);
        radioGroup = findViewById(R.id.radioGroup);
        checkButton = findViewById(R.id.check);
        createButton = findViewById(R.id.create);
        calendarImageView = findViewById(R.id.calendarImageView);
        calendarImageView2 = findViewById(R.id.calendarImageView2);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = isbnEditText.getText().toString();
                checkIsbn(isbn);
            }
        });

        calendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateEditText);
            }
        });

        calendarImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDateEditText);
            }
        });

        // Adding OnClickListener to createButton
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a message when the button is clicked
                showMessage();
            }
        });
    }

    private void showDatePickerDialog(final TextInputEditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                borrow_add.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
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
            radioGroup.setEnabled(true);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(true);
            }
            createButton.setEnabled(true);
        } else {
            // ISBN not found, unlock fields and show a pop-up message
            bookTitleEditText.setEnabled(true);
            pageNumberEditText.setEnabled(true);
            radioGroup.setEnabled(true);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(true);
            }
            createButton.setEnabled(true);

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

    // Method to show a message when createButton is clicked
    private void showMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Η αγγελία καταχωρήθηκε.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}