package dev.vision.shopping.center.views;

import de.vogella.algorithms.dijkstra.model.Point;
import dev.vision.shopping.center.Fragments.Mapper_Fragment;
import dev.vision.shopping.center.adapter.FragmentAdapter;
import dev.vision.shopping.center.api.BaseLocation;
import dev.vision.shopping.center.api.Store;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FacilitiesViewPager extends ViewPager{


	public static final String CUSTOMER_SERVICE = "city.stars.customer_service";
	public static final String ATM = "city.stars.atm";
	public static final String WC = "city.stars.wc";
	public static final String ELEVATORS = "city.stars.lifts";
	public static final String ESCALATORS = "city.stars.escalators";

	private boolean isPagingEnabled = true;

	public FacilitiesViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	

	public FacilitiesViewPager(Context context) {
		super(context);
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }



	public void show(boolean customer, boolean esc, boolean elev, boolean wc, boolean atm) {
		for(int i = 0; i < getAdapter().getCount(); i++){
			Mapper_Fragment fm = ((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(i));
			if(customer){
				fm.CustomerService();
			}else if(esc){
				fm.Escalators();
			}else if(elev){
				fm.Elevators();
			}else if(wc){
				fm.WC();
			}else if(atm){
				fm.ATM();
			}else{
				fm.RESET();
			}
			
			//fm.refresh();
		}
	}
	
	
	
}
