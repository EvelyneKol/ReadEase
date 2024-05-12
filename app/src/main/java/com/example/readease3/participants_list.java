package com.example.readease3;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class participants_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participants_list);
        // Retrieve the event description from the intent extras
        String eventTitle = getIntent().getStringExtra("event_title");
        String eventdes = getIntent().getStringExtra("description");
        String eventStart = getIntent().getStringExtra("start_time");
        String eventEnd = getIntent().getStringExtra("end_time");
        String eventLocation = getIntent().getStringExtra("location");
        String de_script = eventdes +". Η εκδήλωση ξεκινά "+ eventStart + " και τελειώνει " +eventEnd +". Θα μας βείτε στην τοποθεσία "+ eventLocation ;

        // Find textView7 and set its text with the event description
        TextView title = findViewById(R.id.title);
        title.setText(eventTitle);

        TextView description = findViewById(R.id.description);
        description.setText(de_script);

        // Find the LinearLayout inside the ScrollView
        LinearLayout editTextContainer = findViewById(R.id.editTextContainer);

        // Retrieve the number from the intent extras
        int insertedNumber = getIntent().getIntExtra("inserted_number", 0);

        // Set padding between EditTexts
        int paddingBetweenEditTexts = 100;

        // Create and add EditText dynamically based on the insertedNumber
        for (int i = 0; i < insertedNumber; i++) {
            EditText editText = new EditText(this);
            editText.setId(ViewCompat.generateViewId()); // Generate unique ID for EditText
            editText.setHint("  Όνομα " + (i + 1)+ "ου συμμετέχοντα !");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            // Apply top margin to create padding between EditTexts
            params.setMargins(0, paddingBetweenEditTexts, 0, 0);
            editText.setLayoutParams(params);

            // Set background drawable
            editText.setBackgroundResource(R.drawable.edittext_border);

            // Add EditText to LinearLayout inside ScrollView
            editTextContainer.addView(editText);
        }
    }

}
