package com.example.antfyv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

/*
 * LINK TUTORIAL PORUGUÊS: https://www.youtube.com/watch?v=y0KNW133DY0&list=PLssIKrX2yyQGfnguom7FagidX6KYqv-S2
 * LINK TUTORIAL INGLÊS: https://www.youtube.com/watch?v=x1y4tEHDwk0
 * https://github.com/devunwired/accessory-samples/tree/master/BluetoothGatt
 * https://medium.com/@shahar_avigezer/bluetooth-low-energy-on-android-22bc7310387a
*/


public class HomeActivity extends AppCompatActivity {

    Button btConectar, btMedir;

    boolean conexao = false;
    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;
    BluetoothAdapter bluetoothAdapter = null;



    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home);
        setProgressBarIndeterminate(true);

        // ACTIONBAR (NAVIGATION OFF)
        Objects.requireNonNull(getSupportActionBar()).setTitle("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHOME");
        View decorView = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOpcoes);




        // BLUETOOTH
        btConectar = (Button) findViewById(R.id.btConectar);
        btMedir = (Button) findViewById(R.id.btMedir);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui bluetooh.", Toast.LENGTH_SHORT).show();
        } else if(!bluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }

        btConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao) {
                    // desconectar
                } else {
                    // conectar
                    Intent abreLista = new Intent(HomeActivity.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO);
                }
            }
        });




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

        // NOTÍCIAS ACTIVITY
        Button btNoticias = findViewById(R.id.btNoticias);
        btNoticias.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NoticiasActivity.class)));

        // NOTIFICAÇÃO ACTIVITY
        ImageView btNotify = findViewById(R.id.btNotify);
        btNotify.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NotifyActivity.class)));

        // PERFIL ACTIVITY
        ImageView btPerfil = findViewById(R.id.btPerfil);
        btPerfil.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PerfilActivity.class)));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SOLICITA_ATIVACAO:
                if(resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "O bluetooth foi ativado.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "O bluetooth não foi ativado, o app será encerrado.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }
}
