package com.example.puzzlethebrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton bt_game1, bt_game2, bt_game3, bt_game4, bt_game5, bt_game6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_game1 = findViewById(R.id.bt_game1);
        bt_game2 = findViewById(R.id.bt_game2);
        bt_game3 = findViewById(R.id.bt_game3);
        bt_game4 = findViewById(R.id.bt_game4);
        bt_game5 = findViewById(R.id.bt_game5);
        bt_game6 = findViewById(R.id.bt_game6);

        bt_game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityGame1.class);
                startActivity(intent);
            }
        });

        bt_game2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ActivityGame2.class);
                startActivity(intent);
            }
        });

        bt_game3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ActivityGame3.class);
                startActivity(intent);
            }
        });

        bt_game4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ActivityGame4.class);
                startActivity(intent);
            }
        });

        bt_game5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ActivityGame5.class);
                startActivity(intent);
            }
        });

        bt_game6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ActivityGame6.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}