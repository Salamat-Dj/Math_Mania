package com.example.mymathe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.mymathe.levels.GameLevels;
import com.example.mymathe.levels.GameLevelsDivide;
import com.example.mymathe.levels.GameLevelsMultiplication;
import com.example.mymathe.levels.GameLevelsSubtract;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    Button Start_addition;
    Button Start_subtraction;
    Button Start_multi;
    Button Start_divide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Start_addition = findViewById(R.id.buttonAddStart);
        Start_subtraction = findViewById(R.id.buttonSubStart);
        Start_multi = findViewById(R.id.buttonMultiStart);
        Start_divide = findViewById(R.id.buttonDivStart);

        Start_addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameLevels.class);
                startActivity(intent);
                finish();
            }
        });

        Start_subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameLevelsSubtract.class);
                startActivity(intent);
                finish();
            }
        });

        Start_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameLevelsMultiplication.class);
                startActivity(intent);
                finish();
            }
        });

        Start_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameLevelsDivide.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(getBaseContext(),"Click again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}