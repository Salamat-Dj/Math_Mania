package com.example.mymathe.LevelTF;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymathe.Games.GameDivide;
import com.example.mymathe.LevelTables.LevelTable;
import com.example.mymathe.MainActivity;
import com.example.mymathe.R;
import com.example.mymathe.Results.Result;
import com.example.mymathe.levels.GameLevels;
import com.example.mymathe.levels.GameLevelsDivide;

import java.util.Random;

public class LevelTrueFalse extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    Dialog dialogend;
    TextView txtViewQuestion;
    TextView txtViewResult;
    TextView txtTime;
    TextView txtScore;
    ImageButton btnCorrect;
    ImageButton btnIncorrect;
    boolean isResultCorrect;
    int seconds = 29;
    private int score = 0;
    private boolean stopTimer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Вызов диалогового окна в конец игры
        dialogend = new Dialog(this); //создаем новое диалоговое окно
        dialogend.requestWindowFeature(Window.FEATURE_NO_TITLE); //скрываем заголовок
        dialogend.setContentView(R.layout.enddialog); //путь к макету диалогового окна
        dialogend.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // произрачный фон диалогового окна
        dialogend.setCancelable(false);

        //Кнопка которая закрывает диалоговое окно - начало
        Button btnExitMenu = (Button)dialogend.findViewById(R.id.btnExitMenu);
        btnExitMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Обрабатываем нажатия кнопки - начало
                try{
                    //Вернуться назад к выбору уровня - начало
                    Intent intent = new Intent(LevelTrueFalse.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                    //Вернуться назад к выбору уровня - конец
                }catch (Exception e){
                    //Здесь кода не будет
                }
                dialogend.dismiss();
                //Обрабатываем нажатия кнопки - конец
            }
        });
        Button btnReset = (Button)dialogend.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Обрабатываем нажатия кнопки - начало
                try{
                    //Вернуться назад к выбору уровня - начало
                    Intent intent = new Intent(LevelTrueFalse.this, LevelTrueFalse.class);
                    startActivity(intent);
                    finish();
                    //Вернуться назад к выбору уровня - конец
                }catch (Exception e){
                    //Здесь кода не будет
                }
                dialogend.dismiss();
                //Обрабатываем нажатия кнопки - конец
            }
        });


        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewResult = (TextView) findViewById(R.id.txtViewResult);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtScore = (TextView) findViewById(R.id.txtScore);
        btnCorrect = (ImageButton) findViewById(R.id.btnCorrect);
        btnIncorrect = (ImageButton) findViewById(R.id.btnIncorrect);
        timer();
        btnCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(true);
            }
        });
        btnIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(false);
            }
        });
        generateQuestion();
    }

    private void generateQuestion() {
        isResultCorrect = true;
        Random random = new Random();
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        int result = number1 + number2;
        float f = random.nextFloat();
        if (f > 0.5f) {
            result = random.nextInt(100);
            isResultCorrect = false;
        }
        txtViewQuestion.setText(number1 + " + " + number2);
        txtViewResult.setText(" = " + result);
    }

    public void verifyAnswer(boolean answer) {
        if (answer == isResultCorrect) {
            score += 5;
            txtScore.setText("SCORE: " + score + " | 30");
        }
        else {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
        if(isResultCorrect && answer){
            btnCorrect.setBackgroundResource(R.color.green);
            btnCorrect.setEnabled(false);
        }
        else if(!isResultCorrect && !answer) {
            btnIncorrect.setBackgroundResource(R.color.green);
            btnIncorrect.setEnabled(false);
        }
        else if(isResultCorrect && !answer){
            btnIncorrect.setBackgroundResource(R.color.red);
            btnIncorrect.setEnabled(false);
        }
        else{
            btnCorrect.setBackgroundResource(R.color.red);
            btnCorrect.setEnabled(false);
        }
        if(score == 30){
            stopTimer = true;
            dialogend.show();
        }
        stopTimer = true;
        timer2();
    }

    private void timer2(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnCorrect.setBackgroundResource(R.color.purple);
                btnIncorrect.setBackgroundResource(R.color.purple);
                btnIncorrect.setEnabled(true);
                btnCorrect.setEnabled(true);
                stopTimer = false;
                timer();
                generateQuestion();
            }
        }, 1000);
    }

    private void timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (seconds < 0) {
                    stopTimer = true;
                    ImageView previewimg = (ImageView)dialogend.findViewById(R.id.previewimg);
                    previewimg.setImageResource(R.drawable.gameover1);
                    TextView textdescription = (TextView) dialogend.findViewById(R.id.textdescription);
                    textdescription.setText("Unfortunately, you didn't score enough points :(");
                    dialogend.show();
                }
                if (stopTimer == false && seconds >= 0) {
                    handler.postDelayed(this, 2000);
                    txtTime.setText("TIME: " + seconds);
                    seconds--;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer = false;
        finish();
    }

    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            stopTimer = true;
            backToast.cancel();
            Intent intent = new Intent(LevelTrueFalse.this, GameLevels.class);
            startActivity(intent);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Click again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}