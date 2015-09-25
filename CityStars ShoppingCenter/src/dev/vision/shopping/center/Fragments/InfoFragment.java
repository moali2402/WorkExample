package dev.vision.shopping.center.Fragments;

import com.fortysevendeg.swipelistview.SwipeListView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.adapter.Menu_Adapter;
import dev.vision.shopping.center.adapter.Tabbed_Adapter;
import dev.vision.shopping.center.api.Stores;


public class InfoFragment extends Fragment implements OnTabChangeListener {
	TabHost tabs;
	private View v;
	int posi;
	SwipeListView lv;
	
	public InfoFragment() {
	}
	public static InfoFragment NewInstance(int pos) {
		InfoFragment d =new InfoFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.fragment_info, null);
		return v;
	}
	
	

	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		posi = getArguments().getInt("pos");
		
		int icon = Static.icons[posi];
		String txt  = Static.text[posi];
		((ActionBarActivity)getActivity()).getSupportActionBar().show();
		((ActionBarActivity)getActivity()).getSupportActionBar().setIcon(icon);
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(txt);

       
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onTabChanged(String arg0) {
		
	}


}
