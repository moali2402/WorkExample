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


public class Fragment_Ramadan extends BaseFragment_SubCat {
	
	ArrayList<SubCategory> subs = new ArrayList<SubCategory>();
	private Adapter_Subs subsAdapter;


	public Fragment_Ramadan() {}
	
	public static Fragment_Ramadan newInstance() {
		Fragment_Ramadan smf = new Fragment_Ramadan();
		smf.TITLE = "RAMADANIAT";
		smf.TITLE_ARABIC = "رمـضانـيات";
		smf.ICON = R.drawable.canon;
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
