package dev.vision.shopping.center.views;

import de.vogella.algorithms.dijkstra.model.Point;
import dev.vision.shopping.center.Fragments.Mapper_Fragment;
import dev.vision.shopping.center.adapter.FragmentAdapter;
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

public class ViewPagerWrap extends ViewPager{

	private Store to;
	private Store from;
	private boolean isPagingEnabled;

	public ViewPagerWrap(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewPagerWrap(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
	    int height = 0;
	    for(int i = 0; i < getChildCount(); i++) {
	        View child = getChildAt(i);
	        child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	        int h = child.getMeasuredHeight();
	        if(h > height) height = h;
	    }

	    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		 */
	    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
	
		
	public void setToStore(Store s){
		
		if(s!=null){
			to = s;
			int lev = to.getLevel();
			setCurrentItem(lev);
			((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(lev)).setTo(to);
		}
		
	}
	
	public void setFromStore(Store s){
		//resetAll();
		if(s!=null){
			from = s;
			int lev = from.getLevel();
			
			((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(lev)).setFrom(from);

			if(to.getLevel() == from.getLevel()){
				//start navigation
			}else{
				int id = ((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(lev)).toNearest();
				((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(to.getLevel())).setFrom(id);
			}
			for(int i = 0; i < getAdapter().getCount(); i++){
				((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(i)).refresh();
			}
    		setCurrentItem(from.getLevel(),false);			
		}
	}


	private void resetAll() {
		for(int i = 0; i < getAdapter().getCount(); i++){
			Mapper_Fragment fm = ((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(i));
			fm.setFrom(null);
			if(i != to.getLevel())
				fm.setTo(null);
			else fm.setTo(to);	
		}
	}
	
	public void reset( int i ) {
		Mapper_Fragment fm = ((Mapper_Fragment)((FragmentAdapter)getAdapter()).getItem(i));
		fm.setFrom(null);
		if(i != to.getLevel())
			fm.setTo(null);
		else fm.setTo(to);	
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
	
}
