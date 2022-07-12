package com.example.puzzlethebrain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame5 extends AppCompatActivity {

    static final int[][] idArray = {{R.id.et_00, R.id.et_01, R.id.et_02, R.id.et_03, R.id.et_04, R.id.et_05, R.id.et_06, R.id.et_07, R.id.et_08},
            {R.id.et_10, R.id.et_11, R.id.et_12, R.id.et_13, R.id.et_14, R.id.et_15, R.id.et_16, R.id.et_17, R.id.et_18},
            {R.id.et_20, R.id.et_21, R.id.et_22, R.id.et_23, R.id.et_24, R.id.et_25, R.id.et_26, R.id.et_27, R.id.et_28},
            {R.id.et_30, R.id.et_31, R.id.et_32, R.id.et_33, R.id.et_34, R.id.et_35, R.id.et_36, R.id.et_37, R.id.et_38},
            {R.id.et_40, R.id.et_41, R.id.et_42, R.id.et_43, R.id.et_44, R.id.et_45, R.id.et_46, R.id.et_47, R.id.et_48},
            {R.id.et_50, R.id.et_51, R.id.et_52, R.id.et_53, R.id.et_54, R.id.et_55, R.id.et_56, R.id.et_57, R.id.et_58},
            {R.id.et_60, R.id.et_61, R.id.et_62, R.id.et_63, R.id.et_64, R.id.et_65, R.id.et_66, R.id.et_67, R.id.et_68},
            {R.id.et_70, R.id.et_71, R.id.et_72, R.id.et_73, R.id.et_74, R.id.et_75, R.id.et_76, R.id.et_77, R.id.et_78},
            {R.id.et_80, R.id.et_81, R.id.et_82, R.id.et_83, R.id.et_84, R.id.et_85, R.id.et_86, R.id.et_87, R.id.et_88}};


    EditText[][] edit_text = new EditText[9][9];
    private static TextView tv_time, tv_win;
    private static Timer timer;
    private static boolean isTimeRunning = false;
    private int timeCount = 0;

    static int[] tiles;
    static int[][] tempSudoku = new int[9][9];
    Button bt_solved, bt_solution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game5);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sudoku");

        loadNumbers();
        fillValues();
        loadDataToViews();
        loadTimer();

        tv_time = findViewById(R.id.tv_time);
        tv_win = findViewById(R.id.tv_win);

        bt_solved = findViewById(R.id.bt_solved);
        bt_solution = findViewById(R.id.bt_solution);


        bt_solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dt1 = "ColorStateList{mThemeAttrs=nullmChangingConfigurations=0mStateSpecs=[[]]mColors=[-16776961]mDefaultColor=-16776961}";

                timer.cancel();
                bt_solved.setEnabled(false);
                int flag = 0;

                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        ColorStateList st = edit_text[i][j].getTextColors();
                        String val = "" + edit_text[i][j].getText();
                        boolean s = dt1.equals("" + st);
                        if(s && isSafe(i, j, edit_text, val)){
                            edit_text[i][j].setTextColor(Color.RED);
                            flag = 1;
                        }
                    }
                }

                if(flag==0){
                    tv_win.setText("Kudos!! You solved the Sudoku!!!");
                    tv_win.setTextColor(Color.GREEN);
                }else{
                    tv_win.setText("Oops! Wrongly Solved!!");
                    tv_win.setTextColor(Color.RED);
                }
            }
        });


        bt_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityGame5.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you really want to see the solution?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {
                        // When the user click yes button
                        // then app will close
                        //finish();
                        timer.cancel();
                        bt_solved.setEnabled(false);
                        bt_solution.setEnabled(false);
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                if (mat[i][j] == 0) {
                                    edit_text[i][j].setText(Integer.toString(tempSudoku[i][j]));
                                    edit_text[i][j].setFocusableInTouchMode(false);
                                }
                            }
                        }
                    }
                }).setNegativeButton("No" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {
                        // If user click no
                        // then dialog box is canceled.
                        dialog.cancel();
                    }
                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }

//                timer.cancel();
//                bt_solved.setVisibility(View.GONE);
//                for (int i=0;i<9;i++){
//                    for (int j=0;j<9;j++){
//                        if(mat[i][j]==0){
//                            edit_text[i][j].setText(Integer.toString(tempSudoku[i][j]));
//                            edit_text[i][j].setFocusableInTouchMode(false);
//                        }
//                    }
//                }
//            }
        });
    }

    boolean isSafe(int row, int col, EditText[][] edit_text, String val){
        for(int i=0;i<9;i++){
            if(i!=col && ("" + edit_text[row][i].getText()).equals(val)){
                return true;
            }
            if(i!=row && ("" + edit_text[i][col].getText()).equals(val)){
                return true;
            }
            if((row!=3*(row/3) + i/3 || col!=3*(col/3) + i%3) && ("" + edit_text[3*(row/3) + i/3][3*(col/3) + i%3].getText()) == val){
                return true;
            }
        }

        return false;
    }

    void loadNumbers(){
        tiles = new int[9];

        for(int i=0; i<9;i++){
            tiles[i] = i+1;
        }
    }

    void loadDataToViews() {

        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                edit_text[i][j] = findViewById(idArray[i][j]);
                String editText = Integer.toString(mat[i][j]);
                if(!editText.equals("0")) {
                    edit_text[i][j].setText(editText);
                }

                if(editText.equals("0")){
                    edit_text[i][j].setFocusableInTouchMode(true);
                    edit_text[i][j].setTextColor(Color.BLUE);
                }
            }
        }

    }

    static void generateNumbers(){
        int n=9;
        Random random = new Random();

        while(n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }

    }

    static int[][] mat = new int[9][9];
    static int N=9; // number of columns/rows.
    static int SRN=3; // square root of N
    static int K=40; // No. Of missing digits

    // sudukoGenerator Generator
    public void fillValues()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);

        solvedSudoku();

        // Remove Randomly K digits to make game
        removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    static void fillDiagonal()
    {

        for (int i = 0; i<N; i=i+SRN){
            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
        }

    }

    // Fill a 3 x 3 matrix.
    static void fillBox(int row , int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    static int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    // Check if safe to put in cell
    static boolean CheckIfSafe(int i , int j , int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    static boolean unUsedInRow(int i, int num)
    {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    static boolean unUsedInCol(int j, int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // Returns false if given 3 x 3 block contains num.
    static boolean unUsedInBox(int rowStart , int colStart , int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // A recursive function to fill remaining
    // matrix
    static boolean fillRemaining(int i , int j)
    {
        //  System.out.println(i+" "+j);
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    public static void solvedSudoku(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                tempSudoku[i][j] = mat[i][j];
            }
        }
    }

    // Remove the K no. of digits to
    // complete game
    @SuppressLint("DefaultLocale")
    public static void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;

            // extract coordinates i  and j
            int i = (cellId/N);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            if (mat[i][j] != 0)
            {
                count--;
                mat[i][j] = 0;
            }
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

}