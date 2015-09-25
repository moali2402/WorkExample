package dev.vision.shopping.center;


import java.util.Random;

import dev.vision.license.LICENSE;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Splash extends FragmentActivity {
	private TransitionDrawable td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	ImageView iv = new ImageView(this);
    	Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
    	mLoadAnimation.setDuration(2500);
    	iv.setImageResource(R.drawable.myapp_loading);

    	mLoadAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				new Handler().postDelayed(new Runnable() {
					
		        	
					@SuppressLint("NewApi") @Override
					public void run() {
						Intent i = new Intent(Splash.this, CustomZoomAnimation.class);
						if(LICENSE.isValid("10/07/2015", Splash.this)){
							startActivity(i);
							finish();
						}
					}
				}, 2000);				
			}
		});
        
       
    	iv.setAnimation(mLoadAnimation);

        setContentView(iv);
        
    }
    
    void Init(){
	    overrideFonts(this, findViewById(android.R.id.content));
		
		
	    final ImageView im = null;//(ImageView) findViewById(R.id.ImageView01);
		
		td = (TransitionDrawable) im.getDrawable();
		td.setCrossFadeEnabled(true);
		td.startTransition(3000);
		final Random rnd = new Random(); 
	
		new Handler().postDelayed(new Runnable() {
			
	
			@SuppressLint("NewApi") @Override
			public void run() {
				int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
				td = new TransitionDrawable(new Drawable[]{td.getDrawable(1), new ColorDrawable(color)});
				im.setImageDrawable(td);
				td.setCrossFadeEnabled(true);
				td.startTransition(3000);
	
				new Handler().postDelayed(this, 3000);
			}
		}, 3000);
    }

	private void overrideFonts(final Context context, final View v) {
	      try {
	        if (v instanceof ViewGroup) {
	        	ViewGroup vg = (ViewGroup) v;
	          
	             for (int i = 0; i < vg.getChildCount(); i++) {
		             View child = vg.getChildAt(i);
		             overrideFonts(context, child);
	             }
	 
	        } else if (v instanceof TextView ) {
	          ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "AvantGardeITCbyBTBook.otf"));
	        }
	 
	      } catch (Exception e) {
	      }
	}
}
