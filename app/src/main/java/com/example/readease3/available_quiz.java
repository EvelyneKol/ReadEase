package com.example.readease3;

import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.content.res.ColorStateList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.List;

public class available_quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_quiz);

        ConstraintLayout layout = findViewById(R.id.main);
        DBHandler dbHandler = new DBHandler(this);
        List<String> quizTitles = dbHandler.getAllQuizTitles(); // Make sure this method is implemented in DBHandler

        int previousViewId = R.id.imageView4; // Start below the ImageView
        int margin = (int) (16 * getResources().getDisplayMetrics().density); // Convert dp to pixels

        for (String title : quizTitles) {
            TextView textView = new TextView(this);
            textView.setId(TextView.generateViewId());
            textView.setText(title);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

            Button button = new Button(this);
            button.setId(Button.generateViewId());
            button.setText("Παίξε τώρα");
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6d6b6b")));

            layout.addView(textView, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(button, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

            ConstraintSet set = new ConstraintSet();
            set.clone(layout);

            // Constraints for TextView
            set.connect(textView.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START, margin);
            set.connect(textView.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END, margin);
            set.connect(textView.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, margin);

            // Constraints for Button
            set.connect(button.getId(), ConstraintSet.START, textView.getId(), ConstraintSet.START);
            set.connect(button.getId(), ConstraintSet.TOP, textView.getId(), ConstraintSet.BOTTOM, margin);

            set.applyTo(layout);

            previousViewId = button.getId(); // Update the previousViewId to the current button's id for next iteration
        }
    }
}
