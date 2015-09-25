package dev.vision.shopping.center.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {   

    @Override
    public void onReceive(Context context, Intent intent) {

     Intent myIntent = new Intent(context, ProxAlertActivity.class);
     context.startService(myIntent);
    }
}