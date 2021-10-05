package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;
import java.util.UUID;

public class SentindoActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentindo);

        // ACTIONBAR (NAVIGATION OFF)
        Objects.requireNonNull(getSupportActionBar()).setTitle("\t\t\t\t\t\t\t\t\t\t\tSENTINDO");
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);

        // LINKS
        // https://developer.android.com/guide/topics/connectivity/bluetooth?hl=pt-br
        // https://pt.stackoverflow.com/questions/104127/como-obter-valor-do-campo-html-atrav%C3%A9s-do-webview-usando-javascript
        // https://pt.stackoverflow.com/questions/153041/como-recuperar-dados-de-um-form-html-aberto-em-uma-webview
        // https://medium.com/@shahar_avigezer/bluetooth-low-energy-on-android-22bc7310387a
        // https://developer.android.com/training/notify-user/build-notification?hl=pt-br


        // WEBVIEW
        WebView webview2 = findViewById(R.id.buscaSintoma);
        webview2.setWebViewClient(new WebViewClient());
        webview2.getSettings().setJavaScriptEnabled(true);
        webview2.loadUrl("https://antfy.github.io/buscamedica.github.io/");
    }
}