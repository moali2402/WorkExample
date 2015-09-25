package dev.vision.shopping.center.map;
import dev.vision.shopping.center.CustomZoomAnimation;
import dev.vision.shopping.center.NFC;
import dev.vision.shopping.center.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ProximityIntentReceiver extends BroadcastReceiver {

     

    private static final int NOTIFICATION_ID = 1000;
    PendingIntent pendingIntent;
    Notification n;
	private boolean entered;
	private boolean entering;
    

    @Override

    public void onReceive(Context context, Intent intent) {

         

        String key = LocationManager.KEY_PROXIMITY_ENTERING;

 

        entering = intent.getBooleanExtra(key, false);
        pendingIntent = PendingIntent.getActivity(context,0 , new Intent(context, CustomZoomAnimation.class), 0);       
        NotificationManager notificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        /*
        if(n==null)
        n = //createNotification();

        //n.when = System.currentTimeMillis();

       
            
        if (entering) {

            Log.d(getClass().getSimpleName(), "entering");
            n.setLatestEventInfo(context,
            		"Welcome to CityStars!", "Click here to open CItyStars app.", pendingIntent);
            entered=true;
        }
        else {
        	if(entered){
	            Log.d(getClass().getSimpleName(), "exiting");
	            
	            n.setLatestEventInfo(context,
	            		"GoodBye!", "CityStars wishes to see you again soon.", pendingIntent);
	            notificationManager.notify(NOTIFICATION_ID, n);
	            entered=false;
        	}
        } 
         */
        
        if (entering) {
        	createNotification_API(context, "Welcome to CityStars!", "Click here to open CityStars app.");
            notificationManager.notify(NOTIFICATION_ID, n);
        	entered=true;
        }
        else {
        	if(entered){
            	createNotification_API(context, "GoodBye!", "CityStars wishes to see you again soon.");
	            notificationManager.notify(NOTIFICATION_ID, n);
            	entered=false;
        	}
        } 

    }

    
    private Notification createNotification_API(Context ctx , String ticker, String msg) {
    	long[] pattern ={0, 200, 200, 0, 200, 100};
    	boolean nfc = NFC.isSupported(ctx);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo_s2)
                .setVibrate(pattern)
                .setOngoing(entering)
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(!entering && entered)
                .setContentTitle(ticker)
                .setContentText(msg);
        if(entering){
        	builder.setPriority(Notification.PRIORITY_MAX)
            	.addAction(nfc? R.drawable.nfc_i : R.drawable.barcode_i, "Scan Parking "+ (nfc? "NFC" : "QR_Code"), pendingIntent);
        }
        n = builder.build();
		

        return n;

    }
     

    private Notification createNotification() {

        n = new Notification();

         

        n.icon = R.drawable.logo_s2;
         
        if(entering)
        n.flags |= Notification.FLAG_NO_CLEAR;

        n.flags |= Notification.FLAG_SHOW_LIGHTS;

         

        n.defaults |= Notification.DEFAULT_VIBRATE;

        n.defaults |= Notification.DEFAULT_LIGHTS;

         

        n.ledARGB = Color.WHITE;

        n.ledOnMS =  1500;
        n.ledOffMS = 1500;


         

        return n;

    }

     

}
