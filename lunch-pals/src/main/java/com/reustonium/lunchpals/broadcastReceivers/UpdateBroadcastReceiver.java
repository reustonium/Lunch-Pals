package com.reustonium.lunchpals.broadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.reustonium.lunchpals.R;


/**
 * Created by Andrew on 3/3/14.
 */
public class UpdateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent updateIntent;

        if(action.equals("com.reustonium.lunchpals.UPDATE")){
            final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
            try {
                updateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            } catch (android.content.ActivityNotFoundException anfe) {
                updateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            }

            PendingIntent pIntent = PendingIntent.getBroadcast(context, 9, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Haz Pangs?")
                            .setContentText(String.format("Nudged by %s", "andy").toString())
                            .addAction(android.R.drawable.btn_star_big_off, "no-haz", pIntent)
                            .addAction(android.R.drawable.btn_star_big_on, "haz", pIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());

        }
    }
}
