package com.example.lukasztrojok.hour6app;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothActivity extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BLUETOOTH = 0;
    private static final int REQUEST_DISCOVER_BLUETOOTH = 1;


    TextView bluetoothStatText;
    ImageView bluetooth;


    Switch swAll, swGetDownload;

    BluetoothAdapter blueAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        final ComponentName componentName = new ComponentName(BluetoothActivity.this, BluetoothReceiver.class);
        final PackageManager packageManager = this.getPackageManager();



        bluetoothStatText = findViewById(R.id.statBluetothText);

        bluetooth = findViewById(R.id.iconBlue);


        swAll = findViewById(R.id.swBtAll);
        swGetDownload = findViewById(R.id.swGetDownload);


        blueAdapter = BluetoothAdapter.getDefaultAdapter();


        if (blueAdapter == null) {
            bluetoothStatText.setText("Bluetooth not available");

            swAll.setChecked(false);

            bluetooth.setImageResource(R.mipmap.action_off_round);


        } else {
            bluetoothStatText.setText("Bluetooth available on this device");

        }

        if (blueAdapter.isEnabled()) {
            swAll.setChecked(true);

            bluetooth.setImageResource(R.mipmap.action_on_round);

        } else {


            if (!blueAdapter.isEnabled()) {
                swAll.setChecked(false);
                swGetDownload.setChecked(false);

                bluetooth.setImageResource(R.mipmap.action_off_round);

            }

        }
        if (packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            swGetDownload.setChecked(true);
        } else if (packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {

            swGetDownload.setChecked(false);
        }


        swAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                 if (isChecked) {
                                                     if (!blueAdapter.isEnabled()) {


                                                         showToast("Starting Bluetooth");

                                                         Intent intent = new Intent((BluetoothAdapter.ACTION_REQUEST_ENABLE));
                                                         startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);


                                                     } else {
                                                         showToast("Bluetooth is running");
                                                     }


                                                 } else {

                                                     if (blueAdapter.isEnabled()) {

                                                         blueAdapter.disable();
                                                         showToast("Disabling bluetooth");
                                                         bluetooth.setImageResource(R.mipmap.action_off_round);
                                                         swGetDownload.setChecked(false);

                                                         try {

                                                         } catch (Exception e) {
                                                         }


                                                     } else {

                                                         showToast("Bluetooth is off");
                                                     }


                                                 }


                                             }


                                         }


        );

        swGetDownload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    if (!blueAdapter.isEnabled()) {

                        showToast("Enable bluetooth first!");
                        swGetDownload.setChecked(false);

                    } else {

                        showToast("AUTO LOCATION ON");

                        packageManager.setComponentEnabledSetting(
                                componentName,
                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                PackageManager.DONT_KILL_APP);

                    }


                } else {
                    showToast("AUTO LOCATION OFF");


                    packageManager.setComponentEnabledSetting(
                            componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                    try {

                    } catch (Exception e) {
                    }

                }

            }
        });





/*

        blueOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!blueAdapter.isEnabled()){


                    showToast("Starting Bluetooth");

                    Intent intent = new Intent((BluetoothAdapter.ACTION_REQUEST_ENABLE));
                    startActivityForResult(intent,REQUEST_ENABLE_BLUETOOTH);


                }
                else{
                    showToast("Bluetooth is running");
                }


            }
        });
        */

/*

        blueDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!blueAdapter.isDiscovering()){

                    showToast("Your device will be discoverable");
                    Intent intent = new Intent (BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,REQUEST_DISCOVER_BLUETOOTH);

                }



            }
        });
*/
/*
        blueOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(blueAdapter.isEnabled()){

                    blueAdapter.disable();
                    showToast("Disabling bluetooth");
                    bluetooth.setImageResource(R.mipmap.action_off_round);

                }
                else{
                    showToast("Bluetooth is off");
                }


            }
        });
*/
/*

        bluePaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(blueAdapter.isEnabled()){
pairedText.setText("Paired Devices");
                    Set<BluetoothDevice>devices=blueAdapter.getBondedDevices();
                    for(BluetoothDevice device:devices){
                        pairedText.append("\nDevice: "+ device.getName()+" , " +device);

                    }


                }

                else{

                    showToast("Turn on bluetooth first!");







            }
        });


    }

        }
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {

            case REQUEST_ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                    bluetooth.setImageResource(R.mipmap.action_on_round);
                    showToast("Bluetooth on");

                } else {

                    showToast("Can't use bluetooth");

                }
                break;


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    protected void onPause() {
        try {

        } catch (Exception e) {

        }
        super.onPause();

    }


    protected void onDestroy() {
        try {

        } catch (Exception e) {

        }
        super.onDestroy();
    }

}

