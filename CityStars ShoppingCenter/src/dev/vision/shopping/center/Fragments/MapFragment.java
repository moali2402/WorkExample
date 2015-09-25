package dev.vision.shopping.center.Fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.adapter.FragmentAdapter;
import dev.vision.shopping.center.views.FacilitiesViewPager;


public class MapFragment extends Fragment {
	
	private View v;
	int posi;
	private FacilitiesViewPager vp;
	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if( s != null ){
				boolean customer = false;
				boolean esc = false;
				boolean elev = false;
				boolean wc = false;
				boolean atm = false;
				if(s.equals(FacilitiesViewPager.CUSTOMER_SERVICE)){
					customer=true;
				}else if(s.equals(FacilitiesViewPager.ESCALATORS)){
					esc=true;
				}else if(s.equals(FacilitiesViewPager.ELEVATORS)){
					elev=true;
				}else if(s.equals(FacilitiesViewPager.WC)){
					wc=true;
				}else if(s.equals(FacilitiesViewPager.ATM)){
					atm=true;
				}
					
				vp.show(customer, esc, elev, wc, atm);
			}
			
		}
	};
	
	public MapFragment() {
	}
	
	public static MapFragment NewInstance(int pos) {
		MapFragment d =new MapFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.mappps, container, false);
		vp =(FacilitiesViewPager) v.findViewById(R.id.viewp);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		posi = getArguments().getInt("pos");

		int icon = Static.icons[posi];
		String txt  = Static.text[posi];
		((ActionBarActivity)getActivity()).getSupportActionBar().show();
		((ActionBarActivity)getActivity()).getSupportActionBar().setIcon(icon);
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(txt);
		
		vp.setAdapter(new FragmentAdapter(getChildFragmentManager(), getMapsFragments()));
		vp.setOffscreenPageLimit(6);
		
		IntentFilter f = new IntentFilter();
		f.addAction(FacilitiesViewPager.CUSTOMER_SERVICE);
		f.addAction(FacilitiesViewPager.ELEVATORS);
		f.addAction(FacilitiesViewPager.ESCALATORS);
		f.addAction(FacilitiesViewPager.WC);
		f.addAction(FacilitiesViewPager.ATM);

		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(rec , f);
		
	}
	
	public ArrayList<Fragment> getMapsFragments(){
		ArrayList<Fragment> maps = new ArrayList<Fragment>();
		
		Mapper_Fragment level0 = Mapper_Fragment.newInstance(0, null);
		Mapper_Fragment level1 = Mapper_Fragment.newInstance(1, null);
		Mapper_Fragment level2 = Mapper_Fragment.newInstance(2, null);
		Mapper_Fragment level3 = Mapper_Fragment.newInstance(3, null);
		Mapper_Fragment level4 = Mapper_Fragment.newInstance(4, null);
		Mapper_Fragment level5 = Mapper_Fragment.newInstance(5, null);
		Mapper_Fragment level6 = Mapper_Fragment.newInstance(6, null);
		
		maps.addAll(Arrays.asList(level0, level1, level2, level3, level4, level5, level6));
		return maps;
	}
}
