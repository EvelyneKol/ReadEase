package com.example.readease3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    private String event1_getTitle;
    private String event1_getStartTime ;
    private String event1_getEndTime ;
    private String event1_getDescription ;
    private String event1_getLocation ;

    private String event2_getTitle;
    private String event2_getStartTime ;
    private String event2_getEndTime ;
    private String event2_getDescription ;
    private String event2_getLocation ;

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
        getEventinfo();
    }

    private void getEventinfo() {
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
                            int insertedNumber1 = Integer.parseInt(insertedNumberStr);

                            // Get the capacity from the event object
                            int eventCapacity = event1.getCapacity();
                            event1_getTitle =event1.getTitle();
                            event1_getStartTime =event1.getStartTime();
                            event1_getEndTime =event1.getEndTime();
                            event1_getDescription =event1.getDescription();
                            event1_getLocation =event1.getLocation();

                            // Compare the inserted number with the capacity
                            if (checkCapacity(insertedNumber1, eventCapacity) == 1) {
                                fillParticipantslist(1,insertedNumber1);
                            } else {
                                notEnoughroom();
                            }
                        } else {
                            // If the EditText field is empty
                            // Show a message or perform any other action
                            Toast.makeText(events_screen.this, "Παρακαλώ εισάγεται κάποιον αριθμό!", Toast.LENGTH_SHORT).show();
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
                            int insertedNumber2 = Integer.parseInt(insertedNumberStr);

                            // Get the capacity from the event object
                            int eventCapacity = event2.getCapacity();
                            event2_getTitle =event2.getTitle();
                            event2_getStartTime =event2.getStartTime();
                            event2_getEndTime =event2.getEndTime();
                            event2_getDescription =event2.getDescription();
                            event2_getLocation =event2.getLocation();

                            // Compare the inserted number with the capacity
                            if (checkCapacity(insertedNumber2, eventCapacity) == 1) {
                                fillParticipantslist(2,insertedNumber2);
                            } else {
                                notEnoughroom();
                            }
                        } else {
                            // If the EditText field is empty
                            // Show a message or perform any other action
                            Toast.makeText(events_screen.this, "Παρακαλώ εισάγεται κάποιον αριθμό!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else {
            // No events found
            binding.noevents.setText("No events found");
        }
    }
    private void notEnoughroom() {
        new AlertDialog.Builder(this)
                .setTitle("Μη επαρκείς χωρητικότητα")
                .setMessage("Μας συγχωρείται αλλά ο αριθμός ατόμων που επιλέξατε υπερβαίνει το επιτρεπτό όριο.Πακαλούμε επιλέξτε έναν έγκυρο αριθμών ατόμων!")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void fillParticipantslist(int eventNum, int number) {
        if(eventNum==1){
            // Start the participants_list activity
            Intent fillParticipantslist = new Intent(events_screen.this, participants_list.class);
            // Pass the event description as an extra with the intent
            fillParticipantslist.putExtra("event_title", event1_getTitle);
            fillParticipantslist.putExtra("inserted_number", number);
            fillParticipantslist.putExtra("start_time", event1_getStartTime);
            fillParticipantslist.putExtra("end_time", event1_getEndTime);
            fillParticipantslist.putExtra("description", event1_getDescription);
            fillParticipantslist.putExtra("location", event1_getLocation);
            startActivity(fillParticipantslist);
        }
        if(eventNum==2){
            // Start the participants_list activity
            Intent fillParticipantslist = new Intent(events_screen.this, participants_list.class);
            // Pass the event description as an extra with the intent
            fillParticipantslist.putExtra("event_title", event2_getTitle);
            fillParticipantslist.putExtra("inserted_number", number);
            fillParticipantslist.putExtra("start_time", event2_getStartTime);
            fillParticipantslist.putExtra("end_time", event2_getEndTime );
            fillParticipantslist.putExtra("description", event2_getDescription);
            fillParticipantslist.putExtra("location", event2_getLocation);
            startActivity(fillParticipantslist);
        }

    }

    private int checkCapacity(int people, int capacity) {
        if (people <= capacity) {
            return 1;
        } else {
            return 0; // or some other value or logic as needed
        }
    }


}