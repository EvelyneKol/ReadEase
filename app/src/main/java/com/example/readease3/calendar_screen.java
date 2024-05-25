package com.example.readease3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class calendar_screen extends AppCompatActivity {

    private TextView startDateTextView;
    private TextView endDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_screen);

        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);

        // Show start date picker dialog immediately when the activity is created
        showStartDatePickerDialog();
    }

    private void showStartDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog startDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String startDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        startDateTextView.setText("Start Date: " + startDate);
                        showEndDatePickerDialog(); // Show end date picker dialog after setting start date
                    }
                }, year, month, dayOfMonth);

        startDatePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String endDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        endDateTextView.setText("End Date: " + endDate);
                    }
                }, year, month, dayOfMonth);

        endDatePickerDialog.show();
    }
}
