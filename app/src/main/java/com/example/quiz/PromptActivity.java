package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quiz.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_ANSWER_SHOWN = "answer_shown";
    private int id_podpowiedzi;
    private TextView podpowiedzTextView;

    private Button pokazPodpowiedzButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        id_podpowiedzi = getIntent().getIntExtra(MainActivity.KEY_PODPOWIEDZ, 1);

        pokazPodpowiedzButton = findViewById(R.id.show_prompt_button);
        podpowiedzTextView = findViewById(R.id.podpowiedz_text_view);

        pokazPodpowiedzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                podpowiedzTextView.setText(id_podpowiedzi);
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean answerWasShown)
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}