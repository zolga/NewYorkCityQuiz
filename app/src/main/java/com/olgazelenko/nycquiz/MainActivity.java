package com.olgazelenko.nycquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String playerName; //TODO: public and use it in result view
    private int layoutId = R.layout.activity_main;
    private EditText nameEditText;
    final String STATE_PLAYER_NAME = String.valueOf(R.string.player_name);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_PLAYER_NAME, playerName);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playerName = savedInstanceState.getString(STATE_PLAYER_NAME);
        nameEditText.setText(playerName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = new Intent(this, ResultActivity.class);
//        intent.putExtra(STATE_PLAYER_NAME, playerName);
//        intent.putExtra("score", 100);
//        startActivity(intent);

        setContentView(layoutId);
        initialStates();
    }
    /**
     * This method is called from the onCreate method and initial all the global variables.
     */
    private void initialStates() {
        nameEditText = findViewById(R.id.name);
        nameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
        playerName = "";
    }

    public void start(View view)
    {
        boolean valid = getPlayerName();
        if (valid)
        {
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra(STATE_PLAYER_NAME, playerName);
            startActivity(intent);
        }
    }

    private boolean getPlayerName(){
        boolean valid =  true;
        playerName = nameEditText.getText().toString();
        if(playerName == "")
        {
            valid = false;
            Toast.makeText(this, R.string.validation_emptyName, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
}

