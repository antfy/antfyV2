package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Actionbar (Navigation Off)
        Objects.requireNonNull(getSupportActionBar()).setTitle("\t\t\t\t\t\t\t\tCADASTRE-SE");
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);
    }
}