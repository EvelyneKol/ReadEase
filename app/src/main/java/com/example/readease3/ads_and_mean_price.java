package com.example.readease3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ads_and_mean_price extends AppCompatActivity {
    private Button createButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_and_mean_price);

        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);


        // Λάβετε τα δεδομένα από το intent
        String isbn = getIntent().getStringExtra("ISBN");
        String status = getIntent().getStringExtra("STATUS");


        // Καλέστε τη μέθοδο getAds
        getAds(isbn);

    }
    private void getAds(String isbn) {
        // Find the main LinearLayout container
        LinearLayout mainLayout = findViewById(R.id.mainLayout);

        // Find the NumberPicker
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        createButton = findViewById(R.id.create);
        // Ανακτήστε τις αγγελίες από τη βάση δεδομένων
        DBHandler dbHandler = new DBHandler(this);
        List<sellingAd> sellingAds = dbHandler.getSellingAdByIsbn(isbn);

        // Υπολογίστε τον μέσο όρο τιμής
        float meanPrice = calcMeanPrice(sellingAds);


        // Εμφάνιση των δεδομένων στην οθόνη
        TextView meanPriceTextView = findViewById(R.id.mean_price_text_view);
        meanPriceTextView.setText(String.format("Μέση τιμή: %.2f", meanPrice));

        // Ρύθμιση του NumberPicker
        numberPicker.setMinValue(0); // Ελάχιστη τιμή
        numberPicker.setMaxValue(10000); // Μέγιστη τιμή (100.00)
        numberPicker.setValue(Math.round(meanPrice * 100)); // Αρχική τιμή η μέση τιμή σε cents
        numberPicker.setFormatter(value -> String.format("%d,%02d", value / 100, value % 100));

        for (sellingAd ad : sellingAds) {
            // Create a new linear layout for each ad with a black border
            LinearLayout adContainerLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16); // Add margin at the bottom for spacing between ads
            adContainerLayout.setLayoutParams(params);
            adContainerLayout.setOrientation(LinearLayout.VERTICAL);
            adContainerLayout.setPadding(8, 8, 8, 8);
            adContainerLayout.setBackgroundResource(R.drawable.ad_border);

            TextView adTextView = new TextView(this);
            adTextView.setText("Όνομα Κτόχου: " + dbHandler.getUserNameById(ad.getSellingPublisher()) + "\n" + "Κατάσταση: " + ad.getSellingStatus() + "\n" + "Τιμή: " + ad.getSellingPrice());
            adContainerLayout.addView(adTextView);

            // Add the container layout to the main layout
            mainLayout.addView(adContainerLayout);
        }
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = getIntent().getStringExtra("ISBN");
                float price = numberPicker.getValue() / 100.0f; // Μετατροπή της τιμής σε float
                int publisher = 1; // Προσωρινή τιμή για τον publisher, ανάλογα με τη λογική της εφαρμογής σας
                String status = "ΚΑΛΗ"; // Προσωρινή τιμή για την κατάσταση, ανάλογα με τη λογική της εφαρμογής σας

                // Εισαγωγή νέας αγγελίας στη βάση δεδομένων
                registerSellingAd(isbn, price, publisher, status);

            }
        });
    }

    private float calcMeanPrice(List<sellingAd> sellingAds) {
        float total = 0;
        for (sellingAd ad : sellingAds) {
            total += ad.getSellingPrice();
        }
        return total / sellingAds.size();
    }

    private void registerSellingAd(String isbn, float price, int publisher, String status) {
        // Εισαγωγή νέας αγγελίας στη βάση δεδομένων
        SQLiteOpenHelper dbHelper = new DBHandler(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ((DBHandler) dbHelper).insertSellingAd(db, isbn, price, publisher, status);

        // Εμφάνιση μηνύματος επιτυχίας με Toast
        Toast.makeText(this, "Η αγγελία καταχωρήθηκε με επιτυχία.", Toast.LENGTH_SHORT).show();
    }
}
