package com.example.puzzlethebrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashActivity extends AppCompatActivity {

    ImageView iv_kidProblem, iv_plus, iv_kidThinking, iv_equal, iv_kidSolution;
    TextView tv_appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        iv_kidThinking = findViewById(R.id.iv_kidThinking);
        iv_kidSolution = findViewById(R.id.iv_kidSolution);
        iv_kidProblem = findViewById(R.id.iv_kidProblem);
        iv_plus = findViewById(R.id.iv_plus);
        iv_equal = findViewById(R.id.iv_equal);
        tv_appTitle = findViewById(R.id.tv_appTitle);

        YoYo.with(Techniques.SlideInRight).duration(2000).repeat(0).playOn(iv_kidProblem);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                iv_plus.setVisibility(View.VISIBLE);
                iv_kidThinking.setVisibility(View.VISIBLE);
            }
        };
        Handler handler1 = new Handler(Looper.getMainLooper());
        handler1.postDelayed(runnable1, 2100);

        YoYo.with(Techniques.SlideInLeft).delay(2000).duration(2000).repeat(0).playOn(iv_kidThinking);

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                iv_equal.setVisibility(View.VISIBLE);
                iv_kidSolution.setVisibility(View.VISIBLE);
            }
        };
        Handler handler2 = new Handler(Looper.getMainLooper());
        handler2.postDelayed(runnable2, 4100);

        YoYo.with(Techniques.SlideInLeft).delay(4000).duration(2000).repeat(0).playOn(iv_kidSolution);

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                tv_appTitle.setVisibility(View.VISIBLE);
            }
        };
        Handler handler3 = new Handler(Looper.getMainLooper());
        handler3.postDelayed(runnable3, 6100);

        YoYo.with(Techniques.SlideInRight).delay(6000).duration(2000).repeat(0).playOn(tv_appTitle);

        Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                    startActivity(intent);
            }
        };
        Handler handler4 = new Handler(Looper.getMainLooper());
        handler4.postDelayed(runnable4, 8100);

    }
}