package dev.vision.shopping.center.Fragments;

import java.util.ArrayList;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.Store_Activity;
import dev.vision.shopping.center.adapter.CategoriesAdapter;
import dev.vision.shopping.center.adapter.Tabbed_Adapter;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;


public class StoresFragment extends BaseFragment implements OnTabChangeListener, OnQueryTextListener {
	private View v;
	int posi;
	ListView lv;
	Stores Stores = Static.Stores;
	WrapperExpandableListAdapter wrapperAdapter;
	ArrayList<Integer> openned = new ArrayList<Integer>();

	public StoresFragment() {
	}
	public static StoresFragment NewInstance(int pos) {
		StoresFragment d =new StoresFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
		
	}
		

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.stores_list_fragment, null);
		tabs=(TabHost)v.findViewById(android.R.id.tabhost); //Id of tab host

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
		posi = getArguments().getInt("pos",1);
	
		int icon = Static.icons[posi];
		String txt  = Static.text[posi];
		((ActionBarActivity)getActivity()).getSupportActionBar().show();
		((ActionBarActivity)getActivity()).getSupportActionBar().setIcon(icon);
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(txt);

        tabs.setup();
        TabHost.TabSpec spec= createTab("ALL " +txt, "ALL " +txt, R.id.tab1);
        tabs.addTab(spec); //Add it 

        spec= createTab("CATEGORIES", "CATEGORIES", R.id.tab3);
        tabs.addTab(spec);
        
        spec= createTab("FAVOURITES", "FAVOURITES", R.id.tab2);
        tabs.addTab(spec);
        
        tabs.setOnTabChangedListener(this);
		onTabChanged(null);
	}
		
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@SuppressLint("NewApi") @Override
	public void onTabChanged(String arg0) {
		openned.clear();
		getActivity().invalidateOptionsMenu();
		switch(tabs.getCurrentTab()){
		case 0:
			lv = (SwipeListView) tabs.getCurrentView();
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
			});*/
			((SwipeListView)lv).setOffsetLeft(Static.SCREEN_WIDTH - convertDpToPixel(90));

			((SwipeListView)lv).setAdapter(new Tabbed_Adapter(getActivity(), Stores , Static.Stores, Static.favoStore, ((SwipeListView)lv)));
			((SwipeListView)lv).setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
						long arg3) {

					Intent i = new Intent(getActivity(), Store_Activity.class);
					i.putExtra("store",  (Store)lv.getAdapter().getItem(pos));
					getActivity().startActivity(i);				
				}
			});
			break;
		case 1:
			lv = (FloatingGroupExpandableListView) tabs.getCurrentView();

			if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
				((FloatingGroupExpandableListView)lv).setIndicatorBounds(Static.SCREEN_WIDTH - 150, Static.SCREEN_WIDTH - 20);
			} else {
				((FloatingGroupExpandableListView)lv).setIndicatorBoundsRelative(Static.SCREEN_WIDTH- 150, Static.SCREEN_WIDTH - 20);
			}
			((FloatingGroupExpandableListView)lv).setDivider(null);
			CategoriesAdapter myAdapter = new CategoriesAdapter(getActivity(), Static.Catagorised);
			wrapperAdapter = new WrapperExpandableListAdapter(myAdapter);
			((FloatingGroupExpandableListView)lv).setAdapter(wrapperAdapter);
			for(int i = 0; i < wrapperAdapter.getGroupCount(); i++) {
				((FloatingGroupExpandableListView)lv).expandGroup(i);
			}
			((FloatingGroupExpandableListView)lv).setOnChildClickListener(new OnChildClickListener() {
				
				@Override
				public boolean onChildClick(ExpandableListView arg0, View arg1, int g,
						int c, long arg4) {

					Intent i = new Intent(getActivity(), Store_Activity.class);
					i.putExtra("store",  (Store) ((WrapperExpandableListAdapter) ((FloatingGroupExpandableListView)lv).getExpandableListAdapter()).getChild(g, c));
					getActivity().startActivity(i);	
					return true;
				}
			});
			break;
		case 2:
			lv = (SwipeListView) tabs.getCurrentView();
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
			});*/
			((SwipeListView)lv).setOffsetLeft(Static.SCREEN_WIDTH - convertDpToPixel(90));

			((SwipeListView)lv).setFastScrollEnabled(true);
			((SwipeListView)lv).setFastScrollAlwaysVisible(true);

			((SwipeListView)lv).setAdapter(new Tabbed_Adapter(getActivity(), Static.favoStore, Static.Stores, Static.favoStore, ((SwipeListView)lv)));
			((SwipeListView)lv).setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
						long arg3) {

					Intent i = new Intent(getActivity(), Store_Activity.class);
					i.putExtra("store",  (Store)lv.getAdapter().getItem(pos));
					getActivity().startActivity(i);				
				}
			});
			break;
		}
		lv.setTextFilterEnabled(true);
		
		
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

		switch(tabs.getCurrentTab()){
		case 1:
			   expand.setVisible(true);
			   break;
	    default :
			   expand.setVisible(false);
		}
	    SearchView searchView = null;
	    
	    searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
	    if(searchView == null)
	    searchView = (SearchView) searchItem.getActionView();
	    SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );

	    //customise searchview
	    int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		TextView searchText = (TextView) searchView.findViewById(id);
		searchView.setQueryHint("Search Stores");
		searchText.setHintTextColor(getResources().getColor(android.R.color.white));
		
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);
	   
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            return true;
	        case R.id.expand:
	        	if(tabs.getCurrentTab() == 1)
	        	 toggle(item);
		         return true;
	         default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private void toggle(MenuItem item) {
		if(((String)item.getTitle()).equalsIgnoreCase("collapse")){
			item.setTitle("Expand");
			item.setIcon(R.drawable.arrow_expand);
		   	 for(int i = 0; i < wrapperAdapter.getGroupCount(); i++) {
		   		((FloatingGroupExpandableListView)lv).collapseGroup(i);
		   	 }	
		}else{
			 item.setTitle("Collapse");
			 item.setIcon(R.drawable.arrow_collapse);
		   	 for(int i = 0; i < wrapperAdapter.getGroupCount(); i++) {
		   		((FloatingGroupExpandableListView)lv).expandGroup(i);
		   	 }	
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
		return false;
	}
}
