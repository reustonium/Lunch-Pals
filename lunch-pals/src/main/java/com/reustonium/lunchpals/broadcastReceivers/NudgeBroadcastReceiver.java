package com.reustonium.lunchpals.broadcastReceivers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.activities.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andrew on 2/17/14.
 */
public class NudgeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //ContentIntent
        Intent lpIntent = new Intent(context, SplashActivity.class);
        PendingIntent lpPendingIntent = PendingIntent.getBroadcast(context, 005, lpIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Haz Pangs Intent
        Intent hazIntent = new Intent();
        hazIntent.setAction("com.reustonium.lunchpals.HAZPANGS");
        PendingIntent hazPendingIntent = PendingIntent.getBroadcast(context, 007, hazIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //NoHaz Pangs Intent
        Intent noHazIntent = new Intent();
        noHazIntent.setAction("com.reustonium.lunchpals.NOHAZPANGS");
        PendingIntent noHazPendingIntent = PendingIntent.getBroadcast(context, 007, noHazIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //MayHaz Pangs Intent
        Intent mayHazIntent = new Intent();
        mayHazIntent.setAction("com.reustonium.lunchpals.MAYHAZPANGS");
        PendingIntent mayHazPendingIntent = PendingIntent.getBroadcast(context, 007, mayHazIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String from = json.getString("from");

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Haz Pangs?")
                            .setContentText(String.format("Nudged by %s", from).toString())
                            .setContentIntent(lpPendingIntent)
                            .addAction(android.R.drawable.btn_star_big_on, "haz", hazPendingIntent)
                            .addAction(android.R.drawable.ic_menu_help, "may-haz", mayHazPendingIntent)
                            .addAction(android.R.drawable.btn_star_big_off, "no-haz", noHazPendingIntent)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.lunchpal)));


            Notification notification = new WearableNotifications.Builder(mBuilder)
                    .setHintHideIcon(true)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, notification);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
