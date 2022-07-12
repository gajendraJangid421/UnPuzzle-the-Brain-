package com.example.puzzlethebrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame1 extends AppCompatActivity {

    private int emptyX=3, emptyY=3, stepsCount=0, timeCount=0;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;
    private TextView tv_steps, tv_time;
    private Timer timer;
    private Button bt_shuffle, bt_resume;
    private boolean isTimeRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sliding Number Game");

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToViews();
    }

    private void loadDataToViews(){
        emptyX = 3;
        emptyY = 3;

        for(int i=0; i<group.getChildCount()-1; i++){
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundResource(android.R.drawable.btn_default);
        }

        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, R.color.blankButton));
    }

    private void generateNumbers(){
        int n=15;
        Random random = new Random();

        while(n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }

        if(!isSolvable()){
            generateNumbers();
        }
    }

    private boolean isSolvable(){
        int countInversions=0;

        for (int i=0;i<15;i++){
            for(int j=0;j<i;j++){
                if(tiles[j]>tiles[i]){
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }

    private void loadNumbers(){
        tiles = new int[16];

        for(int i=0; i<group.getChildCount()-1;i++){
            tiles[i] = i+1;
        }
    }

    private void loadTimer(){
        isTimeRunning = true;
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCount++;
                setTime(timeCount);
            }
        }, 1000, 1000);
    }

    private void setTime(int timeCount){
        int second = timeCount % 60;
        int hour = timeCount / 3600;
        int minute = (timeCount - hour * 3600) / 60;

        tv_time.setText(String.format("Time: %02d:%02d:%02d", hour, minute, second));
    }

    private void loadViews(){
        group = findViewById(R.id.group);
        tv_steps = findViewById(R.id.tv_steps);
        tv_time = findViewById(R.id.tv_time);
        bt_shuffle = findViewById(R.id.bt_shuffle);
        bt_resume = findViewById(R.id.bt_resume);

        loadTimer();
        buttons = new Button[4][4];

        for(int i=0;i<group.getChildCount();i++){
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
        }

        bt_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNumbers();
                loadDataToViews();
            }
        });

        bt_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimeRunning){
                    timer.cancel();
                    bt_resume.setText("Resume");
                    isTimeRunning = false;
                    for(int i=0; i<group.getChildCount(); i++){
                        buttons[i/4][i%4].setClickable(false);
                    }
                }else {
                    loadTimer();
                    bt_resume.setText("Pause");
                    for(int i=0; i<group.getChildCount(); i++){
                        buttons[i/4][i%4].setClickable(true);
                    }
                }
            }
        });
    }

    public void buttonClick(View view){

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();

        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if ((Math.abs(emptyX-x)==1 && emptyY==y) || (Math.abs(emptyY-y)==1 && emptyX==x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.blankButton));
            emptyX=x;
            emptyY=y;
            stepsCount++;
            tv_steps.setText("Steps: " + stepsCount);
            checkWin();
        }
    }

    public void checkWin(){
        boolean isWin = false;

        if (emptyX==3 && emptyY==3){
            for (int i=0;i<group.getChildCount()-1;i++){
                if (buttons[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                    isWin=true;
                }else{
                    isWin=false;
                    break;
                }
            }
        }

        if (isWin){
            Toast.makeText(this, "You Win!!!\nSteps: " + stepsCount, Toast.LENGTH_SHORT).show();

            for(int i=0;i<group.getChildCount();i++){
                buttons[i/4][i%4].setClickable(false);
            }

            timer.cancel();
            bt_shuffle.setClickable(false);
            bt_resume.setClickable(false);
        }
    }
}