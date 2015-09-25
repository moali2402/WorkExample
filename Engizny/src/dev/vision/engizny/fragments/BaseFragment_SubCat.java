package dev.vision.engizny.fragments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import dev.vision.engizny.CustomAnimation;
import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.Static;
import dev.vision.engizny.Static.LANGUAGE;
import dev.vision.engizny.adapters.Adapter_Ads;
import dev.vision.engizny.adapters.Adapter_Subs;
import dev.vision.engizny.classes.SubCategory;
import dev.vision.engizny.interfaces.Ads;
import dev.vision.engizny.interfaces.TitledIconed;
import dev.vision.engizny.parse.ParseAds;
import dev.vision.engizny.parse.ParseAds.TYPES;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

public abstract class BaseFragment_SubCat extends Fragment implements TitledIconed, Ads{
	
	private ImageView icon;
	

	String TITLE;
	String TITLE_ARABIC;

	int ICON;
	public View v;
	public GridView list;
	public ArrayList<Fragment> ads = new ArrayList<Fragment>();
	ViewPager pager;
	Handler handler;
	Runnable animateViewPager;
	protected boolean stopSliding;

	static final long ANIM_VIEWPAGER_DELAY = 5000;
	static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

	private CirclePageIndicator circleIndicator;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.fragment_subbase, null);
		icon = (ImageView) v.findViewById(R.id.menu);
		list = (GridView) v.findViewById(R.id.listView1);
		pager = (ViewPager) v.findViewById(R.id.pager);
		circleIndicator = (CirclePageIndicator) v.findViewById(R.id.titles);

		setIcon();

		icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Engizny) getActivity()).toggle();				
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
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SubCategory sub=((SubCategory)list.getAdapter().getItem(position));
				String oid = sub.getObjectId();
				Fragment_Stores s = Fragment_Stores.newInstance(oid, sub.getName());
				((Engizny)getActivity()).switchContent(s);
			}
		});
		return v;
	}
	
	public void setIcon(){
		Picasso.with(getActivity()).load(getIcon()).fit().into(icon);
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
	
	@Override
	public int getIcon() {
		return ICON;
	}

	@Override
	public String getTitle() {
		return Static.LANG == LANGUAGE.ENGLISH? TITLE : TITLE_ARABIC;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
			
		ParseQuery<ParseAds> pads = ParseAds.getQuery();
		pads.whereEqualTo(ParseAds.TYPE, TYPES.SUB.name())
		.findInBackground(new FindCallback<ParseAds>() {
			
			@Override
			public void done(List<ParseAds> adlist, ParseException e) {
				if(e==null){
					
					if(adlist != null && adlist.size()>0){
						ads.clear();
						Collections.shuffle(adlist);	

						for(ParseAds ad : adlist.subList(0, adlist.size()> 3 ? 3 : adlist.size())){
							AdFragment adf = AdFragment.newInstance(ad.getAd().getUrl());
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
	}
	
	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}

}
