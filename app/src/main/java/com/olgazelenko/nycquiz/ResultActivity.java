package com.olgazelenko.nycquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    final String STATE_PLAYER_NAME = getString(R.string.player_name);
    final String STATE_SCORE = getString(R.string.player_score);
    final int NUMBER_OF_QUESTIONS = Integer.parseInt(getString(R.string.number_of_questions));
    int score;
    String playerName;
    int correctAnswer;
    TextView nameTextView;
    TextView scoreTextView;
    TextView outOfTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        playerName = intent.getStringExtra(STATE_PLAYER_NAME);
        correctAnswer = intent.getIntExtra(STATE_SCORE,0);

        nameTextView = findViewById(R.id.textCongrats);
        String congrats = getString(R.string.congrats);
        nameTextView.setText(String.format(congrats,playerName));

        scoreTextView = findViewById(R.id.theScore);
        score = 0;
        if(correctAnswer > 0)
            score  = Math.round((correctAnswer / NUMBER_OF_QUESTIONS) * 100);
        scoreTextView.setText(score);

        outOfTextView = findViewById(R.id.outOf);
        outOfTextView.setText(String.format(getString(R.string.out_of),correctAnswer, NUMBER_OF_QUESTIONS));
    }

    public void newGame(View view)
    {
        //reset
        setContentView(R.layout.activity_main);
    }
}
