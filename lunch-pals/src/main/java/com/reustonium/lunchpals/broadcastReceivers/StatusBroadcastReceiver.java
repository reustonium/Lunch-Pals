package com.reustonium.lunchpals.broadcastReceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Andrew on 2/18/14.
 */
public class StatusBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals("com.reustonium.lunchpals.HAZPANGS")){
            Toast toast = Toast.makeText(context, "HAZ!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (action.equals("com.reustonium.lunchpals.NOHAZPANGS")){
            Toast toast = Toast.makeText(context, "YOU SUCK!!", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "I dont know what to hink about you!!!", Toast.LENGTH_LONG);
            toast.show();
        }

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.cancel(1);
    }
}
