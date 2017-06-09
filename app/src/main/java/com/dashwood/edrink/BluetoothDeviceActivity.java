package com.dashwood.edrink;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class BluetoothDeviceActivity extends AppCompatActivity {

    LottieAnimationView search;
    static int anim_status = 0;
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device);

        search = (LottieAnimationView) findViewById(R.id.search_anim);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anim_status == 0)
                {
                    search.playAnimation();
                    anim_status = 1;
                }
                else
                {
                    search.cancelAnimation();
                    anim_status = 0;
                }

            }
        });
    }
}
