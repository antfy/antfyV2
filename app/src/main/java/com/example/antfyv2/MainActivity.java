package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btEntre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tela inicial apenas com barra de notificação
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);

        //ir EntrarActivity
        btEntre = findViewById(R.id.btEntre);
        btEntre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent
                startActivity(new Intent(getApplicationContext(), EntrarActivity.class));
            }
        });
    }
}
