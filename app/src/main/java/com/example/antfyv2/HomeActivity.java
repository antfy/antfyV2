package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ACTIONBAR (NAVIGATION OFF)
        Objects.requireNonNull(getSupportActionBar()).setTitle("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHOME");
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);



        // SENTINDO ACTIVITY
        Button btSentindo = findViewById(R.id.btSentindo);
        btSentindo.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SentindoActivity.class)));

        // MENSAGEM WHATSAPP
        Button btMensagem = findViewById(R.id.btMensagens);
        btMensagem.setOnClickListener(v -> {
            EditText telefone = findViewById(R.id.textTelefone);
            String wpp = "https://api.whatsapp.com/send?phone=" + telefone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(wpp));
            startActivity(intent);
        });

        // NOTIFICAÇÃO ACTIVITY
        ImageView btNotify = findViewById(R.id.btNotify);
        btNotify.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NotifyActivity.class)));

        // PERFIL ACTIVITY
        ImageView btPerfil = findViewById(R.id.btPerfil);
        btPerfil.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PerfilActivity.class)));



        // WEBVIEW
        WebView webview = findViewById(R.id.noticias);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://www.roche.com.br/pt/imprensa.html");
    }
}
