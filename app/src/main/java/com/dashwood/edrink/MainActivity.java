package com.dashwood.edrink;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Interpolator;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView water_anim;
    Button goto_profile, goto_login, connect, disconnect;
    Typeface typeface_regular;
    TextView water_percent;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    static boolean isConnected = false;
    ProgressDialog progress;
    String address = null;
    BluetoothSocket btSocket = null;
    BluetoothAdapter bluetoothAdapter = null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    InputStream inStream = null;
    byte delimiter = 10;
    boolean stopWorker = false;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    android.os.Handler handler;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeface_regular = TypefaceProvider.getTypeFace(getApplicationContext(), "Circular_book.ttf");

        water_anim = (LottieAnimationView) findViewById(R.id.water_loading);
        water_percent = (TextView) findViewById(R.id.water_percent);
        water_percent.setTypeface(typeface_regular);
        goto_profile = (Button) findViewById(R.id.profile_btn);
        goto_profile.setTypeface(typeface_regular);
        goto_login = (Button) findViewById(R.id.login_btn);
        goto_login.setTypeface(typeface_regular);
        connect = (Button) findViewById(R.id.btn_connect);
        connect.setTypeface(typeface_regular);
        disconnect = (Button) findViewById(R.id.btn_disconnect);
        disconnect.setTypeface(typeface_regular);


        Intent get_device = getIntent();
        address = get_device.getStringExtra(BluetoothDeviceActivity.EXTRA_ADDRESS);

        if (address == null)
        {
            Toast.makeText(getApplicationContext(), "HC-05 NOT Connected", Toast.LENGTH_SHORT).show();

        }
        else
        {
            new ConnectBT().execute();
            connect.setVisibility(View.INVISIBLE);
            disconnect.setVisibility(View.VISIBLE);
        }



        handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        byte[] reading = (byte[]) msg.obj;
                        String string_input = new String(reading, 0, msg.arg1);
                        sb.append(string_input);
                        int endOfLineIndex = sb.indexOf(";");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                            sb.delete(0, sb.length());
                            water_percent.setText(sbprint);

                        }
                        Log.d("TAG", "String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;


                }
            }
        };


        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            goto_login.setVisibility(View.VISIBLE);
            goto_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
            //Toast.makeText(getApplicationContext(), "Account LOG OUT", Toast.LENGTH_SHORT).show();
        } else {
            goto_login.setText("LOG OUT");
            goto_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    goto_login.setText("LOGIN");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    Toast.makeText(getApplicationContext(), "未登入", Toast.LENGTH_SHORT).show();
                }
            }
        };

        goto_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PersonalDataActivity.class));
            }
        });


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, BluetoothDeviceActivity.class));
                finish();

            }
        });

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
                disconnect.setVisibility(View.INVISIBLE);
                connect.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "HC-05 DISCONNECTED", Toast.LENGTH_SHORT).show();

            }
        });



    }

    /*private void sendSignal ( String number ) {
        if ( btSocket != null ) {
            try {

            } catch (IOException e) {

            }
        }
    }*/

    private void Disconnect () {
        if ( btSocket!=null ) {
            try {
                btSocket.close();
            } catch(IOException e) {

            }
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( btSocket==null || !isConnected ) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                Toast.makeText(getApplicationContext(), "Connection Failed. Try again?", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                isConnected = true;

            }

            progress.dismiss();
            beginListenForData();

        }
    }


    public void beginListenForData()   {
        try {
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
        }

        Thread workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inStream.available();
                        int bytes;
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];


                            while(true)
                            {
                                try {
                                    bytes = inStream.read(packetBytes);
                                    handler.obtainMessage(1, bytes, -1 , packetBytes).sendToTarget();
                                }
                                catch (IOException e)
                                {
                                    break;
                                }
                            }

                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        water_anim.playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        water_anim.cancelAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        water_anim.cancelAnimation();
    }
}
