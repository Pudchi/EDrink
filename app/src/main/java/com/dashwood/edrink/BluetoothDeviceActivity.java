package com.dashwood.edrink;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class BluetoothDeviceActivity extends AppCompatActivity {

    LottieAnimationView search;
    //static int anim_status = 0;
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    //BluetoothLeScanner btScanner;
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    ListView bt_device_list;
    ArrayList<String> s = new ArrayList<String>();
    public static String EXTRA_ADDRESS = "device_address";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    //builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device);


        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> paired = btAdapter.getBondedDevices();

        search = (LottieAnimationView) findViewById(R.id.search_anim);
        bt_device_list = (ListView) findViewById(R.id.bt_device_list);
        for (BluetoothDevice bt : paired)
            s.add(bt.getName() + " : " + bt.getAddress());

        bt_device_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.my_list_item, s));
        bt_device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String device_info = ((TextView) view).getText().toString();
                String address = device_info.substring(device_info.length() - 17);
                Log.i("device", address);

                Intent i = new Intent(BluetoothDeviceActivity.this, MainActivity.class);
                i.putExtra(EXTRA_ADDRESS, address);
                startActivity(i);
            }
        });
    }
}



        /*if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect peripherals.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    }
                });
                builder.show();
            }
        }*/




        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anim_status == 0)
                {
                    search.playAnimation();
                    anim_status = 1;



                    //list_paired_Devices();
                }
                else
                {
                    search.cancelAnimation();
                    anim_status = 0;
                    /*if (btAdapter!= null && btAdapter.isEnabled())
                                                            {
                                                                btAdapter.disable();
                                                            }
                }

            }
        });*/
