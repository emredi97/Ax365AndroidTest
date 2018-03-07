package emre.ax365test;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by emreh_000 on 27.12.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Map<String, String> data = remoteMessage.getData();
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        String url = data.get("URL");
        sendNotification(remoteMessage);

        //  startActivity(i);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
//        Toast.makeText(getApplicationContext(), messageBody, Toast.LENGTH_LONG).show();
        //Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pushnotify);
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (remoteMessage.getData().get("URL") != null) {
            i.setData(Uri.parse(remoteMessage.getData().get("URL")));
            PendingIntent intent = PendingIntent.getActivity(this, 0,
                    i, 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.roedlround)
                    .setContentTitle("RD 365 AX Test")
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentIntent(intent)
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.roedlround)
                    .setContentTitle("RD 365 AX Test")
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }


    }
}