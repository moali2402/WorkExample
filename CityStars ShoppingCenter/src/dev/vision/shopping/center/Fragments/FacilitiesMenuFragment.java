package dev.vision.shopping.center.Fragments;

import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.NFC;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.R.layout;
import dev.vision.shopping.center.adapter.F_Menu_Adapter;
import dev.vision.shopping.center.adapter.Menu_Adapter;
import dev.vision.shopping.center.views.BasicMap;
import dev.vision.shopping.center.views.BoldGillsansTextView;
import dev.vision.shopping.center.views.FacilitiesViewPager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FacilitiesMenuFragment extends ListFragment {
	F_Menu_Adapter ma;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout l = new LinearLayout(getActivity());
		l.setOrientation(LinearLayout.VERTICAL);
		View v = inflater.inflate(R.layout.back_menu, null);
		l.setBackgroundColor(Color.parseColor("#e6be59"));
		BoldGillsansTextView bg = new BoldGillsansTextView(getActivity());
		bg.setText("Facilities");
		bg.setGravity(Gravity.CENTER);
		bg.setPadding(5, 25, 5, 20);
		bg.setTextColor(Color.WHITE);
		bg.setBackgroundColor(Color.DKGRAY);
		l.addView(bg);
		l.addView(v);
		return l;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 ma = new F_Menu_Adapter(getActivity());
				
		setListAdapter(ma);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int pos, long id) {
		 ma.setSelecteIndex(pos , v);
		 ((F_Menu_Adapter)lv.getAdapter()).notifyDataSetChanged();
		 Intent i = new Intent();
		 if(pos==0){
		    i.setAction(FacilitiesViewPager.CUSTOMER_SERVICE);
		 }else if (pos==1){
			i.setAction(FacilitiesViewPager.ELEVATORS);
		 }else if (pos==2){
				i.setAction(FacilitiesViewPager.ESCALATORS);
		 }else if (pos==3){
				i.setAction(FacilitiesViewPager.WC);
		 }else{
				i.setAction(FacilitiesViewPager.ATM);
		 }
		 
		 if (i != null)
			send(i);
	}
	
	// the meat of switching the above fragment
	private void send(Intent i) {
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(i);
		((CustomAnimation)getActivity()).sm.showContent();
	}	
	
}
