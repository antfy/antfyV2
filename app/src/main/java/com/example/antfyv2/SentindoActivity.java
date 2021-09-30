package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class SentindoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentindo);

        // ACTIONBAR (NAVIGATION OFF)
        Objects.requireNonNull(getSupportActionBar()).setTitle("\t\t\t\t\t\t\t\t\t\t\tSENTINDO");
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);

        // https://pt.stackoverflow.com/questions/104127/como-obter-valor-do-campo-html-atrav%C3%A9s-do-webview-usando-javascript
        // https://pt.stackoverflow.com/questions/153041/como-recuperar-dados-de-um-form-html-aberto-em-uma-webview
        // https://medium.com/@shahar_avigezer/bluetooth-low-energy-on-android-22bc7310387a
        // https://developer.android.com/training/notify-user/build-notification?hl=pt-br
    }
}