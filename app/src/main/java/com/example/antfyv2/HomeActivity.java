package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    int ATIVA_BLUETOOTH = 1;
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
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui bluetooh", Toast.LENGTH_SHORT).show();
        } else if(!bluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, ATIVA_BLUETOOTH);
        }




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

}
