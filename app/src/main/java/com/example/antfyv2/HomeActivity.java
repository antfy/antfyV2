package com.example.antfyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

// LINK: https://www.youtube.com/watch?v=y0KNW133DY0
// LINK: https://www.youtube.com/watch?v=x1y4tEHDwk0 (10:09)
// https://github.com/devunwired/accessory-samples/tree/master/BluetoothGatt
// LINK: https://medium.com/@shahar_avigezer/bluetooth-low-energy-on-android-22bc7310387a

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "BluetoohGattActivity";
    private static final String DEVICE_NAME = "SensorTag";

    // TEMPERATURE SERVICE
    // HEART RATE SERVICE

    private BluetoothAdapter mBluetoothAdapter;
    private SparseArray<BluetoothDevice> mDevice;
    private BluetoothGatt mConnectedGatt;

    private TextView mTemperatura, mBatimento;

    private ProgressDialog mProgress;


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



        mTemperatura = (TextView) findViewById(R.id.txtTemp);
        mBatimento = (TextView) findViewById(R.id.txtBatimento);

        // BLUETOOTH MANAGER
        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mDevices = new SparseArray<BluetoothDevice>();

        // PROGRESS
        mProgress = new ProgressDialog(this);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);



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
    protected void onResume() {
        super.onResume();

        // verifica bluetooth habilitado
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            // bluetooth is disabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            finish();
            return;
        }

        // verifica suporte para bluetooth low energy
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        clearDisplayValues();
    }


    @Override
    protected void onPause() {
        super.onPause();

        // dialog hidden
        mProgress.dismiss();

        // cancel any scans in progress
        mHandler.removeCallbacks(mStopRunnable);
        mHandler.removeCallbacks(mStartRunnable);
        mBluetoothAdapter.stopLeScan(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // disconnect from any active tag connection
        if (mConnectedGatt != null) {
            mConnectedGatt.disconnect();
            mConnectedGatt = null;
        }
    }


}
