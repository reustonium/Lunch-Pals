package com.reustonium.lunchpals.broadcastReceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;

/**
 * Created by Andrew on 2/18/14.
 */
public class StatusBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        ParseUser user = ParseUser.getCurrentUser();
        user.put("pangsUpdatedAt", new Date());

        if(action.equals("com.reustonium.lunchpals.HAZPANGS")){
            user.put("status", 2);
            Toast toast = Toast.makeText(context, "HAZ Pangs!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (action.equals("com.reustonium.lunchpals.NOHAZPANGS")){
            user.put("status", 0);
            Toast toast = Toast.makeText(context, "NO Pangs!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (action.equals("com.reustonium.lunchpals.MAYHAZPANGS")){
            user.put("status", 1);
            Toast toast = Toast.makeText(context, "MAY HAZ Pangs!!", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(context, "You Borked it!", Toast.LENGTH_LONG);
            toast.show();
        }

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                }
            }
        });

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.cancel(1);
    }
}
