package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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


    private String debug_tag;
    private String debug_onStart;
    private String debug_onStop;
    private String debug_onDestroy;
    private String debug_onPause;
    private String debug_onResume;
    private String debug_onCreate;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(debug_tag, debug_onStart);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(debug_tag, debug_onStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(debug_tag, debug_onDestroy);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(debug_tag, debug_onPause);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(debug_tag, debug_onResume);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        debug_tag = getResources().getString(R.string.debug_tag);
        debug_onStart = getResources().getString(R.string.debug_onStart);
        debug_onStop = getResources().getString(R.string.debug_onStop);
        debug_onDestroy = getResources().getString(R.string.debug_onDestroy);
        debug_onPause = getResources().getString(R.string.debug_onPause);
        debug_onResume = getResources().getString(R.string.debug_onResume);
        debug_onCreate = getResources().getString(R.string.debug_onCreate);

        Log.d(debug_tag, debug_onCreate);

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