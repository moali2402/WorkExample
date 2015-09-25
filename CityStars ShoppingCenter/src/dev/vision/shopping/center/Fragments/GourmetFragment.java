package dev.vision.shopping.center.Fragments;

import java.util.ArrayList;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import dev.vision.license.LICENSE;
import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.CustomZoomAnimation;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Splash;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.Store_Activity;
import dev.vision.shopping.center.adapter.Menu_Adapter;
import dev.vision.shopping.center.adapter.Tabbed_Adapter;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;


public class GourmetFragment extends BaseFragment implements OnTabChangeListener, OnQueryTextListener {
	private View v;
	int posi;
	SwipeListView lv;
	ArrayList<Integer> openned = new ArrayList<Integer>();
	private ImageView iv;

	public GourmetFragment() {
	}
	public static GourmetFragment NewInstance(int pos) {
		GourmetFragment d =new GourmetFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = new FrameLayout(getActivity());
		View fm= inflater.inflate(R.layout.tabbed_list_fragment, null);
		((FrameLayout)v).addView(fm);
		tabs=(TabHost)v.findViewById(android.R.id.tabhost); //Id of tab host
    	iv = new ImageView(getActivity());
		((FrameLayout)v).addView(iv);
    	iv.setImageResource(R.drawable.gourmetb);
    	iv.setBackgroundColor(Color.WHITE);
    	iv.setScaleType(ScaleType.CENTER_INSIDE);
		Animation mLoadAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
    	mLoadAnimation.setStartOffset(1000);
		mLoadAnimation.setDuration(1500);
    	mLoadAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				iv.setVisibility(View.GONE);
									
			}
		});
        
       
    	iv.setAnimation(mLoadAnimation);
		
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
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
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("");

        tabs.setup();
        TabHost.TabSpec spec= createTab("ALL " +txt, "ALL " +txt, R.id.tab1);
        tabs.addTab(spec); //Add it 
        
        spec=createTab("FAVOURITES", "FAVOURITES", R.id.tab2);
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
		lv.setOffsetLeft(Static.SCREEN_WIDTH - convertDpToPixel(90));

		switch(tabs.getCurrentTab()){
		case 0:
			lv.setAdapter(new Tabbed_Adapter(getActivity(), new Stores() , new Stores() , new Stores(), lv));
			break;
		case 1:
			lv.setAdapter(new Tabbed_Adapter(getActivity(), new Stores() ,new Stores() , new Stores() , lv));
			break;
		}
		/*
		((SwipeListView)lv).setSwipeListViewListener(new SwipeListViewListener() {
			
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
				//lv.openAnimate(position);
			}
			
			@Override
			public void onClickBackView(int position) {
				//lv.closeAnimate(position);
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
				
				return -1;
			}
		});
		*/
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				Intent i = new Intent(getActivity(), Store_Activity.class);
				i.putExtra("store",  (Store)lv.getAdapter().getItem(pos));
				getActivity().startActivity(i);				
			}
		});
	}
	
	public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
	
	@Override
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {
	    //inflater.inflate(R.menu.main, menu);
	    inflater.inflate(R.menu.offers, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		MenuItem expand = menu.findItem(R.id.expand);
		expand.setVisible(false);
		
	    SearchView searchView = null;
	    
	    searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
	    if(searchView == null)
	    searchView = (SearchView) searchItem.getActionView();
	    SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);
	   
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            return true;
	         default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText))
	      {
	            lv.clearTextFilter();
	      }
	    else
	      {
	            lv.setFilterText(newText.toString());
	      }		
		return true;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


}
