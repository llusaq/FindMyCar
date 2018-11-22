package com.example.lukasztrojok.hour6app;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {
    //private final static String BT_DISCONNECT_TAG = "BT_DISCONNECTED";
    public static final String ACTION_ACL_DISCONNECTED = "ACTION_ACL_DISCONNECTED";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        Log.d("TestLog0", "Bluetooth Disconnected  STARTED INTENT");


        if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {


            Log.d("TestLog3", "Bluetooth disconnected REAL");


            Toast.makeText(context, "BT Device Disconnected", Toast.LENGTH_LONG).show();



/*
           Intent startIntent = new Intent(context, CoolMenu.class);
           context.startActivity(startIntent);

*/

            Intent startActivity = new Intent();
            startActivity.setClassName("com.example.lukasztrojok.hour6app", "com.example.lukasztrojok.hour6app.CoolMenu");
            startActivity.setFlags(startActivity.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startActivity);


        }

        // throw new UnsupportedOperationException("Not yet implemented");
    }


}

