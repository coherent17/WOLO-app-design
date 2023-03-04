package com.example.wolo_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView BluetoothStatus, PairedTextView;
    ImageView BluetoothIcon;
    Button OnButton, offButton, discoverButton, pairButton;

    BluetoothAdapter myBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BluetoothStatus = findViewById(R.id.BluetoothStatus);
        PairedTextView = findViewById(R.id.PairedTextView);
        BluetoothIcon = findViewById(R.id.BluetoothIcon);
        OnButton = findViewById(R.id.OnButton);
        offButton = findViewById(R.id.offButton);
        discoverButton = findViewById(R.id.discoverButton);
        pairButton = findViewById(R.id.pairButton);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //check if bluetooth is available or not
        if(myBluetoothAdapter == null){
            BluetoothStatus.setText("Bluetooth is not available");
        }
        else{
            BluetoothStatus.setText("Bluetooth is available");
        }

        //set image according to bluetooth status (on/off)
        if(myBluetoothAdapter.isEnabled()){
            BluetoothIcon.setImageResource(R.drawable.ic_action_on);
        }
        else{
            BluetoothIcon.setImageResource(R.drawable.ic_action_off);
        }

        //Button setup
        OnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myBluetoothAdapter.isEnabled()){
                    showToast("Turning on Bluetooth...");

                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        {
                            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                            return;
                        }
                    }
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
                else{
                    showToast("Bluetooth is already on");
                }

            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetoothAdapter.isEnabled()){
                    myBluetoothAdapter.disable();
                    showToast("Turning Bluetooth off");
                    BluetoothIcon.setImageResource(R.drawable.ic_action_off);
                }
                else{
                    showToast("Bluetooth is already off");
                }
            }
        });

        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myBluetoothAdapter.isDiscovering()){
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        pairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetoothAdapter.isEnabled()){
                    PairedTextView.setText("Paired Devices");
                    Set<BluetoothDevice> devices = myBluetoothAdapter.getBondedDevices();
                    for(BluetoothDevice device: devices){
                        PairedTextView.append("\nDevice" + device.getName() + ", " + device);
                    }
                }
                else{
                    //bluetooth is off -> can't get paired devices
                    showToast("Turn on bluetooth to get paired devices");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if(requestCode == RESULT_OK){
                    //bluetooth is on
                    BluetoothIcon.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }
                else{
                    //user denied to turn bluetooth on
                    showToast("Couldn't turn on the bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //show toast message
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}