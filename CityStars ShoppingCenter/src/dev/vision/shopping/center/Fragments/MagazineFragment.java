package dev.vision.shopping.center.Fragments;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.MuPDFPageAdapter;
import com.artifex.mupdfdemo.MuPDFReaderView;
import com.artifex.mupdfdemo.SearchTask;
import com.artifex.mupdfdemo.SearchTaskResult;

import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;


public class MagazineFragment extends Fragment implements  OnQueryTextListener {
	int posi;
 
    private static final String TAG = "CityStarsMagazine";
    private static final String SAMPLE_FILE = "sample.pdf";
    private static final String SEARCH_TEXT = "text";
    
    private RelativeLayout mainLayout;
    private MuPDFCore core;
    private MuPDFReaderView mDocView;
    private Context mContext;
    private String mFilePath;
    private SearchTask mSearchTask;

    
	public MagazineFragment() {
	}
	public static MagazineFragment NewInstance(int pos) {
		MagazineFragment d =new MagazineFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 	mContext = getActivity();
	        mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SAMPLE_FILE;
	        		//args.getString("filepath");
	        View rootView = inflater.inflate(R.layout.pdf, container, false);
	        mainLayout = (RelativeLayout) rootView.findViewById(R.id.pdflayout);

	        core = openFile(Uri.decode(mFilePath));

	        if (core != null && core.countPages() == 0) {
	            core = null;
	        }
	        if (core == null || core.countPages() == 0 || core.countPages() == -1) {
	            Log.e(TAG, "Document Not Opening");
	        }
	        if (core != null) {
	            mDocView = new MuPDFReaderView(getActivity()) {
	                @Override
	                protected void onMoveToChild(int i) {
	                    if (core == null)
	                        return;
	                    super.onMoveToChild(i);
	                }

	            };
		        mDocView.setLinksEnabled(true);

	           	            
	            mDocView.setAdapter(new MuPDFPageAdapter(mContext, core));
	            mainLayout.addView(mDocView);
	        }

	        mSearchTask = new SearchTask(mContext, core) {

	            @Override
	            protected void onTextFound(SearchTaskResult result) {
	                SearchTaskResult.set(result);
	                mDocView.setDisplayedViewIndex(result.pageNumber);
	                mDocView.resetupChildren();
	            }
	        };

	        return rootView;
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
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(txt);

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	
	public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
	

    public void search(int direction, String text) {
        int displayPage = mDocView.getDisplayedViewIndex();
        SearchTaskResult r = SearchTaskResult.get();
        int searchPage = r != null ? r.pageNumber : -1;
        mSearchTask.go(text, direction, displayPage, searchPage);
    }


    private MuPDFCore openBuffer(byte buffer[]) {
        System.out.println("Trying to open byte buffer");
        try {
            core = new MuPDFCore(mContext, buffer);
	        
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        return core;
    }

    private MuPDFCore openFile(String path) {
        int lastSlashPos = path.lastIndexOf('/');
        mFilePath = new String(lastSlashPos == -1
                ? path
                : path.substring(lastSlashPos + 1));
        try {
            core = new MuPDFCore(mContext, path);
            // New file: drop the old outline data
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        return core;
    }

    public void onDestroy() {
        if (core != null)
            core.onDestroy();
        core = null;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSearchTask != null)
            mSearchTask.stop();
    }
    
	@Override
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.offers, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		MenuItem expand = menu.findItem(R.id.expand);
		expand.setVisible(false);
		
		
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    if(searchView == null)
	    searchView = (SearchView) searchItem.getActionView();
	    SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);

        /*
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            
        };
        searchView.setOnQueryTextListener(textChangeListener);
         */
	}
	
	@Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        search(1, query);
        return true;
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
	
	
}
