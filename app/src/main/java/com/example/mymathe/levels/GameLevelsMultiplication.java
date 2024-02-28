package com.example.mymathe.levels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymathe.Games.GameMultiplication;
import com.example.mymathe.LevelTF.LevelTrueFalseMultiplication;
import com.example.mymathe.LevelTables.LevelTableMultiplication;
import com.example.mymathe.MainActivity;
import com.example.mymathe.R;

public class GameLevelsMultiplication extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_levels_multiplication);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(GameLevelsMultiplication.this, MainActivity.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

        Button btnInput = (Button) findViewById(R.id.btnInput);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(GameLevelsMultiplication.this, GameMultiplication.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

        Button btnLearn = (Button) findViewById(R.id.btnLearn);
        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(GameLevelsMultiplication.this, LevelTableMultiplication.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

        Button btnTrueFalse = (Button) findViewById(R.id.btnTrueFalse);
        btnTrueFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(GameLevelsMultiplication.this, LevelTrueFalseMultiplication.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(GameLevelsMultiplication.this,MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception e){

        }
    }
}