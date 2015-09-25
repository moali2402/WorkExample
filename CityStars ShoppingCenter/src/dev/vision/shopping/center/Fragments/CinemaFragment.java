package dev.vision.shopping.center.Fragments;

import java.util.ArrayList;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;

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
import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.adapter.Menu_Adapter;
import dev.vision.shopping.center.adapter.Movies_Tabbed_Adapter;
import dev.vision.shopping.center.adapter.Tabbed_Adapter;
import dev.vision.shopping.center.api.Movie;
import dev.vision.shopping.center.api.Movies;
import dev.vision.shopping.center.api.Stores;


public class CinemaFragment extends BaseFragment implements OnTabChangeListener {
	private View v;
	int posi;
	SwipeListView lv;
	ArrayList<Integer> openned = new ArrayList<Integer>();
	public CinemaFragment() {
	}
	public static CinemaFragment NewInstance(int pos) {
		CinemaFragment d =new CinemaFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.tabbed_list_fragment, null);
		tabs=(TabHost)v.findViewById(android.R.id.tabhost); //Id of tab host

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

        tabs.setup();
        TabHost.TabSpec spec= createTab("CURRENTLY SHOWING", "CURRENTLY SHOWING", R.id.tab1);
        tabs.addTab(spec); //Add it 
        
        spec= createTab("COMING SOON", "COMING SOON", R.id.tab2);
        tabs.addTab(spec);
        
        tabs.setOnTabChangedListener(this);
		onTabChanged(null);

	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onTabChanged(String arg0) {
		openned.clear();
		lv = (SwipeListView) tabs.getCurrentView();
		Movies mvs = new Movies();
		mvs.add(new Movie("Fast Furious 7","VIP","L.E 100.00", "hours", "http://freehdwal.com/wp-content/uploads/2014/11/fast_and_furious_7_wallpaper_full_wide_background_desktop.png"));
		mvs.add(new Movie("007 Skyfall","DELUXE","L.E 50.00", "hours", "http://cdn.superbwallpapers.com/wallpapers/movies/james-bond-skyfall-14237-1680x1050.jpg"));
		mvs.add(new Movie("007 Skyfall","VIP","L.E 100.00", "hours", "http://cdn.superbwallpapers.com/wallpapers/movies/james-bond-skyfall-14237-1680x1050.jpg"));
		mvs.add(new Movie("Spider-Man","DELUXE","L.E 50.00", "hours", "http://cdn01.wallconvert.com/_media/conv/1/21/208506-spider-man-4.jpg"));
		
		switch(tabs.getCurrentTab()){
		case 0:
			lv.setAdapter(new Movies_Tabbed_Adapter(getActivity(), mvs , lv));
			break;
		case 1:

			break;
		}
		/*
		lv.setSwipeListViewListener(new SwipeListViewListener() {
			
			@Override
			public void onStartOpen(int position, int action, boolean right) {
				
			}
			
			@Override
			public void onStartClose(int position, boolean right) {
				
				
			}
			
			@Override
			public void onOpened(int position, boolean toRight) {
				((CustomAnimation)getActivity()).sm.setSlidingEnabled(false);
				openned.add(position);
			}
			
			@Override
			public void onMove(int position, float x) {
				
				
			}
			
			@Override
			public void onListChanged() {
				
				
			}
			
			@Override
			public void onLastListItem() {
				
				
			}
			
			@Override
			public void onFirstListItem() {
				
				
			}
			
			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				
				
			}
			
			@Override
			public void onClosed(int position, boolean fromRight) {
				if(openned.contains(position))
					openned.remove((Object)position);
				if(openned.isEmpty())
				((CustomAnimation)getActivity()).sm.setSlidingEnabled(true);
			}
			
			@Override
			public void onClickFrontView(int position) {
				lv.openAnimate(position);
			}
			
			@Override
			public void onClickBackView(int position) {
				lv.closeAnimate(position);
			}
			
			@Override
			public void onChoiceStarted() {
				
				
			}
			
			@Override
			public void onChoiceEnded() {
				
				
			}
			
			@Override
			public void onChoiceChanged(int position, boolean selected) {
				
				
			}
			
			@Override
			public int onChangeSwipeMode(int position) {
				
				return 0;
			}
		});*/
	}


}
