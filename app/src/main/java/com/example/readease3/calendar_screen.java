package com.example.readease3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import  java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import  java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;
import java.util.Calendar;

public class calendar_screen extends AppCompatActivity {

    private TextView startDateTextView;
    private TextView endDateTextView;
    private DBHandler dbHandler;
    private Button borrowButton; // Προσθήκη αναφοράς στο κουμπί δανεισμού

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_screen);

        Intent intent = getIntent();

        borrowButton = findViewById(R.id.borrow);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        dbHandler = new DBHandler(this);

        // Show start date picker dialog immediately when the activity is created
        showStartDatePickerDialog();
    }

    private void showStartDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog startDatePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth1) -> {
                    String startDate = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    startDateTextView.setText("Εναρξη Δανεισμού: " + startDate);
                    showEndDatePickerDialog(); // Show end date picker dialog after setting start date
                }, year, month, dayOfMonth);

        startDatePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth1) -> {
                    String endDate = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    endDateTextView.setText("Λήξη Δανεισμού: " + endDate);
                    int adId = getIntent().getIntExtra("ad_id", -1);

                    // Check book availability
                    if (startDateTextView.getText() != null && !startDateTextView.getText().toString().isEmpty()) {
                        String startDate = startDateTextView.getText().toString().replace("Start Date: ", "");
                        if (checkBookAvailability(adId, startDate, endDate)) { // Χρήση της μεθόδου που ανακτά τις ημερομηνίες από τη βάση
                            Toast.makeText(calendar_screen.this, "Το βιβλίο είναι διαθέσιμο για δανεισμό!", Toast.LENGTH_SHORT).show();
                            borrowButton.setEnabled(true);

                            // Εγγραφή δανεισμού
                            registerBorrow(adId);
                        } else {
                            showNotAvailableMessage();
                        }
                    } else {
                        Toast.makeText(calendar_screen.this, "Παρακαλώ ορίστε την ημερομηνία έναρξης πρώτα!", Toast.LENGTH_SHORT).show();
                    }
                }, year, month, dayOfMonth);

        endDatePickerDialog.show();
    }
    private void registerBorrow(int adId) {
        borrowButton.setOnClickListener(v -> {
            // Πάρτε τις ημερομηνίες που επιλέχθηκαν
            String startDateStr = startDateTextView.getText().toString().replace("Start Date: ", "");
            String endDateStr = endDateTextView.getText().toString().replace("End Date: ", "");

            // Εισάγετε τα δεδομένα στον πίνακα Borrow
            if (startDateStr != null && endDateStr != null) {
                dbHandler.insertBorrow(adId, startDateStr, endDateStr);
                Toast.makeText(calendar_screen.this, "Επιτυχής δανεισμός!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(calendar_screen.this, "Παρουσιάστηκε σφάλμα κατά την εισαγωγή των δεδομένων!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotAvailableMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(calendar_screen.this);
        builder.setTitle("Μη διαθέσιμο βιβλίο")
                .setMessage("Το βιβλίο δεν είναι διαθέσιμο για δανεισμό σε αυτή την περίοδο. Επιλέξτε άλλες Ημερομηνίες")
                .setPositiveButton("Εντάξει", (dialog, which) -> {
                    dialog.dismiss();
                    showStartDatePickerDialog(); // Καλείτε την μέθοδο όταν πατηθεί το "Εντάξει"
                })
                .create().show();
    }



    // Κώδικας για τη μέθοδο checkBookAvailability στην calendar_screen
    private boolean checkBookAvailability(int borrowId, String startDateStr, String endDateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Ανακτήστε τις ημερομηνίες από τη βάση δεδομένων
            String dbStartDateStr = dbHandler.getStartDateFromBorrowId(borrowId);
            String dbEndDateStr = dbHandler.getEndDateFromBorrowId(borrowId);

            // Μετατροπή των ημερομηνιών από τη βάση σε αντικείμενα Date
            Date dbStartDate = format.parse(dbStartDateStr);
            Date dbEndDate = format.parse(dbEndDateStr);

            // Μετατροπή των ημερομηνιών που εισήγαγε ο χρήστης σε αντικείμενα Date
            Date userStartDate = format.parse(startDateStr);
            Date userEndDate = format.parse(endDateStr);

            // Εδώ μπορείτε να κάνετε τη σύγκριση των ημερομηνιών και να επιστρέψετε το αποτέλεσμα
            // Προσοχή: Αυτό είναι ένα παράδειγμα. Πρέπει να προσαρμόσετε τη λογική σύμφωνα με την εφαρμογή σας
            return dbStartDate.compareTo(userEndDate) <= 0 && dbEndDate.compareTo(userStartDate) >= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            // Εάν υπάρξει σφάλμα κατά τη μετατροπή των ημερομηνιών, επιστρέφουμε false
            return false;
        }
    }

}