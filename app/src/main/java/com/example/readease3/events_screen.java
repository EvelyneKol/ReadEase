package com.example.readease3;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readease3.databinding.EventsScreenBinding;
import com.example.readease3.databinding.SearchBinding;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class events_screen extends AppCompatActivity {
    private DBHandler dbHandler;
    private EventsScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_screen);
        binding = EventsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize DBHandler
        dbHandler = new DBHandler(this);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Search and display events
        searchEvents();
    }

    private void searchEvents() {
        // Perform search query in the database
        List<events> eventsList = dbHandler.returnEventsInfo();

        if (!eventsList.isEmpty()) {
            // Display the first event details
            events event1 = eventsList.get(1);
            String eventDetails1 = event1.getStartTime() + "--" + event1.getEndTime() + "\n" +
                    event1.getTitle();

            // Parse the date string to extract the month and date
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
            try {
                Date date = inputFormat.parse(event1.getDate());
                String eventMonth =dateFormat.format(date) + "\n" +  monthFormat.format(date) ;

                // Update UI with event details
                binding.title1.setText(eventDetails1);
                binding.date1.setText(eventMonth);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Set OnClickListener for Button 11
            binding.button11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if eventsList is not empty
                    if (!eventsList.isEmpty()) {
                        // Get the first event
                        events event1 = eventsList.get(1);
                        // Clear the text of description1
                        binding.description1.setText("");
                        // Set the text of description1 with the date from the first event
                        binding.description1.setText(event1.getDescription() + ". Θα βρισκόμαστε στην τοποθεσία: " + event1.getLocation()+ " Σας περιμένουμε!");
                    }
                }
            });

            binding.button12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if eventsList is not empty
                    if (!eventsList.isEmpty()) {
                        // Get the first event
                        events event1 = eventsList.get(1);
                        // Clear the text of description1
                        binding.description1.setText("");
                        binding.description1.setText("Έχουν μείνει ακόμα: "+event1.getCapacity()+" θέσεις, σας ευχαριστούμαι!");
                    }
                }
            });

            events event2 = eventsList.get(0);
            String eventDetails2 = event2.getStartTime() + "--" + event2.getEndTime() + "\n" +
                    event2.getTitle();
            try {
                Date date2 = inputFormat.parse(event2.getDate());
                String eventMonth2 =dateFormat.format(date2) + "\n" +  monthFormat.format(date2) ;

                // Update UI with event details
                binding.title2.setText(eventDetails2);
                binding.date2.setText(eventMonth2);

            } catch (ParseException e) {
                e.printStackTrace();

            }

            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if eventsList is not empty
                    if (!eventsList.isEmpty()) {
                        // Get the first event
                        events event2 = eventsList.get(0);
                        // Clear the text of description1
                        binding.description2.setText("");
                        // Set the text of description1 with the date from the first event
                        binding.description2.setText(event2.getDescription() + ". Θα βρισκόμαστε στην τοποθεσία: " + event2.getLocation()+ " Σας περιμένουμε!");
                    }
                }
            });

            binding.button14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if eventsList is not empty
                    if (!eventsList.isEmpty()) {
                        // Get the first event
                        events event2 = eventsList.get(0);
                        // Clear the text of description1
                        binding.description2.setText("");
                        binding.description2.setText("Έχουν μείνει ακόμα: "+ event2.getCapacity() +" θέσεις, σας ευχαριστούμαι!");
                    }
                }
            });

        } else {
            // No events found
            binding.noevents.setText("No events found");
        }
    }
}