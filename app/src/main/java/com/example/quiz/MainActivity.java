package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;

    private Question[] questions = new Question[]{
            new Question(R.string.q_licznosc_continuum, false),
            new Question(R.string.q_licznosc_alef_0, false),
            new Question(R.string.q_dzielenie, false),
            new Question(R.string.q_mnozenie, true),
            new Question(R.string.q_kryptografia, true)
    };

    private int currentIndex = 0;

    private int pointsScoredThisQuestion = 0;

    private int pointsScoredOverWholeRun = 0;

    private boolean isItFirstQuestion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1)%questions.length;
                setNextQuestion();
            }
        });
        setNextQuestion();
        isItFirstQuestion = false;
    }
    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (userAnswer == correctAnswer) {
            pointsScoredThisQuestion = 1;
            resultMessageId = R.string.correct_answer;
        }
        else {
            pointsScoredThisQuestion = 0;
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion()
    {
        pointsScoredOverWholeRun += pointsScoredThisQuestion;
        pointsScoredThisQuestion = 0;
        if (currentIndex == 0 && !isItFirstQuestion) {
            String text = getString(R.string.result, pointsScoredOverWholeRun, questions.length);
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            pointsScoredOverWholeRun = 0;
        }
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
}