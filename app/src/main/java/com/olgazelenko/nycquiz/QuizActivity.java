package com.olgazelenko.nycquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 24/03/2018.
 */
public class QuizActivity extends AppCompatActivity {
    private int layoutId = R.layout.activity_quiz;
    private RadioGroup q1, q2, q4, q5, q8, q9, q10;
    private CheckBox q3_1, q3_2, q3_3, q3_4, q7_1, q7_2, q7_3, q7_4;
    private Spinner spinner_q6;
    private String playerName;
    private String[] correctAnswers;
    private String[] correctAnswers_q3;
    private String[] correctAnswers_q7;
    int totalCorrect;
    int questionNumber;
    private final String STATE_PLAYER_NAME = getString(R.string.player_name);
    private final String STATE_SCORE = getString(R.string.player_score);
    private final String STATE_Q1 = getString(R.string.STATE_Q1);
    private final String STATE_Q2 = getString(R.string.STATE_Q2);
    private final String STATE_Q3_1 = getString(R.string.STATE_Q3_1);
    private final String STATE_Q3_2 = getString(R.string.STATE_Q3_2);
    private final String STATE_Q3_3 = getString(R.string.STATE_Q3_3);
    private final String STATE_Q3_4 = getString(R.string.STATE_Q3_4);
    private final String STATE_Q4 = getString(R.string.STATE_Q4);
    private final String STATE_Q5 = getString(R.string.STATE_Q5);
    private final String STATE_Q6 = getString(R.string.STATE_Q6);
    private final String STATE_Q7_1 = getString(R.string.STATE_Q7_1);
    private final String STATE_Q7_2 = getString(R.string.STATE_Q7_2);
    private final String STATE_Q7_3 = getString(R.string.STATE_Q7_3);
    private final String STATE_Q7_4 = getString(R.string.STATE_Q7_4);
    private final String STATE_Q8 = getString(R.string.STATE_Q8);
    private final String STATE_Q9 = getString(R.string.STATE_Q9);
    private final String STATE_Q10 = getString(R.string.STATE_Q10);


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(STATE_PRICE, orderPrice);
//        outState.putInt(STATE_QUANTITY, quantity);
//        outState.putBoolean(STATE_CHECK_BOX_WHIPPED_CREAM, whippedCreamCheckBox.isChecked());
//        outState.putBoolean(STATE_CHECK_BOX_CHOCOLATE, chocolateCheckBox.isChecked());
//        outState.putString(STATE_USER_NAME, nameEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        orderPrice = savedInstanceState.getInt(STATE_PRICE);
//        quantity = savedInstanceState.getInt(STATE_QUANTITY);
//        whippedCreamCheckBox.setChecked(savedInstanceState.getBoolean(STATE_CHECK_BOX_WHIPPED_CREAM));
//        chocolateCheckBox.setChecked(savedInstanceState.getBoolean(STATE_CHECK_BOX_CHOCOLATE));
//        nameEditText.setText(savedInstanceState.getString(STATE_USER_NAME));
//        setQuantityAndPrice();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        initialStates(getIntent());
    }

    /**
     * This method is called from the onCreate method and initial all the global variables.
     */
    private void initialStates(Intent intent) {
        playerName = intent.getExtras().getString(String.valueOf(R.string.player_name));
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
        q3_1 =  findViewById(R.id.q3_1);
        q3_2 =  findViewById(R.id.q3_2);
        q3_3 =  findViewById(R.id.q3_3);
        q3_4 =  findViewById(R.id.q3_4);
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
        correctAnswers_q3 = getResources().getStringArray(R.array.correctAnswersArray_Question3);
        correctAnswers_q7 = getResources().getStringArray(R.array.correctAnswersArray_Question7);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.q6_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_q6.setAdapter(adapter);
    }

    public void submit(View view) {
        totalCorrect = getTotalCorrectAnswers();
        if(totalCorrect < 0)
            return;
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(STATE_PLAYER_NAME, playerName);
        intent.putExtra(STATE_SCORE, totalCorrect);
        startActivity(intent);
    }

    private int getTotalCorrectAnswers() {
        questionNumber = 0;
        try {
            checkRadioButtonAnswer(questionNumber, q1);
            checkRadioButtonAnswer(questionNumber, q2);

            if(q3_1.isChecked() && q3_2.isChecked() && !q3_3.isChecked() && !q3_4.isChecked())
                totalCorrect++;

            checkRadioButtonAnswer(questionNumber, q4);
            checkRadioButtonAnswer(questionNumber, q5);

            final String answer6 = spinner_q6.getSelectedItem().toString();
            if (answer6.equals(correctAnswers[questionNumber]))
                totalCorrect++;

            if(q7_1.isChecked() && q7_3.isChecked() && !q7_2.isChecked() && !q7_4.isChecked())
                totalCorrect++;

            checkRadioButtonAnswer(questionNumber, q8);
            checkRadioButtonAnswer(questionNumber, q9);
            checkRadioButtonAnswer(questionNumber, q10);

            return totalCorrect;

        }   catch (Exception e)
        {
            Toast.makeText(this, R.string.validation_error, Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
    private void checkRadioButtonAnswer(int questionIdx, RadioGroup rg) {
        final String answer = getCheckedRadioButtonId(rg).getText().toString();
        if (answer.equals(correctAnswers[questionIdx]))
            totalCorrect++;
        questionIdx++;
    }

    private RadioButton getCheckedRadioButtonId(RadioGroup rg)
    {
        return (RadioButton) findViewById(rg.getCheckedRadioButtonId());
    }

}
