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


public class Fragment_Restaurants extends BaseFragment_SubCat {
	
	ArrayList<SubCategory> subs = new ArrayList<SubCategory>();
	private Adapter_Subs subsAdapter;


	public Fragment_Restaurants() {}
	
	public static Fragment_Restaurants newInstance() {
		Fragment_Restaurants smf = new Fragment_Restaurants();
		smf.TITLE = "RESTAURANTS";
		smf.TITLE_ARABIC = "مطــاعم";
		smf.ICON = R.drawable.restaurant;
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
		
		try{

			ParseCategory category =  new ParseCategory();
			category.setObjectId("srixejtws3");
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
