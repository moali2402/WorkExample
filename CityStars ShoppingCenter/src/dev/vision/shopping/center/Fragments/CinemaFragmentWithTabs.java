package dev.vision.shopping.center.Fragments;

import com.fortysevendeg.swipelistview.SwipeListView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;


public class CinemaFragmentWithTabs extends Fragment {
	TabHost tabs;
	private View v;
	int posi;
	SwipeListView lv;
	
	public CinemaFragmentWithTabs() {
	}
	public static CinemaFragmentWithTabs NewInstance(int pos) {
		CinemaFragmentWithTabs d =new CinemaFragmentWithTabs();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.cinema_fragment, null);

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
		Bundle bun = getArguments();
		if(bun !=null)
		posi = bun.getInt("pos");
		Activity ac = getActivity();
		int icon = Static.icons[posi];
		String txt  = Static.text[posi];
		ac.getActionBar().show();
		ac.getActionBar().setIcon(icon);
		ac.getActionBar().setTitle(txt);
		
		ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab tab = actionBar.newTab()
	                       .setText("MOVIES")
	                       .setTabListener(new TabListener<StoresFragment>(
	                    		   getActivity(), "MOVIES", StoresFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	                   .setText("COMING SOON")
	                   .setTabListener(new TabListener<CinemaFragmentWithTabs>(
	                		   getActivity(), "COMING SOON", CinemaFragmentWithTabs.class));
	    actionBar.addTab(tab);
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag for the fragment
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction ft) {
		if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = StoresFragment.NewInstance(1);
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
		  if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }		
	}

}


}
