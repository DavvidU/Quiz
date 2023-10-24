package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_SCORED_TQ = "ptsScoredTQ";
    private static final String KEY_SCORED_WR = "ptsScoredWR";
    private static final String KEY_FIRST_QUESTION = "keyFQ";
    private static final int REQUEST_CODE_PROMPT = 0;
    public static final String KEY_PODPOWIEDZ = "com.example.quiz.id_podpowiedzi";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button podpowiedzButton;
    private TextView questionTextView;

    private Question[] questions = new Question[]{
            new Question(R.string.q_licznosc_continuum, false,
                    R.string.q_licznosc_continuum_podpowiedz),
            new Question(R.string.q_licznosc_alef_0, false,
                    R.string.q_licznosc_alef_0_podpowiedz),
            new Question(R.string.q_dzielenie, false,
                    R.string.q_dzielenie_podpowiedz),
            new Question(R.string.q_mnozenie, true,
                    R.string.q_mnozenie_podpowiedz),
            new Question(R.string.q_kryptografia, true,
                    R.string.q_kryptografia_podpowiedz)
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
    private String debug_onSaveInstanceState;

    private boolean answerWasShown;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { return; }
        if (requestCode == REQUEST_CODE_PROMPT)
        {
            if (data == null) { return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(debug_tag, debug_onSaveInstanceState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putInt(KEY_SCORED_TQ, pointsScoredThisQuestion);
        outState.putInt(KEY_SCORED_WR, pointsScoredOverWholeRun);
        outState.putBoolean(KEY_FIRST_QUESTION, isItFirstQuestion);

    }
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
        debug_onSaveInstanceState = getResources().getString(R.string.debug_onSaveInstanceState);

        Log.d(debug_tag, debug_onCreate);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            pointsScoredThisQuestion = savedInstanceState.getInt(KEY_SCORED_TQ);
            pointsScoredOverWholeRun = savedInstanceState.getInt(KEY_SCORED_WR);
            if (currentIndex != 0)
                isItFirstQuestion = savedInstanceState.getBoolean(KEY_FIRST_QUESTION);
        }
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        podpowiedzButton = findViewById(R.id.prompt_button);

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
                answerWasShown = false;
                setNextQuestion();
            }
        });
        podpowiedzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                int id_podpowiedzi = questions[currentIndex].getPodpowiedzId();
                intent.putExtra(KEY_PODPOWIEDZ, id_podpowiedzi);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
        setNextQuestion();
        isItFirstQuestion = false;
    }
    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (answerWasShown)
        {
            resultMessageId = R.string.odpowiedz_po_podpowiedzi;
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        }
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