package com.example.puzzlethebrain;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class ActivityGame2 extends AppCompatActivity {

    private TextView mTextView;
    TextView tv_level, tv_number;
    EditText et_number;
    Button b_confirm;

    int currentLevel = 1;
    String generateNumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Number Memory Game");

        tv_level = findViewById(R.id.tv_level);
        tv_number = findViewById(R.id.tv_number);
        et_number = findViewById(R.id.et_number);
        b_confirm = findViewById(R.id.b_confirm);

        et_number.setVisibility(View.GONE);
        b_confirm.setVisibility(View.GONE);
        tv_number.setVisibility(View.VISIBLE);

        tv_level.setText(("Level: " + currentLevel));
        if(currentLevel<8) {
            generateNumber = (generateNumber(currentLevel));
        }else{
            generateNumber = (generateNumber(8));
        }
        tv_number.setText(generateNumber);

        new Handler().postDelayed(() -> {
            et_number.setVisibility(View.VISIBLE);
            b_confirm.setVisibility(View.VISIBLE);
            tv_number.setVisibility(View.GONE);
        } , 1000);

        b_confirm.setOnClickListener(this::onclick);
    }

    private String generateNumber(int digits) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            Random rand = new Random();
            int randomDigits = rand.nextInt(10);
            output.append(randomDigits);
        }
        return output.toString();
    }

    private void onclick(View v) {

        if (generateNumber.equals(et_number.getText().toString())) {
            et_number.setVisibility(View.GONE);
            b_confirm.setVisibility(View.GONE);
            tv_number.setVisibility(View.VISIBLE);

            et_number.setText("");
            currentLevel++;
            tv_level.setText(("Level: " + currentLevel));
            generateNumber = (generateNumber(currentLevel));
            tv_number.setText(generateNumber);

            new Handler().postDelayed(() -> {
                et_number.setVisibility(View.VISIBLE);
                b_confirm.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.GONE);
                // button_playAgain.setVisibility(View.VISIBLE);
                et_number.requestFocus();
            } , 1000);
        } else {
            tv_level.setText(format("Game Over! The number was %s" , generateNumber));
            b_confirm.setEnabled(false);
        }

    }

}