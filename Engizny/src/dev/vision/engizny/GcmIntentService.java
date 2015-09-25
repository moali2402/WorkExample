package dev.vision.engizny;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.InboxStyle;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mNotificationManager = (NotificationManager)
            this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti;
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Splash.class), 0);
        noti = buildNotification("Engizny", "Engizny", null, contentIntent);      
        
        mNotificationManager.notify(NOTIFICATION_ID, noti);

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

	
    private Notification buildNotification(String title, String text, ArrayList<String> m, PendingIntent contentIntent){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);

        Notification n = new NotificationCompat.InboxStyle(mBuilder)
        		.setBigContentTitle("New Vooms!")
            	.addLine("New Message!")
            	.addLine("New Message!")
                .setSummaryText("More messages")
                .build();
        
        return n;
    }

}

