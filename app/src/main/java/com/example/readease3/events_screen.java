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
            events event = eventsList.get(1);
            String eventDetails = "Start Date: " + event.getDateTime() + "\n" +
                    "Title: " + event.getTitle();

            // Update UI with event details
            binding.eventinfo.setText(eventDetails);
        } else {
            // No events found
            binding.eventinfo.setText("No events found.");
        }
    }
}