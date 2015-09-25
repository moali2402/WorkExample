package dev.vision.engizny;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.ParseException;
import com.parse.ParsePush;

import dev.vision.engizny.parse.ParseApp;
import dev.vision.license.LICENSE;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.widget.Toast;


public class Splash extends ActionBarActivity {

    @SuppressWarnings("deprecation")
	@SuppressLint({ "InlinedApi", "NewApi" }) 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       // LICENSE.isValid("31/05/2015", this);
        
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
	        Window w = getWindow();
	        w.setFlags(
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	        w.setFlags(
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   
        }
        setContentView(R.layout.splash);

        if(Build.VERSION.SDK_INT > 12){
    		Point po = new Point();
        	getWindowManager().getDefaultDisplay().getSize(po);
        	Static.SCREEN_WIDTH = po.x;
        }
        else Static.SCREEN_WIDTH = getWindowManager().getDefaultDisplay().getWidth();
		
        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());

        
		
        WebView wv;  
        wv = (WebView) findViewById(R.id.webView1);  
        wv.loadUrl("file:///android_asset/loading.html"); 
        if (Build.VERSION.SDK_INT >= 11) {
        	wv.setBackgroundColor(0x01000000);

		} else {
			wv.setBackgroundColor(0x00000000);
		}
        View view = findViewById(android.R.id.content);
    	Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
    	mLoadAnimation.setDuration(2500);
             
    	view.setAnimation(mLoadAnimation);
		new RegisterGcmTask().execute();				

        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    class RegisterGcmTask extends AsyncTask<Void, Void, ParseApp> {

        String msg = "";

        @Override
        protected ParseApp doInBackground(Void... voids) {
        	try {
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        	if(CheckNetworkConnection.isConnectionAvailable(getApplicationContext())){

                try {
                    //msg = gcm.register(CREDENTIALS.GDC_PROJECT_NUBMER);
					ParseApp app = new ParseApp();
					app.setObjectId("uPAokY6Gh8");
					
					app.fetch();
					return app;
					
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				
        	}
            return null;
        }

        @Override
        protected void onPostExecute(ParseApp app) {
        	if(app != null){
            	if(!app.isLocked()){
					Intent i = new Intent(Splash.this, Activity_Engizny.class);
					startActivity(i);
				}else{
					Toast.makeText(getApplicationContext(), app.getMessage(), Toast.LENGTH_LONG).show();
				}
        	}else
			Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_LONG).show();
			finish();
			

        }
    }
	
}
