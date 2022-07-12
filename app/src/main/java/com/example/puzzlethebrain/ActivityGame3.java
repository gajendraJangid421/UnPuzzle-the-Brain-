package com.example.puzzlethebrain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class ActivityGame3 extends AppCompatActivity {

    private TextView tv_guess, tv_enter, tv_high, tv_low, tv_win, tv_count;
    private EditText et_number;
    private Button bt_start, bt_check, bt_playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Guess the Number");

        tv_guess = findViewById(R.id.tv_guess);
        tv_enter = findViewById(R.id.tv_enter);
        tv_high = findViewById(R.id.tv_high);
        tv_low = findViewById(R.id.tv_low);
        tv_win = findViewById(R.id.tv_win);
        tv_count = findViewById(R.id.tv_count);
        et_number = findViewById(R.id.et_number);
        bt_start = findViewById(R.id.bt_start);
        bt_check = findViewById(R.id.bt_check);
        bt_playAgain = findViewById(R.id.bt_playAgain);
        final Random random = new Random();
        final int[] num = {0};
        int[] count = {0};

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_guess.setVisibility(View.GONE);
                bt_start.setVisibility(View.GONE);
                tv_enter.setVisibility(View.VISIBLE);
                bt_check.setVisibility(View.VISIBLE);
                et_number.setVisibility(View.VISIBLE);
                num[0] = random.nextInt(1000);
                count[0] = 0;
            }
        });

        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = (et_number.getText().toString());

                if(et_number.getText().toString().equals("")){
                    Toast.makeText(ActivityGame3.this, "Enter the Number", Toast.LENGTH_SHORT).show();
                }else{
                    tv_enter.setVisibility(View.GONE);
                    int numEntered = Integer.valueOf(value);

                    count[0]++;
                    tv_count.setVisibility(View.VISIBLE);
                    tv_count.setText("" + count[0]);

                    if(numEntered > num[0]){
                        tv_high.setVisibility(View.VISIBLE);
                        tv_low.setVisibility(View.GONE);
                        et_number.getText().clear();
                    }else if(numEntered < num[0]){
                        tv_low.setVisibility(View.VISIBLE);
                        tv_high.setVisibility(View.GONE);
                        et_number.getText().clear();
                    }else{
                        tv_low.setVisibility(View.GONE);
                        tv_high.setVisibility(View.GONE);
                        tv_count.setText("In " + count[0] + " tries!");
                        tv_win.setVisibility(View.VISIBLE);
                        bt_check.setEnabled(false);
                        bt_playAgain.setVisibility(View.VISIBLE);
                        closeKeyboard();
                    }

                }
            }
        });

        bt_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_win.setVisibility(View.GONE);
                tv_count.setVisibility(View.GONE);
                bt_check.setEnabled(true);
                bt_check.setVisibility(View.GONE);
                bt_playAgain.setVisibility(View.GONE);
                et_number.getText().clear();
                et_number.setVisibility(View.GONE);
                tv_guess.setVisibility(View.VISIBLE);
                bt_start.setVisibility(View.VISIBLE);
            }
        });
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMM = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMM.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}