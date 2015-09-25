package dev.vision.engizny.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.viewpagerindicator.CirclePageIndicator;

import dev.vision.engizny.CustomAnimation;
import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.adapters.Adapter_Ads;
import dev.vision.engizny.parse.ParseAds;
import dev.vision.engizny.parse.ParseAds.TYPES;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;


public class MainAdFragment extends BaseFragment {

	private ImageView show_hide;
	private View v;


	

	public ArrayList<Fragment> ads = new ArrayList<Fragment>();

	ViewPager pager;

	Handler handler;

	Runnable animateViewPager;

	protected boolean stopSliding;
	private CirclePageIndicator circleIndicator;


	static final long ANIM_VIEWPAGER_DELAY = 5000;
	static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
	
	
	public MainAdFragment() { 
		TITLE = "ENGIZNY";
		TITLE_ARABIC = "انجـزنى";
		ICON = R.drawable.e;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.activity_main, null);
		show_hide = (ImageView) v.findViewById(R.id.show_hide_menu);
		pager = (ViewPager) v.findViewById(R.id.pager);

		circleIndicator = (CirclePageIndicator) v.findViewById(R.id.titles);

		return v;
	}
	
	
	
	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		ObjectAnimator anim_out = ObjectAnimator.ofFloat(show_hide, "alpha", 1f, 0.2f);
		anim_out.setDuration(1500);
		anim_out.setRepeatCount(ObjectAnimator.INFINITE);
		anim_out.setRepeatMode(ObjectAnimator.REVERSE);
		anim_out.start();
		show_hide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((Engizny)getActivity()).toggle();
			}
		});
		ParseQuery<ParseAds> pads = ParseAds.getQuery();
		pads.whereEqualTo(ParseAds.TYPE, TYPES.MAIN.name())
		.findInBackground(new FindCallback<ParseAds>() {
			
			@Override
			public void done(List<ParseAds> adlist, ParseException e) {
				if(e==null){
					if(adlist != null){
						ads.clear();
						Collections.shuffle(adlist);	
						for(ParseAds ad : adlist.subList(0,  adlist.size()> 4 ? 4 : adlist.size())){
							MainAdsFragment adf = MainAdsFragment.newInstance(ad.getAd().getUrl());
							ads.add(adf);
						}
						pager.setOffscreenPageLimit(ads.size());
						pager.setAdapter(new Adapter_Ads(getChildFragmentManager(), getAds()));
						circleIndicator.setViewPager(pager);
						if (ads != null && ads.size()>0) {
							runnable(ads.size());
							//Re-run callback
							handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
						}
					}
				}
			}
		});
		

		pager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction()) {

				case MotionEvent.ACTION_CANCEL:
					break;

				case MotionEvent.ACTION_UP:
					// calls when touch release on ViewPager
					if (ads != null && ads.size() != 0) {
						stopSliding = false;
						runnable(ads.size());
						handler.postDelayed(animateViewPager,
								ANIM_VIEWPAGER_DELAY_USER_VIEW);
					}
					break;

				case MotionEvent.ACTION_MOVE:
					// calls when ViewPager touch
					if (handler != null && stopSliding == false) {
						stopSliding = true;
						handler.removeCallbacks(animateViewPager);
					}
					break;
				}
				return false;
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
		

	public void runnable(final int size) {
		if(handler != null && animateViewPager !=null)
			handler.removeCallbacks(animateViewPager);

		handler = new Handler();
		animateViewPager = new Runnable() {

			public void run() {
				if (!stopSliding) {
					if (pager.getCurrentItem() == size - 1) {
						pager.setCurrentItem(0);
					} else {
						pager.setCurrentItem(
								pager.getCurrentItem() + 1, true);
					}
					handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
				}
			}
		};
	}
	
	@Override
	public void onResume() {
		if (ads != null && ads.size()>0) {
			runnable(ads.size());
			//Re-run callback
			handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		}
		super.onResume();
	}

	
	@Override
	public void onPause() {
		if (handler != null) {
			//Remove callback
			handler.removeCallbacks(animateViewPager);
		}
		super.onPause();
	}
	
	public ArrayList<Fragment> getAds() {
		return ads;
	}	

}
