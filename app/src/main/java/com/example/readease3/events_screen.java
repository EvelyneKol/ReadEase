package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        Eventinfo();
    }

    private void Eventinfo() {
        // Perform search query in the database
        List<events> eventsList = dbHandler.returnEventsInfo();

        if (!eventsList.isEmpty()) {
            // Display the first event details
            events event1 = eventsList.get(1);
            String eventDetails1 = event1.getTitle() + "\n" + event1.getStartTime() + "--" + event1.getEndTime();

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

            binding.participate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if eventsList is not empty
                    if (!eventsList.isEmpty()) {
                        // Get the first event
                        events event1 = eventsList.get(1);

                        // Retrieve the number from the EditText field
                        EditText editText = findViewById(R.id.numper1);
                        String insertedNumberStr = editText.getText().toString();

                        if (!insertedNumberStr.isEmpty()) { // Check if the EditText field is not empty
                            // Parse the inserted number to an integer
                            int insertedNumber = Integer.parseInt(insertedNumberStr);

                            // Get the capacity from the event object
                            int eventCapacity = event1.getCapacity();

                            // Compare the inserted number with the capacity
                            if (insertedNumber <= eventCapacity) {
                                // Start the participants_list activity
                                Intent fillParticipantslist = new Intent(events_screen.this, participants_list.class);
                                // Pass the event description as an extra with the intent
                                fillParticipantslist.putExtra("event_title", event1.getTitle());
                                fillParticipantslist.putExtra("inserted_number", insertedNumber);
                                fillParticipantslist.putExtra("start_time", event1.getStartTime());
                                fillParticipantslist.putExtra("end_time", event1.getEndTime());
                                fillParticipantslist.putExtra("description", event1.getDescription());
                                fillParticipantslist.putExtra("location", event1.getLocation());
                                startActivity(fillParticipantslist);
                            } else {
                                // Start the not_enough_capacity activity
                                Intent notEnoughroom = new Intent(events_screen.this, not_enough_capacity.class);
                                startActivity(notEnoughroom);
                            }
                        } else {
                            // If the EditText field is empty
                            // Show a message or perform any other action
                            Toast.makeText(events_screen.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            events event2 = eventsList.get(0);
            String eventDetails2 = event2.getTitle() + "\n" + event2.getStartTime() + "--" + event2.getEndTime();
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

            binding.participate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if eventsList is not empty
                    if (!eventsList.isEmpty()) {
                        // Get the first event
                        events event2 = eventsList.get(0);

                        // Retrieve the number from the EditText field
                        EditText editText = findViewById(R.id.numper2);
                        String insertedNumberStr = editText.getText().toString();

                        if (!insertedNumberStr.isEmpty()) { // Check if the EditText field is not empty
                            // Parse the inserted number to an integer
                            int insertedNumber = Integer.parseInt(insertedNumberStr);

                            // Get the capacity from the event object
                            int eventCapacity = event2.getCapacity();

                            // Compare the inserted number with the capacity
                            if (insertedNumber <= eventCapacity) {
                                // Start the participants_list activity
                                Intent fillParticipantslist = new Intent(events_screen.this, participants_list.class);
                                // Pass the event description as an extra with the intent
                                fillParticipantslist.putExtra("event_title", event2.getTitle());
                                fillParticipantslist.putExtra("inserted_number", insertedNumber);
                                fillParticipantslist.putExtra("start_time", event2.getStartTime());
                                fillParticipantslist.putExtra("end_time", event2.getEndTime());
                                fillParticipantslist.putExtra("description", event2.getDescription());
                                fillParticipantslist.putExtra("location", event2.getLocation());
                                startActivity(fillParticipantslist);
                            } else {
                                // Start the not_enough_capacity activity
                                Intent notEnoughroom = new Intent(events_screen.this, not_enough_capacity.class);
                                startActivity(notEnoughroom);
                            }
                        } else {
                            // If the EditText field is empty
                            // Show a message or perform any other action
                            Toast.makeText(events_screen.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else {
            // No events found
            binding.noevents.setText("No events found");
        }
    }
}