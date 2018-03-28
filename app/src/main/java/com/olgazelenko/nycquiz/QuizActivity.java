package com.olgazelenko.nycquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private final String STATE_PLAYER_NAME = "PLAYER_NAME";
    private final String STATE_SCORE = "SCORE";
    private final String STATE_Q1 = "STATE_Q1";
    private final String STATE_Q2 = "STATE_Q2";
    private final String STATE_Q4 = "STATE_Q4";
    private final String STATE_Q5 = "STATE_Q5";
    private final String STATE_Q8 = "STATE_Q8";
    private final String STATE_Q9 = "STATE_Q9";
    private final String STATE_Q10 = "STATE_Q10";
    private RadioGroup q1, q2, q4, q5, q8, q9, q10;
    private CheckBox q3_1, q3_2, q3_3, q3_4, q7_1, q7_2, q7_3, q7_4;
    private Spinner spinner_q6;
    private String playerName;
    private String[] correctAnswers;
    private int totalCorrect;
    private int questionNumber;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //save checked radio buttons
        savedInstanceState.putString(STATE_PLAYER_NAME, playerName);
        savedInstanceState.putInt(STATE_Q1, q1.getCheckedRadioButtonId());
        savedInstanceState.putInt(STATE_Q2, q2.getCheckedRadioButtonId());
        savedInstanceState.putInt(STATE_Q4, q4.getCheckedRadioButtonId());
        savedInstanceState.putInt(STATE_Q5, q5.getCheckedRadioButtonId());
        savedInstanceState.putInt(STATE_Q8, q8.getCheckedRadioButtonId());
        savedInstanceState.putInt(STATE_Q9, q9.getCheckedRadioButtonId());
        savedInstanceState.putInt(STATE_Q10, q10.getCheckedRadioButtonId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playerName = savedInstanceState.getString(STATE_PLAYER_NAME);
        //restore checked radio buttons
        try {
            q1.check(savedInstanceState.getInt(STATE_Q1));
            q2.check(savedInstanceState.getInt(STATE_Q2));
            q4.check(savedInstanceState.getInt(STATE_Q4));
            q5.check(savedInstanceState.getInt(STATE_Q5));
            q8.check(savedInstanceState.getInt(STATE_Q8));
            q9.check(savedInstanceState.getInt(STATE_Q9));
            q10.check(savedInstanceState.getInt(STATE_Q10));
        } catch (Exception e) {
            Log.v(getString(R.string.quiz_activity_name), getString(R.string.LOG_ONRESTORE_ERROR));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = R.layout.activity_quiz;
        setContentView(layoutId);
        initialStates(getIntent());
    }

    /**
     * This method is called from the onCreate method and initial all the global variables.
     */
    private void initialStates(Intent intent) {
        playerName = intent.getStringExtra(STATE_PLAYER_NAME);
        totalCorrect = 0;
        questionNumber = 0;
        //RadioButton
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        q8 = findViewById(R.id.q8);
        q9 = findViewById(R.id.q9);
        q10 = findViewById(R.id.q10);
        //CheckBox Q3
        q3_1 = findViewById(R.id.q3_1);
        q3_2 = findViewById(R.id.q3_2);
        q3_3 = findViewById(R.id.q3_3);
        q3_4 = findViewById(R.id.q3_4);
        //CheckBox Q7
        q7_1 = findViewById(R.id.q7_1);
        q7_2 = findViewById(R.id.q7_2);
        q7_3 = findViewById(R.id.q7_3);
        q7_4 = findViewById(R.id.q7_4);
        //Spinner Q6
        spinner_q6 = findViewById(R.id.spinner_q6);
        initSpinner();
        FillCorrectAnswersList();
    }

    private void FillCorrectAnswersList() {
        correctAnswers = getResources().getStringArray(R.array.correctAnswersArray);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.q6_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_q6.setAdapter(adapter);
    }

    public void submit(View view) {
        totalCorrect = getTotalCorrectAnswers();
        if (totalCorrect < 0)
            return;
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(STATE_PLAYER_NAME, playerName);
        intent.putExtra(STATE_SCORE, totalCorrect);
        startActivity(intent);
    }

    private int getTotalCorrectAnswers() {
        questionNumber = 0;
        try {
            checkRadioButtonAnswer(q1);
            checkRadioButtonAnswer(q2);

            if (q3_1.isChecked() && q3_2.isChecked() && !q3_3.isChecked() && !q3_4.isChecked())
                totalCorrect++;

            checkRadioButtonAnswer(q4);
            checkRadioButtonAnswer(q5);

            final String answer6 = spinner_q6.getSelectedItem().toString();
            if (answer6.equals(correctAnswers[questionNumber])) {
                totalCorrect++;
                questionNumber++;
            }
            if (q7_1.isChecked() && q7_3.isChecked() && !q7_2.isChecked() && !q7_4.isChecked())
                totalCorrect++;

            checkRadioButtonAnswer(q8);
            checkRadioButtonAnswer(q9);
            checkRadioButtonAnswer(q10);

            return totalCorrect;

        } catch (Exception e) {
            Toast.makeText(this, R.string.validation_error, Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    private void checkRadioButtonAnswer(RadioGroup rg) {
        String answer = getCheckedRadioButtonId(rg).getText().toString();
        if (answer.equals(correctAnswers[questionNumber]))
            totalCorrect++;
        questionNumber++;
    }

    private RadioButton getCheckedRadioButtonId(RadioGroup rg) {
        return (RadioButton) findViewById(rg.getCheckedRadioButtonId());
    }

}
