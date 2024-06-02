package com.example.readease3;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


public class ebook_form extends AppCompatActivity {

    private static final int PICK_PDF_FILE = 2;
    private TextView uploadStatusTextView;
    private EditText editTextName, editTitle, editTextPrice, editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ebook_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        uploadStatusTextView = findViewById(R.id.textView12);
        editTextName = findViewById(R.id.editTextName);
        editTitle = findViewById(R.id.edittitle);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editText);
    }

    public void openFilePicker(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_FILE);
    }

    public void showPopupDialog(View view) {
        if (editTextName.getText().toString().isEmpty() || editTitle.getText().toString().isEmpty()|| editTextPrice.getText().toString().isEmpty() || editTextDescription.getText().toString().isEmpty()) {
            Toast.makeText(this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Επιθυμείς να ενημερώνεσαι για τα στατιστικά των πωλήσεων σου;");

        // Set the positive button to open the file picker
        builder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseStatistics();
            }
        });

        builder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ebook_form.this, "Επιτυχής υποβολή αρχείου", Toast.LENGTH_SHORT).show();
                clearFormFields();
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearFormFields() {
        editTextName.getText().clear();
        editTextPrice.getText().clear();
        editTitle.getText().clear();
        editTextDescription.getText().clear();
    }


    private void chooseStatistics() {
        // Create an AlertDialog for the nested dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Διάλεξε μορφή στατιστικών:");

        // Set the positive button to show "Bars"
        builder.setPositiveButton("Με ραβδόγραμμα", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle Bars button click
                Toast.makeText(ebook_form.this, "Επιτυχής υποβολή αρχείου και στατιστικών", Toast.LENGTH_SHORT).show();
                clearFormFields();
            }
        });

        // Set the negative button to show "Pita"
        builder.setNegativeButton("Με πίτα", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle Pita button click
                Toast.makeText(ebook_form.this, "Επιτυχής υποβολή αρχείου και στατιστικών", Toast.LENGTH_SHORT).show();
                clearFormFields();
            }
        });

        // Show the nested dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                // Update the TextView to show that the file is uploaded
                uploadStatusTextView.setText("Το αρχείο αναρτήθηκε");
                uploadStatusTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}