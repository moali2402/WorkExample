package dev.vision.shopping.center;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.actionbar.ActionBarSlideIcon;




import dev.vision.shopping.center.Fragments.CarLocationFragment;
import dev.vision.shopping.center.Fragments.FacilitiesMenuFragment;
import dev.vision.shopping.center.Fragments.HomeFragment;
import dev.vision.shopping.center.Fragments.MenuFragment;
import dev.vision.shopping.center.map.ProxAlertActivity;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.CityStarsNFC;

public abstract class CustomAnimation extends ActionBarActivity {
	
	private CanvasTransformer mTransformer;
	String title;
    ImageView wel ;
	public SlidingMenu sm;
	private Fragment mContent;
	ActionBar b;
	SlideDrawable sd ;
	SlideDrawable ssd;
	Drawable d;
	private NfcAdapter mAdapter;
	private boolean newIntent;
	
    public CustomAnimation(int titleRes, CanvasTransformer transformer) {
		setTitle(titleRes);
		mTransformer = transformer;
	}

	public CustomAnimation(String string, CanvasTransformer transformer) {
		title=string;
		mTransformer = transformer;
		
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);


		final Window window = getWindow();
	    window.requestFeature(Window.FEATURE_NO_TITLE);
       	super.onCreate(savedInstanceState);


		setTitle(title);
		
		Static.setContext(this);
		

		setContentView(R.layout.content_frame);

		//setSlidingActionBarEnabled(true);
		// set the Above View
		mAdapter = NFC.getInstance(this);

		Intent myIntent = new Intent(this, ProxAlertActivity.class);
	    startService(myIntent);
		final Resources res = getResources();
		final boolean isKitkat = Build.VERSION.SDK_INT >= 19;
		    
		//replace window background to reduce overdraw
		final ViewGroup contentView = (ViewGroup)findViewById(android.R.id.content);
		final View content = contentView.getChildAt(0);
		final Drawable extendedWindowBackground = window.getDecorView().getBackground();
		final Drawable windowBackground = !isKitkat ? extendedWindowBackground
		                                            : getWindowBackgroundLayer(extendedWindowBackground,
		                                                                       R.id.window_background,
		                                                                       "window_background");
		window.setBackgroundDrawable(null);
		setBackground(content, windowBackground);
		/*
		
		// add statusbar background
		if (isKitkat)
		{
		    // check if translucent bars are enabled
		    final int config_enableTranslucentDecor_id =
		            res.getIdentifier("config_enableTranslucentDecor", "bool", "android");
		    if (config_enableTranslucentDecor_id > 0 && res.getBoolean(config_enableTranslucentDecor_id))
		    {
		        // get ActionBar container
		        final View actionBarContainer = findViewById("action_bar_container", "android");
		        if (actionBarContainer != null)
		        {
		            // add layout listener (can't get margin before layout)
		            //noinspection ConstantConditions
		            actionBarContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
		                    .OnGlobalLayoutListener()
		            {
		                @SuppressWarnings("ConstantConditions")
		                @Override
		                public void onGlobalLayout()
		                {
		                    // remove layout listener
		                    final ViewTreeObserver vto = actionBarContainer.getViewTreeObserver();
		                    if (Build.VERSION.SDK_INT < 16)
		                        vto.removeGlobalOnLayoutListener(this);
		                    else vto.removeOnGlobalLayoutListener(this);
		
		                    // create and add statusbar background view
		                    final Drawable statusBarBackground = getWindowBackgroundLayer(extendedWindowBackground,
		                                                                                  R.id.statusbar_background,
		                                                                                  "statusbar_background");
		                    final int statusBarHeight =
		                            ((ViewGroup.MarginLayoutParams)actionBarContainer.getLayoutParams()).topMargin;
		                    final View statusBarView = new View(CustomAnimation.this);
		                    setBackground(statusBarView, statusBarBackground);
		                    final FrameLayout.LayoutParams statusBarBackground_lp = new FrameLayout.LayoutParams(
		                            ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight,
		                            Gravity.TOP | Gravity.FILL_HORIZONTAL);
		                    contentView.addView(statusBarView, 0, statusBarBackground_lp);
		
		                    // shift content under actionbar
		                    final ViewGroup.MarginLayoutParams content_lp =
		                            (ViewGroup.MarginLayoutParams)content.getLayoutParams();
		                    content_lp.topMargin = getActionBar().getHeight() + statusBarHeight;
		                    content.setLayoutParams(content_lp);
		                }
		            });
		        }
		    }
		}
	    */
		Point po = new Point();
		getWindowManager().getDefaultDisplay().getSize(po);
		Static.SCREEN_WIDTH = po.x;
		Static.SCREEN_HEIGHT = po.y;
		
	    sm = new SlidingMenu(this);
	    sm.setBackgroundResource(R.drawable.abs__list_pressed_holo_dark);
	    
		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			sm.setMenu(R.layout.menu_frame);
			sm.setSecondaryMenu(R.layout.r_menu_frame);

			sm.setSlidingEnabled(true);
		}
		b = getSupportActionBar();

		b.setDisplayShowCustomEnabled(true);
		b.hide();
		
		
		// set the Above View Fragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new HomeFragment();	
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();

		// set the Behind View Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.r_menu_frame, new FacilitiesMenuFragment())
		.commit();
		
		
		
		sm.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMode(SlidingMenu.LEFT);
		sm.setBehindScrollScale(0.0f);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setShadowWidth(50);
		sm.setFadeEnabled(true);
		//sm.setMenu(R.layout.back_menu);
		sm.setFadeDegree((float) 0.5);
		sm.setBehindCanvasTransformer(mTransformer);
		sm.setBackgroundResource(R.drawable.statusbar_background);;
		sm.setBehindWidth((int) (Static.SCREEN_WIDTH/3.5));
		sm.setFitsSystemWindows(true);
		
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
			d = res.getDrawable(R.drawable.icon_nav_menu_selected);
			sd = new SlideDrawable(d);
			b.setHomeAsUpIndicator(sd);
			b.setDisplayHomeAsUpEnabled(true);
		
			ssd = new SlideDrawable(res.getDrawable(R.drawable.nav_arrow_back));
			//ssd.setOffsetBy(.3f);

	        //ssd.setOffset(.8f);
	        sm.setOnOpenListener(new OnOpenListener() {
    			
    			@Override
    			public void onOpen() {
    		         b.setHomeAsUpIndicator(res.getDrawable(R.drawable.nav_arrow_back_n));
    				
    			}
    		});
    		sm.setOnCloseListener(new OnCloseListener() {
    			
    			@Override
    			public void onClose() {
    		         b.setHomeAsUpIndicator(res.getDrawable(R.drawable.ic_drawer_n));

    			}
    		});

		} 
	    b.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
	}
	


    @Override
    public void onNewIntent(Intent intent) {
        newIntent = true;
        setIntent(intent);
        //NFC.resolve(intent);
    }
    

    @Override
    protected void onResume() {
        super.onResume();
        if(newIntent)
            NFC.resolve(getIntent());
        NFC.onResume(this);
        newIntent = false;
    }

    @Override
    protected void onPause() {
        NFC.onPause(this);
        super.onPause();
    }
    
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.addToBackStack(null)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				sm.showContent();
			}
		}, 50);
	}

	private static class SlideDrawable extends Drawable implements Drawable.Callback {
        private Drawable mWrapped;
        private float mOffset;
        private float mOffsetBy;
 
        private final Rect mTmpRect = new Rect();
 
        public SlideDrawable(Drawable wrapped) {
            mWrapped = wrapped;
            setCallback(this);
        }
 
        public SlideDrawable(Drawable sd, float f) {
        	mOffset = f;
        	 mWrapped = sd;
             setCallback(this);
         }

		public void setOffset(float offset) {
            mOffset = offset;
            invalidateSelf();
            VersionHelper.refreshActionBarMenu((ActionBarActivity) Static.ctx);
        }
 
        public float getOffset() {
            return mOffset;
        }
 
        public void setOffsetBy(float offsetBy) {
            mOffsetBy = offsetBy;
            invalidateSelf();
            VersionHelper.refreshActionBarMenu((ActionBarActivity) Static.ctx);

        }
 
        @Override
        public void draw(Canvas canvas) {
            mWrapped.copyBounds(mTmpRect);
            canvas.save();
            canvas.translate(mOffsetBy * mTmpRect.width() * -mOffset, 0);
            mWrapped.draw(canvas);
            canvas.restore();
        }
 
        @Override
        public void setChangingConfigurations(int configs) {
            mWrapped.setChangingConfigurations(configs);
        }
 
        @Override
        public int getChangingConfigurations() {
            return mWrapped.getChangingConfigurations();
        }
 
        @Override
        public void setDither(boolean dither) {
            mWrapped.setDither(dither);
        }
 
        @Override
        public void setFilterBitmap(boolean filter) {
            mWrapped.setFilterBitmap(filter);
        }
 
        @Override
        public void setAlpha(int alpha) {
            mWrapped.setAlpha(alpha);
        }
 
        @Override
        public void setColorFilter(ColorFilter cf) {
            mWrapped.setColorFilter(cf);
        }
 
        @Override
        public void setColorFilter(int color, PorterDuff.Mode mode) {
            mWrapped.setColorFilter(color, mode);
        }
 
        @Override
        public void clearColorFilter() {
            mWrapped.clearColorFilter();
        }
 
        @Override
        public boolean isStateful() {
            return mWrapped.isStateful();
        }
 
        @Override
        public boolean setState(int[] stateSet) {
            return mWrapped.setState(stateSet);
        }
 
        @Override
        public int[] getState() {
            return mWrapped.getState();
        }
 
        @Override
        public Drawable getCurrent() {
            return mWrapped.getCurrent();
        }
 
        @Override
        public boolean setVisible(boolean visible, boolean restart) {
            return super.setVisible(visible, restart);
        }
 
        @Override
        public int getOpacity() {
            return mWrapped.getOpacity();
        }
 
        @Override
        public Region getTransparentRegion() {
            return mWrapped.getTransparentRegion();
        }
 
        @Override
        protected boolean onStateChange(int[] state) {
            mWrapped.setState(state);
            return super.onStateChange(state);
        }
 
        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mWrapped.setBounds(bounds);
        }
 
        @Override
        public int getIntrinsicWidth() {
            return mWrapped.getIntrinsicWidth();
        }
 
        @Override
        public int getIntrinsicHeight() {
            return mWrapped.getIntrinsicHeight();
        }
 
        @Override
        public int getMinimumWidth() {
            return mWrapped.getMinimumWidth();
        }
 
        @Override
        public int getMinimumHeight() {
            return mWrapped.getMinimumHeight();
        }
 
        @Override
        public boolean getPadding(Rect padding) {
            return mWrapped.getPadding(padding);
        }
 
        @Override
        public ConstantState getConstantState() {
            return super.getConstantState();
        }
 
        @Override
        public void invalidateDrawable(Drawable who) {
            if (who == mWrapped) {
                invalidateSelf();
            }
        }
 
        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            if (who == mWrapped) {
                scheduleSelf(what, when);
            }
        }
 
        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            if (who == mWrapped) {
                unscheduleSelf(what);
            }
        }
    }

	public void toggle() {
		sm.toggle();
	}
	
	
	/**
	 * Find a view by providing the id resource name and package in the form {@code @package/id:name}.
	 * <p>
	 * Useful for getting "private" views like {@code @android:id/action_bar_container}.
	 * </p>
	 *
	 * @param name the resource id name (for example @android.id:resource would be "resource").
	 * @param pkg  the resource package (for example @android.id:resource would be "android").
	 *
	 * @return The view if found and data is valid, or {@code null}.
	 */
	public View findViewById(String name, String pkg)
	{
	    final int id = getResources().getIdentifier(name, "id", pkg);
	    return id > 0 ? findViewById(id) : null;
	}
	
	/**
	 * Uses {@link android.view.View#setBackground(android.graphics.drawable.Drawable)}
	 * if SDK >= 16, else falls back on
	 * {@link android.view.View#setBackgroundDrawable(android.graphics.drawable.Drawable)}.
	 *
	 * @param background the {@code Drawable} to use as the background, or null to remove the background.
	 */
	@SuppressLint("NewApi") private static void setBackground(View view, Drawable background)
	{
	    if (Build.VERSION.SDK_INT < 16) view.setBackgroundDrawable(background);
	    else view.setBackground(background);
	}
	
	/**
	 * Extracts the requested layer from the window drawable or throws an {@link java.lang.IllegalAccessException} if
	 * the window drawable is not a {@link android.graphics.drawable.LayerDrawable} or the requested layer is missing.
	 *
	 * @param windowBackground the window background.
	 * @param layerId          the layer id like {@code R.id.statusbar_background}.
	 * @param layerIdName      the layer id name like "statusbar_background".
	 *
	 * @return The requested layer.
	 */
	private static Drawable getWindowBackgroundLayer(Drawable windowBackground, int layerId, String layerIdName)
	{
	    if (!(windowBackground instanceof LayerDrawable))
	        throw new IllegalStateException("Window background must be a LayerDrawable.");
	final Drawable layer = ((LayerDrawable)windowBackground).findDrawableByLayerId(layerId);
	if (layer == null) throw new IllegalStateException(
	        String.format("Window background must have layer with android:id=\"@+id/%s\"", layerIdName));
	    return layer;
	}
	
	@SuppressLint("NewApi") @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	         sm.showMenu();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	public void startScan(){
		//(new IntentIntegrator(this)).initiateScan();
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		//intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == IntentIntegrator.REQUEST_CODE && intent != null) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT"); //Gets the data of the scanned QR_Code
				//String format = intent.getStringExtra("SCAN_RESULT_FORMAT");//Gets the format and data type of QR_Code
				//System.out.println(intent.toString());

				if(CityStarsNFC.isStarsNFC(contents)){
					CityStarsNFC cf = new CityStarsNFC(contents);
					Static.saveLocation(cf);
					Fragment car_lo = CarLocationFragment.NewInstance(cf);
					switchContent(car_lo);						
				}
							
			}
		}
	}
	
	@Override
	public void startActivityForResult(Intent intent, int code) {
      	super.startActivityForResult(intent, code);
    }
	
}
