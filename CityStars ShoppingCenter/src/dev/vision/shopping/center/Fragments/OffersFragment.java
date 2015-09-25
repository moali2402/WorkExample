package dev.vision.shopping.center.Fragments;

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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.Store_Activity;
import dev.vision.shopping.center.adapter.SampleAdapter;
import dev.vision.shopping.center.adapter.Tabbed_Adapter;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;


public class OffersFragment extends Fragment implements OnQueryTextListener {
	private View v;
	int posi;
	Stores Stores = Static.Stores;
	FloatingGroupExpandableListView myList;
	WrapperExpandableListAdapter wrapperAdapter;
	
	public OffersFragment() {
	}
	public static OffersFragment NewInstance(int pos) {
		OffersFragment d =new OffersFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
		
	}
		

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.offers, null);
//		tabs=(TabHost)v.findViewById(android.R.id.tabhost); //Id of tab host
		myList = (FloatingGroupExpandableListView) v.findViewById(R.id.my_list);

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

		SampleAdapter myAdapter = new SampleAdapter(getActivity());
		wrapperAdapter = new WrapperExpandableListAdapter(myAdapter);
		myList.setAdapter(wrapperAdapter);
		for(int i = 0; i < wrapperAdapter.getGroupCount(); i++) {
			myList.expandGroup(i);
		}
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
			myList.setIndicatorBounds(Static.SCREEN_WIDTH - 150, Static.SCREEN_WIDTH - 20);
		} else {
			myList.setIndicatorBoundsRelative(Static.SCREEN_WIDTH- 150, Static.SCREEN_WIDTH - 20);
		}
		myList.setDivider(null);
		myList.setTextFilterEnabled(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	
	@Override
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.offers, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
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
	        case R.id.expand:
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
		   			myList.collapseGroup(i);
		   	 }	
		}else{
			 item.setTitle("Collapse");
			 item.setIcon(R.drawable.arrow_collapse);
		   	 for(int i = 0; i < wrapperAdapter.getGroupCount(); i++) {
		   			myList.expandGroup(i);
		   	 }	
		}
	}
	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText))
	      {
			myList.clearTextFilter();
	      }
	    else
	      {
	    	myList.setFilterText(newText.toString());
	      }		
		return true;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
