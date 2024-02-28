package com.example.mymathe.Results;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymathe.Games.GameMultiplication;
import com.example.mymathe.LevelTF.LevelTrueFalseMultiplication;
import com.example.mymathe.R;
import com.example.mymathe.levels.GameLevelsMultiplication;

public class ResultMultiplication extends AppCompatActivity {

    TextView result;
    Button exit;
    Button restart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        result = findViewById(R.id.textViewScore);
        exit = findViewById(R.id.buttonExit);
        restart = findViewById(R.id.buttonRestart);

        TextView tv = (TextView) findViewById(R.id.score);
        int score = getIntent().getIntExtra("score", 0);
        if(score == 50)
        {
            TextView textView = (TextView) findViewById(R.id.textView2);
            textView.setText("YOU WIN");
        }
        tv.setText("Current Score : " + score);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultMultiplication.this, GameMultiplication.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultMultiplication.this, GameLevelsMultiplication.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(ResultMultiplication.this, GameLevelsMultiplication.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
}
