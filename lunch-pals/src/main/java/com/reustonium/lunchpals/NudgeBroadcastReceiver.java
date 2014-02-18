package com.reustonium.lunchpals;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andrew on 2/17/14.
 */
public class NudgeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("!!!", "notification started");

        //TODO update pending intent to set status
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String from = json.getString("from");

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Haz Pangs?")
                            .setContentText(String.format("Nudged by %s",from).toString())
                            .addAction(R.drawable.ic_action_haz, "haz", pIntent)
                            .addAction(R.drawable.ic_action_no_haz, "no-haz", pIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
