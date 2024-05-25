package com.example.readease3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class play_quiz extends AppCompatActivity {

    private LinearLayout questionsContainer;
    private Button submitButton;
    private List<Question> questions;
    private List<QuestionViewHolder> questionViewHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_quiz);

        questionsContainer = findViewById(R.id.questions_container);
        submitButton = findViewById(R.id.submit_button);

        // Load questions
        questions = getQuestions();
        questionViewHolders = new ArrayList<>();
        loadQuestions();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "Σε ποιο σπίτι ανήκει ο Harry Potter στο Χόγκουαρτς;",
                new String[]{"Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin"},
                0 // Index of the correct answer "a) Gryffindor"
        ));

        questions.add(new Question(
                "Ποιος είναι ο καλύτερος φίλος του Harry Potter;",
                new String[]{"Draco Malfoy", "Neville Longbottom", "Ron Weasley", "Cedric Diggory"},
                2 // Index of the correct answer "c) Ron Weasley"
        ));

        questions.add(new Question(
                "Ποιο μαγικό ραβδί χρησιμοποιεί ο Harry στη μάχη του στα θαλάμια των μυστικών;",
                new String[]{"Το δικό του ραβδί", "Το ραβδί του Dumbledore", "Το ραβδί του Snape", "Το ραβδί του Voldemort"},
                0 // Index of the correct answer "a) Το δικό του ραβδί"
        ));

        questions.add(new Question(
                "Ποια ουσία χρησιμοποιεί ο Harry για να σώσει τον Δούδουρα από τις αράχνες στον Κύλικα του Φωτός;",
                new String[]{"Felix Felicis", "Polyjuice Potion", "Wolfsbane Potion", "Veritaserum"},
                0 // Index of the correct answer "a) Felix Felicis"
        ));

        questions.add(new Question(
                "Ποιος ήταν ο πραγματικός κληρονόμος του Slytherin;",
                new String[]{"Tom Riddle", "Severus Snape", "Albus Dumbledore", "Salazar Slytherin"},
                0 // Index of the correct answer "a) Tom Riddle"
        ));

        questions.add(new Question(
                "Σε ποια γλώσσα μπορεί να μιλήσει ο Harry, που συνδέεται με την καταγωγή του Voldemort;",
                new String[]{"Mermish", "Gobbledegook", "Parseltongue", "Elvish"},
                2 // Index of the correct answer "c) Parseltongue"
        ));

        return questions;
    }

    private void loadQuestions() {
        for (Question question : questions) {
            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardLayoutParams.setMargins(0, 0, 0, 16);
            cardView.setLayoutParams(cardLayoutParams);
            cardView.setRadius(8);
            cardView.setCardElevation(4);

            LinearLayout questionLayout = new LinearLayout(this);
            questionLayout.setOrientation(LinearLayout.VERTICAL);
            questionLayout.setPadding(16, 16, 16, 16);

            TextView questionText = new TextView(this);
            questionText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            questionText.setText(question.getQuestionText());
            questionText.setTextColor(getResources().getColor(android.R.color.black, null));
            questionText.setTextSize(20);
            questionLayout.addView(questionText);

            RadioGroup answersGroup = new RadioGroup(this);
            answersGroup.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            answersGroup.setOrientation(RadioGroup.VERTICAL);

            String[] answers = question.getAnswers();
            for (String answer : answers) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setId(View.generateViewId());
                radioButton.setText(answer);
                radioButton.setTextColor(getResources().getColor(android.R.color.black, null));
                answersGroup.addView(radioButton);
            }

            questionLayout.addView(answersGroup);
            cardView.addView(questionLayout);

            questionsContainer.addView(cardView);

            questionViewHolders.add(new QuestionViewHolder(question, answersGroup));
        }
    }

    private void checkAnswers() {
        int correctAnswers = 0;

        for (QuestionViewHolder holder : questionViewHolders) {
            int selectedId = holder.answersGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = holder.answersGroup.findViewById(selectedId);
                int selectedIndex = holder.answersGroup.indexOfChild(selectedRadioButton);

                if (selectedIndex == holder.question.getCorrectAnswerIndex()) {
                    correctAnswers++;
                }
            }
        }

        int totalScore = correctAnswers * 2; // 2 points for each correct answer
        Toast.makeText(this, "Κέρδισες " + totalScore + "πόντους! (" + correctAnswers + "σωστές απαντήσεις)", Toast.LENGTH_SHORT).show();
    }

    private static class QuestionViewHolder {
        Question question;
        RadioGroup answersGroup;

        QuestionViewHolder(Question question, RadioGroup answersGroup) {
            this.question = question;
            this.answersGroup = answersGroup;
        }
    }

    class Question {
        private String questionText;
        private String[] answers;
        private int correctAnswerIndex;

        public Question(String questionText, String[] answers, int correctAnswerIndex) {
            this.questionText = questionText;
            this.answers = answers;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String[] getAnswers() {
            return answers;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }
}
