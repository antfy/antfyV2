package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TELA INICIAL APENAS COM A BARRA DE NAVEGAÇÃO
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);


        // ENTRAR ACTIVITY
        Button btEntre = findViewById(R.id.btEntre);
        btEntre.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EntrarActivity.class)));

        // CADASTRO ACTIVITY
        Button btCadastrese =  findViewById(R.id.btCadastrese);
        btCadastrese.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CadastroActivity.class)));
    }
}
