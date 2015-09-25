/**
 * 
 */
package dev.vision.shopping.center;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.squareup.picasso.Picasso;

import de.vogella.algorithms.dijkstra.model.Point;
import dev.vision.shopping.center.Fragments.Mapper_Fragment;
import dev.vision.shopping.center.adapter.FragmentAdapter;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.views.BoldGillsansTextView;
import dev.vision.shopping.center.views.GillsansTextView;
import dev.vision.shopping.center.views.Map;
import dev.vision.shopping.center.views.ViewPagerWrap;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Mo
 *
 */
public class Store_Activity extends FragmentActivity {
	
    Store to;
	private ViewPagerWrap mapper;
	private Store from;
	@Override
	public void onCreate(Bundle savedInstanceState){
        setTheme(R.style.AppTheme);

       
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_page);

		BoldGillsansTextView name = (BoldGillsansTextView) findViewById(R.id.bold_name);
		GillsansTextView level = (GillsansTextView) findViewById(R.id.level);
		ImageView favourited = (ImageView) findViewById(R.id.store_fave);
		ImageView navigate = (ImageView) findViewById(R.id.nav);
		BoldGillsansTextView call = (BoldGillsansTextView) findViewById(R.id.call);
		ImageView store_im = (ImageView) findViewById(R.id.store_im);
		ImageView back = (ImageView) findViewById(R.id.back);

		
		Bundle extras = getIntent().getExtras();
		to= (Store) extras.getSerializable("store");
		final String nam = to.getName();
		int lev = to.getLevel();

		mapper = (ViewPagerWrap) findViewById(R.id.mapper);
		mapper.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getMapsFragments()));
		mapper.setOffscreenPageLimit(6);
		mapper.setPageTransformer(true, new ForegroundToBackgroundTransformer());
		name.setText(nam);
		level.setText("Level " +lev);
		//favourited.setImageResource(R.drawable.favorite_selector);
		if(Static.favoStore.contains(to))
			favourited.setVisibility(View.VISIBLE);
		else
			favourited.setVisibility(View.INVISIBLE);

		navigate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//844 ,535 microsoft, hm 484 ,543
				//mImageView.setNavPoints( new Point(1462 ,607),s.getLocation(), nam);
				Intent i = new Intent(Store_Activity.this,ChooseFrom_Activity.class);
				i.putExtra("store_n", to);
				startActivityForResult(i,55);
				
			}
		});
		
		Picasso.with(this).load(to.getStoreImage()).placeholder(new ColorDrawable(Color.WHITE)).into(store_im);
			
		call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				Intent intent = new Intent(Intent.ACTION_CALL);
		
				intent.setData(Uri.parse("tel:" + "10000000"));
				startActivity(intent);
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		

	}
	
	@Override
	public void onStart(){
		super.onStart();
		mapper.setToStore(to);
	}

	
	@Override
	public void onResume(){
		super.onResume();
		register();
	}
	
	@Override
	public void onPause(){
		unRegister();
		super.onPause();
	}
	
	public void register(){
		IntentFilter i = new IntentFilter("goToNextMap");
		LocalBroadcastManager l = LocalBroadcastManager.getInstance(this);
		l.registerReceiver(BR, i);;
	}
	
	public void unRegister(){
		LocalBroadcastManager l = LocalBroadcastManager.getInstance(this);
		l.unregisterReceiver(BR);
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
          super.onActivityResult(requestCode, resultCode, data);  
              
          if(requestCode==55 && resultCode ==Activity.RESULT_OK)  
          {  
        	  if(from != null)
        		  mapper.reset(from.getLevel());
    		  mapper.reset(to.getLevel());

             from=(Store) data.getSerializableExtra("store");   
     		 mapper.setFromStore(from);
          }  
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
	
	BroadcastReceiver BR = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle data =intent.getExtras();
			if( data != null ){
				Point p = (Point) data.get("store");
				if(p == to.getLocation()){
					mapper.setCurrentItem(from.getLevel());
				}else if ( p == from.getLocation()){
					mapper.setCurrentItem(to.getLevel());
				}
			}
		}
	};

}
