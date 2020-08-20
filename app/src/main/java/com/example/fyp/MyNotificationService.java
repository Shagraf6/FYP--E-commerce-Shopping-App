package com.example.fyp;

import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void showNotification(String title, String message)
{
    NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"Notification")
            .setAutoCancel(true)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.logo)
            .setContentText(message);
   NotificationManagerCompat manager =NotificationManagerCompat.from(this);
    manager.notify(999,builder.build());
}
}
