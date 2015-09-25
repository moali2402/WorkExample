package dev.vision.engizny.fragments;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import dev.vision.engizny.R;
import dev.vision.engizny.Static;
import dev.vision.engizny.Static.LANGUAGE;
import dev.vision.engizny.adapters.Adapter_Ads;
import dev.vision.engizny.adapters.Adapter_Subs;
import dev.vision.engizny.classes.SubCategory;
import dev.vision.engizny.interfaces.Ads;
import dev.vision.engizny.parse.ParseAds;
import dev.vision.engizny.parse.ParseCategory;
import dev.vision.engizny.parse.ParseSub;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;


public class Fragment_Category extends BaseFragment_SubCat {
	
	ArrayList<SubCategory> subs = new ArrayList<SubCategory>();
	private Adapter_Subs subsAdapter;
	ParseCategory cat;


	public Fragment_Category() {}
	
	public static Fragment_Category newInstance(ParseCategory c) {
		Fragment_Category smf = new Fragment_Category();
		smf.cat = c;

		smf.TITLE = "Education";
		smf.TITLE_ARABIC = "تـعلـيم";
		smf.ICON = R.drawable.graduation_cap;
		return smf; 
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = super.onCreateView(inflater, container, savedInstanceState);
		return v;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		

		subsAdapter = new Adapter_Subs(getActivity(), R.layout.sub_item, subs);
		list.setAdapter(subsAdapter);

		try {
			ParseQuery<ParseAds> pads = ParseAds.getQuery();
			pads.findInBackground(new FindCallback<ParseAds>() {
				
				@Override
				public void done(List<ParseAds> adlist, ParseException e) {
					if(e==null){
						ads.clear();
						for(ParseAds ad : adlist){
							AdFragment adf = AdFragment.newInstance(ad.getAd().getUrl());
							ads.add(adf);
						}
						pager.setAdapter(new Adapter_Ads(getChildFragmentManager(), getAds()));
						if (ads != null && ads.size()>0) {
							runnable(ads.size());
							//Re-run callback
							handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
						}
					}
				}
			});
			
			ParseCategory category =  new ParseCategory();
			category.setObjectId(cat.getObjectId());
			category.fetchIfNeeded();
			
			
			category.getSub(new FindCallback<ParseSub>() {
				
				@Override
				public void done(List<ParseSub> ol, ParseException e) {
					if(e == null){
						ArrayList<SubCategory> temp = new ArrayList<SubCategory>();
		
						for(ParseSub sub : ol){
							SubCategory s = new SubCategory(sub);
							temp.add(s);
						}
						subs.clear();
						subs.addAll(temp);
						subsAdapter.notifyDataSetChanged();
					}else{
						e.printStackTrace();
					}
				}
			});
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public ArrayList<Fragment> getAds() {
		return ads;
	}	
	
}
