package com.example.mymathe.Games;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymathe.R;
import com.example.mymathe.Results.Result;
import com.example.mymathe.Results.ResultMultiplication;
import com.example.mymathe.levels.GameLevelsDivide;
import com.example.mymathe.levels.GameLevelsMultiplication;

import java.util.Locale;
import java.util.Random;

public class GameMultiplication extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    Dialog dialog;

    TextView score;
    TextView life;
    TextView time;
    TextView question;
    EditText answer;
    Button ok;
    Button next;

    Random random = new Random();
    int num1;
    int num2;

    int userAnswer;
    int correctAnswer;

    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;

    private static final long START_TIMER_IN_MS = 30000;

    Boolean timer_running;
    long time_left_in_ms = START_TIMER_IN_MS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Вызов диалогового окна в начале игры
        dialog = new Dialog(this); //создаем новое диалоговое окно
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //скрываем заголовок
        dialog.setContentView(R.layout.previewdialog); //путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // произрачный фон диалогового окна
        dialog.setCancelable(false);

        // Установливаем картинку в диалоговое окно - начало
        ImageView previewimg = (ImageView)dialog.findViewById(R.id.previewimg);
        previewimg.setImageResource(R.drawable.unnamedmult);
        // Установливаем картинку в диалоговое окно - конец

        //Кнопка которая закрывает диалоговое окно - конец
        Button btncontinue = (Button)dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); //Закырваем диалоговое окно
            }
        });
        //Кнопка "Продолжить" - конец

        dialog.show();

        score = findViewById(R.id.textViewScore);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        ok = findViewById(R.id.buttonOk);
        next = findViewById(R.id.buttonNext);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer = Integer.valueOf(answer.getText().toString());

                pauseTimer();

                if(userAnswer == correctAnswer)
                {
                    userScore = userScore + 10;
                    question.setText("Yay! Your Answer is Right!");
                    score.setText("" + userScore);
                }
                else
                {
                    userLife = userLife - 1;
                    question.setTextSize(40);
                    question.setText("Your Answer is Wrong!\nThe correct answer: " + correctAnswer);
                    life.setText("" + userLife);
                }
                next.setEnabled(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");

                resetTimer();

                if (userLife <= 0)
                {
                    Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameMultiplication.this, ResultMultiplication.class);

                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                }
                if(userScore == 50)
                {
                    Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameMultiplication.this, ResultMultiplication.class);

                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    gameContinue();
                }
                next.setEnabled(false);
            }
        });
    }

    //method containing game logic to ask questions
    public void gameContinue()
    {
        num1 = random.nextInt(20);
        num2 = random.nextInt(10);

        correctAnswer = num1 * num2;

        question.setTextSize(60);
        question.setText(num1 + " * " + num2 + " = ?");

        ok.setEnabled(false);
        ok.setEnabled(false);

        startTimer();
    }

    public void startTimer()
    {
        timer = new CountDownTimer(time_left_in_ms, 1000) {
            @Override
            public void onTick(long l) {
                //l = millis until finished
                time_left_in_ms = l;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                next.setEnabled(true);
                userLife = userLife - 1;
                life.setText("" + userLife);
                question.setText("Sorry, time sup");
            }
        }.start();

        timer_running = true;
    }

    public void updateText()
    {
        int second = (int)(time_left_in_ms / 1000) % 60;

        if(answer.getText().toString().isEmpty())
        {
            ok.setEnabled(false);
        }
        else
        {
            ok.setEnabled(true);
        }

        String time_left = String.format(Locale.getDefault(), "%02d", second);
        time.setText(time_left);

    }

    public void pauseTimer()
    {
        timer.cancel();
        timer_running = false;
    }

    public void resetTimer()
    {
        time_left_in_ms = START_TIMER_IN_MS;
        updateText();
    }

    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            Intent intent = new Intent(GameMultiplication.this, GameLevelsMultiplication.class);
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