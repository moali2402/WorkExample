package dev.vision.engizny;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

import dev.vision.engizny.parse.ParseAds;
import dev.vision.engizny.parse.ParseApp;
import dev.vision.engizny.parse.ParseCategory;
import dev.vision.engizny.parse.ParseDistrict;
import dev.vision.engizny.parse.ParseItem;
import dev.vision.engizny.parse.ParseStore;
import dev.vision.engizny.parse.ParseSub;
public class MyApplication extends Application {
    private static final boolean DEVELOPER_MODE = false;

	@SuppressLint("NewApi") @Override
    public void onCreate() {
        super.onCreate();
        if (DEVELOPER_MODE
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyDeath().build());
        }
        
        ParseObject.registerSubclass(ParseApp.class);

        ParseObject.registerSubclass(ParseAds.class);
        ParseObject.registerSubclass(ParseCategory.class);
        ParseObject.registerSubclass(ParseStore.class);
        ParseObject.registerSubclass(ParseSub.class);
        ParseUser.registerSubclass(ParseItem.class);
        ParseUser.registerSubclass(ParseDistrict.class);

        Parse.initialize(this, CREDENTIALS.PARSE_APP_ID, CREDENTIALS.PARSE_CLIENT_KEY);
        String unique = Build.SERIAL;
        
        if(unique == null || unique.isEmpty()){
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	        if(telephonyManager == null || telephonyManager.getDeviceId() == null )
	        	unique = Settings.Secure.ANDROID_ID;
	        else unique = telephonyManager.getDeviceId();
        }
        ParsePush.subscribeInBackground("");
        ParseInstallation.getCurrentInstallation().put("uniqueID", unique);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        

	}
}
