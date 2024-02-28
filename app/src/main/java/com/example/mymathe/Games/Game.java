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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymathe.MainActivity;
import com.example.mymathe.R;
import com.example.mymathe.Results.Result;
import com.example.mymathe.levels.GameLevels;
import com.example.mymathe.levels.GameLevelsDivide;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

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

    //java-класс для генерации случайных чисел для игры
    Random random = new Random();
    int num1;
    int num2;

    //пользователь и правильный ответ
    int userAnswer;
    int correctAnswer;

    //жизнь пользователя и счет
    int userScore = 0;
    int userLife = 3;

    //таймер
    CountDownTimer timer;

    private static final long START_TIMER_IN_MS = 30000;

    //определяет, запущен ли таймер или нет
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

        score= findViewById(R.id.textViewScore);
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
                //сначала получаем ответ пользователя в виде строки, преобразованной в целое число
                userAnswer = Integer.valueOf(answer.getText().toString());

                //приостанавливает таймер, когда пользователь отвечает
                pauseTimer();

                //проверяем ответ пользователя на правильный ответ
                if(userAnswer == correctAnswer)
                {
                    //мы увеличили их счет и показали, что это было правильно
                    userScore = userScore + 10;
                    //""+ преобразует результат в целое значение
                    question.setText("Yay! Your Answer is Right!");
                    score.setText("" + userScore);
                }
                else
                {
                    //уменьшаем их жизнь и показываем, что это было неправильно
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
                //даст четкий ответ и сгенерирует новый вопрос
                answer.setText("");

                //сбрасывает таймер на следующий вопрос
                resetTimer();

                //если у нас заканчиваются жизни, мы открываем действие "Результат" и показываем результаты пользователя
                if (userLife == 0)
                {
                    Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Game.this, Result.class);

                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                }
                if(userScore == 50)
                {
                    Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Game.this, Result.class);

                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                }
                //если у нас все еще есть жизни, игра продолжается
                else
                {
                    gameContinue();
                }
            }
        });
    }

    //метод, содержащий игровую логику, чтобы задавать вопросы
    public void gameContinue()
    {
        //генерировать случайные числа от 0 до 100
        num1 = random.nextInt(20);
        num2 = random.nextInt(20);

        //генерировать ответ на вопрос
        correctAnswer = num1 + num2;

        //задает текст для вопроса в textview
        question.setTextSize(60);
        question.setText(num1 + " + " + num2 + " = ?");

        next.setEnabled(false);
        ok.setEnabled(false);

        //запускает таймер
        startTimer();
    }

    //метод таймера
    public void startTimer()
    {
        //обратный отсчет 20 секунд
        timer = new CountDownTimer(time_left_in_ms, 1000) {
            @Override
            public void onTick(long l) {
                //l = миллисекунд до завершения
                time_left_in_ms = l;
                updateText();
            }

            //если время истекает, мы останавливаем таймер / перезапускаем его до тех пор, пока жизни не закончатся
            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                next.setEnabled(true);
                userLife = userLife - 1;
                life.setText("" + userLife);
                question.setText("Sorry, time up");
            }
        }.start();

        timer_running = true;
    }

    //методы таймера для обеспечения функциональности таймера
    public void updateText()
    {
        //преобразуйте оставшиеся секунды в целые числа
        int second = (int)(time_left_in_ms / 1000) % 60;
        if(answer.getText().toString().isEmpty())
        {
            ok.setEnabled(false);
        }
        else
        {
            ok.setEnabled(true);
        }
        //затем мы преобразуем это значение в строку в виде двух цифр (02d) и показываем его в TextView
        String time_left = String.format(Locale.getDefault(), "%02d", second);
        time.setText(time_left);

    }

    public void pauseTimer()
    {
        //таймер отмены
        timer.cancel();
        timer_running = false;
    }

    public void resetTimer()
    {
        //таймер сброса
        time_left_in_ms = START_TIMER_IN_MS;
        updateText();
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            Intent intent = new Intent(Game.this, GameLevels.class);
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
