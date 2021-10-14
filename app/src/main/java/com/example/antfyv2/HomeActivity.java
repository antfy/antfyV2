package com.example.antfyv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;

/*
 * LINK TUTORIAL PORUGUÊS: https://www.youtube.com/watch?v=y0KNW133DY0&list=PLssIKrX2yyQGfnguom7FagidX6KYqv-S2
 * LINK TUTORIAL INGLÊS: https://www.youtube.com/watch?v=x1y4tEHDwk0
 * https://github.com/devunwired/accessory-samples/tree/master/BluetoothGatt
 * https://medium.com/@shahar_avigezer/bluetooth-low-energy-on-android-22bc7310387a
*/


public class HomeActivity extends AppCompatActivity {

    Button btConectar, btMedir;

    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;

    ConnectedThread connectedThread;

    boolean conexao = false;
    private static String MAC = null;

    BluetoothAdapter bluetoothAdapter = null;
    BluetoothDevice myDevice = null;
    BluetoothSocket mySocket = null;

    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    // HEART_RATE_SERVICE_UUID = convertFromInteger(0x180D);
    // HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A37);
    // HEART_RATE_CONTROL_POINT_CHAR_UUID = convertFromInteger(0x2A39);




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
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if(conexao) {
                    // desconectar
                    try {
                        mySocket.close();
                        conexao = false;

                        btConectar.setText("Conectar");
                        Toast.makeText(getApplicationContext(), "Bluetooth desconectado.", Toast.LENGTH_SHORT).show();

                    } catch (IOException erro) {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro: " + erro, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // conectar
                    Intent abreLista = new Intent(HomeActivity.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO);
                }
            }
        });

        btMedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao) {
                    connectedThread.enviar("Medir");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado.", Toast.LENGTH_SHORT).show();
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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SOLICITA_ATIVACAO:
                if(resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "O bluetooth foi ativado.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "O bluetooth não foi ativado, o app será encerrado.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case SOLICITA_CONEXAO:
                if(resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                    // Toast.makeText(getApplicationContext(), "MAC final: " + MAC, Toast.LENGTH_SHORT).show();

                    myDevice = bluetoothAdapter.getRemoteDevice(MAC);

                    try {
                        mySocket = myDevice.createRfcommSocketToServiceRecord(MEU_UUID);
                        mySocket.connect();
                        conexao = true;

                        connectedThread = new ConnectedThread(mySocket);
                        connectedThread.start();

                        btConectar.setText("Desconectar");
                        Toast.makeText(getApplicationContext(), "Você foi conectado com: " + MAC, Toast.LENGTH_SHORT).show();

                    } catch (IOException erro) {
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro: " + erro, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o endereço MAC.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int numBytes; // bytes returned from read()

            /*
            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity.
                    Message readMsg = handler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
             */
        }

        // Call this from the main activity to send data to the remote device.
        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();

            try {
                mmOutStream.write(msgBuffer);

            } catch (IOException e) {

            }
        }

    }


    /*
    public UUID convertFromInteger(int i) {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }
    */
}
