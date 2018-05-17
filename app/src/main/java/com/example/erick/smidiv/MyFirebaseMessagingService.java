package com.example.erick.smidiv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        /*Log.d("Firebase", "recibi un mensaje prro");
        Log.d("tipo", "onMessageReceived: "+remoteMessage.getMessageType().toString());
        Log.d("", "onMessageReceived: "+remoteMessage.getCollapseKey());*/
        sendNotification(remoteMessage.getNotification().getBody());
    }
    public void sendNotification(String messageBody){
        Log.d("Token", "sendNotification: "+ FirebaseInstanceId.getInstance().getToken().toString());
        Intent intent=  new Intent(this,Selector.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSountUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        NotificationCompat.Builder notificationbuilder=  new NotificationCompat.Builder(this);
        notificationbuilder.setSmallIcon(R.drawable.alarma);
        notificationbuilder.setContentTitle("ALARMA SMIDIV");
        notificationbuilder.setContentText("Hemos detectado que tu vehiculo salio de la zona segura");
        notificationbuilder.setAutoCancel(true);
        notificationbuilder.setSound(defaultSountUri);
        notificationbuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationbuilder.build());
    }
}
