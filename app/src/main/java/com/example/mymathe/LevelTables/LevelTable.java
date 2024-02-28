package com.example.mymathe.LevelTables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymathe.Games.Game;
import com.example.mymathe.Games.GameDivide;
import com.example.mymathe.R;
import com.example.mymathe.levels.GameLevels;
import com.example.mymathe.levels.GameLevelsDivide;

import java.util.ArrayList;
import java.util.Random;

public class LevelTable extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    Button startBtn;
    TextView TimeTextView;
    TextView ScoreTextView;
    TextView AlertTextView;
    TextView QuestionTextView;
    TextView FinalScoreTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button btnExit;
    CountDownTimer countDownTimer;
    ConstraintLayout constraintLayout;
    ConstraintLayout lastLayout;
    Button buttonPlayAgain;


    Random random =new Random();
    int number1;
    int number2;
    int indexOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int points = 0;
    int totalQuestions = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startBtn=(Button)findViewById(R.id.btnStart);

        TimeTextView = findViewById(R.id.TimeTextView);
        ScoreTextView = findViewById(R.id.ScoreTextView);
        FinalScoreTextView=findViewById(R.id.FinalscoreTextView);
        AlertTextView = findViewById(R.id.AlertTextView);
        QuestionTextView =findViewById(R.id.QuestionTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonPlayAgain=findViewById(R.id.buttonPlayAgain);
        btnExit = findViewById(R.id.btnExit);

        constraintLayout=findViewById(R.id.quizUi);
        lastLayout=findViewById(R.id.lastUi);

        lastLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("SetTextI18n")
    public void NextQuestion(){
        number1 = random.nextInt(10);
        number2 = random.nextInt(10);
        QuestionTextView.setText(number1 + " + " + number2 + " = ?");

        indexOfCorrectAnswer = random.nextInt(4);
        answers.clear();
        for(int i = 0; i<4; i++){

            if(indexOfCorrectAnswer == i){
                answers.add(number1+number2);
            }else {
                int wrongAnswer = random.nextInt(20);
                while(wrongAnswer==number1+number2){

                    wrongAnswer = random.nextInt(20);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void optionSelect(View view){
        totalQuestions++;
        if(Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())){
            points++;
            AlertTextView.setText("Correct");

        }else {
            AlertTextView.setText("Wrong");
        }

        ScoreTextView.setText("Score: " + Integer.toString(points));

        NextQuestion();
    }

    public void playAgain(View view){
        points=0;
        totalQuestions=0;
        ScoreTextView.setText("Score: " + Integer.toString(points));
        countDownTimer.start();
        lastLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(LevelTable.this, GameLevels.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });
    }

    public void start(View view) {
        startBtn.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        NextQuestion();
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeTextView.setText("Time: " + String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                TimeTextView.setText("Time Up!");
                FinalScoreTextView.setText("Score: " + Integer.toString(points));
                constraintLayout.setVisibility(View.INVISIBLE);
                lastLayout.setVisibility(View.VISIBLE);

                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Intent intent = new Intent(LevelTable.this,GameLevels.class);
                            startActivity(intent);finish();
                        }catch (Exception e){

                        }
                    }
                });
            }
        }.start();
    }

    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            Intent intent = new Intent(LevelTable.this, GameLevels.class);
            startActivity(intent);
            finish();
            return;
        }else{
            backToast = Toast.makeText(getBaseContext(),"Click again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}