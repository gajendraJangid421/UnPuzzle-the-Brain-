package com.example.puzzlethebrain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mozilla.javascript.Scriptable;

import java.util.Objects;
import java.util.Random;

public class ActivityGame4 extends AppCompatActivity {

    TextView tv_num1, tv_num2, tv_num3, tv_ch1, tv_ch2, tv_level, tv_score;
    EditText et_ans;
    Button bt_check;
    String answer = "", st = "x/+-";
    Random random = new Random();
    int level = 1, score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Math Quiz");

        tv_level = findViewById(R.id.tv_level);
        tv_score = findViewById(R.id.tv_score);
        tv_num1 = findViewById(R.id.tv_num1);
        tv_num2 = findViewById(R.id.tv_num2);
        tv_num3 = findViewById(R.id.tv_num3);
        tv_ch1 = findViewById(R.id.tv_ch1);
        tv_ch2 = findViewById(R.id.tv_ch2);
        et_ans = findViewById(R.id.et_ans);
        bt_check = findViewById(R.id.bt_check);

        int num1 = (int) Math.floor(Math.random()*(25-5) + 5);
        int num2 = (int) Math.floor(Math.random()*(25-5) + 5);
        int num3 = (int) Math.floor(Math.random()*(25-5) + 5);
        int char1 = random.nextInt(st.length());
        int char2 = random.nextInt(st.length());
        char ch1 = st.charAt(char1);
        char ch2 = st.charAt(char2);

        tv_num1.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                random.nextInt(256)));
        tv_num2.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                random.nextInt(256)));
        tv_num3.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                random.nextInt(256)));
        tv_ch1.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                random.nextInt(256)));
        tv_ch2.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                random.nextInt(256)));

        tv_num1.setText(String.valueOf(num1));
        tv_num2.setText(String.valueOf(num2));
        tv_num3.setText(String.valueOf(num3));
        tv_ch1.setText(String.valueOf(ch1));
        tv_ch2.setText(String.valueOf(ch2));

        StringBuilder sb = new StringBuilder();
        sb.append(num1);
        sb.append(ch1);
        sb.append(num2);
        sb.append(ch2);
        sb.append(num3);

        String str = sb.toString();
        String ans = rhinoLibrary(str);

        sb.delete(0 , sb.length());
        for (int i = 0; i < ans.length(); i++) {
            char ch = ans.charAt(i);
            if (ch == '.') {
                break;
            }
            sb.append(ch);
        }
        answer = sb.toString();

        bt_check.setOnClickListener(this::onclick);
    }

    @SuppressLint({"DefaultLocale" , "SetTextI18n"})
    private void onclick(View view) {

        if(level==10){
            Toast.makeText(getApplicationContext() , "Your score is: " + score , Toast.LENGTH_SHORT).show();
        }else {
            if (et_ans.getText().toString().equals(answer)) {
                Toast.makeText(getApplicationContext() , "Correct" , Toast.LENGTH_SHORT).show();
                score++;
            } else {
                Toast.makeText(getApplicationContext() , "Wrong\nCorrect answer is: " + answer , Toast.LENGTH_SHORT).show();
            }
            level++;

            int num1 = (int) Math.floor(Math.random()*(25-5) + 5);
            int num2 = (int) Math.floor(Math.random()*(25-5) + 5);
            int num3 = (int) Math.floor(Math.random()*(25-5) + 5);
            int char1 = random.nextInt(st.length());
            int char2 = random.nextInt(st.length());
            char ch1 = st.charAt(char1);
            char ch2 = st.charAt(char2);

            tv_num1.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                    random.nextInt(256)));
            tv_num2.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                    random.nextInt(256)));
            tv_num3.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                    random.nextInt(256)));
            tv_ch1.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                    random.nextInt(256)));
            tv_ch2.setTextColor(Color.argb(255 , random.nextInt(256) , random.nextInt(256) ,
                    random.nextInt(256)));

            tv_num1.setText(String.valueOf(num1));
            tv_num2.setText(String.valueOf(num2));
            tv_num3.setText(String.valueOf(num3));
            tv_ch1.setText(String.valueOf(ch1));
            tv_ch2.setText(String.valueOf(ch2));
            tv_level.setText("Level: " + level);
            tv_score.setText(String.format("Score: %d" , score));
            et_ans.getText().clear();

            StringBuilder sb = new StringBuilder();
            sb.append(num1);
            sb.append(ch1);
            sb.append(num2);
            sb.append(ch2);
            sb.append(num3);

            String str = sb.toString();
            String ans = rhinoLibrary(str);

            sb.delete(0 , sb.length());
            for (int i = 0; i < ans.length(); i++) {
                char ch = ans.charAt(i);
                if (ch == '.') {
                    break;
                }
                sb.append(ch);
            }
            answer = sb.toString();

            bt_check.setOnClickListener(this::onclick);
        }
    }


    public static String rhinoLibrary(String toString) {
        String data = toString;

        data = data.replaceAll("×" , "*");

        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        Scriptable scriptable = rhino.initStandardObjects();

        return rhino.evaluateString(scriptable , data , "Javascript" , 1 , null).toString();
    }

}