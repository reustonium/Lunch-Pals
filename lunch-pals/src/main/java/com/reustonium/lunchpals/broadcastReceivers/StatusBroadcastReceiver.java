package com.reustonium.lunchpals.broadcastReceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.reustonium.lunchpals.models.Status;

import java.util.Date;

/**
 * Created by Andrew on 2/18/14.
 */
public class StatusBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        String action = intent.getAction();

        ParseUser user = ParseUser.getCurrentUser();
        Status status = new Status();
        status.setUser(user);
        user.put("pangsUpdatedAt", new Date());

        if(action.equals("com.reustonium.lunchpals.HAZPANGS")){
            user.put("status", 2);
            status.setHaz(2);
        }
        else if (action.equals("com.reustonium.lunchpals.NOHAZPANGS")){
            user.put("status", 0);
            status.setHaz(0);
        }
        else if (action.equals("com.reustonium.lunchpals.MAYHAZPANGS")){
            user.put("status", 1);
            status.setHaz(1);
        }

        status.saveInBackground();
        user.saveInBackground();

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.cancel(1);
    }
}
