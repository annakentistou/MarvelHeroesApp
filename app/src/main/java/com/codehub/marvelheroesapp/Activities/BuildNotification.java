package com.codehub.marvelheroesapp.Activities;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.codehub.marvelheroesapp.R;

import static com.codehub.marvelheroesapp.CreateNotificationChannel.CHANNEL_ID;

public class BuildNotification extends AppCompatActivity {

    NotificationManager notificationManager;

    public void activityContext(Context activityName) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notif_intent = new Intent(activityName, NotificationsActivity.class);
        notif_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(activityName, 0, notif_intent, 0);

        String title = "Marvel App";
        String message = "There is no Notifications";
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }
}
