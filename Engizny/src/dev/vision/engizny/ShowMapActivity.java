package dev.vision.engizny;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import dev.vision.engizny.adapters.Adapter_Items;
import dev.vision.engizny.classes.Item;
import dev.vision.engizny.classes.Store;
import dev.vision.engizny.parse.ParseItem;
import dev.vision.engizny.parse.ParseStore;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowMapActivity extends ActionBarActivity  implements OnMarkerClickListener{


	private GoogleMap googleMap;
    public static Context context;
    

	private ListView lv;

	private SupportMapFragment map_frag;

	
	Store store;
	
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        context = this;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
	        Window w = getWindow();
	        w.setFlags(
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	        w.setFlags(
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   
        }
        setContentView(R.layout.store_page);
        
        lv =  (ListView) findViewById(R.id.items_list);

        final ImageView icon =  (ImageView) findViewById(R.id.store_im);
        final ImageView back =  (ImageView) findViewById(R.id.back);

        final TextView name =  (TextView) findViewById(R.id.bold_name);
        final TextView address =  (TextView) findViewById(R.id.descript);
        final ImageView call =  (ImageView) findViewById(R.id.call);
        final ImageView web =  (ImageView) findViewById(R.id.website);

        final ImageView face =  (ImageView) findViewById(R.id.facebook);
        final ImageView twit =  (ImageView) findViewById(R.id.twitter);
        
        back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
        
        call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<String> numbers = store.getNumbers();
				if(numbers.size() == 1){
					call(numbers.get(0));
				}else if(numbers.size() > 1){
					call(numbers.get(0));
				}
			}
		});
					
        web.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String url = store.getWebsite();
				if (!url.startsWith("http://") && !url.startsWith("https://"))
					   url = "http://" + url;
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);
			}
		});
		
		face.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String url = store.getFacebook();
				if (!url.startsWith("http://") && !url.startsWith("https://"))
					   url = "http://" + url;
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);
			}
		});
		
		twit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String url = store.getTwitter();
				if (!url.startsWith("http://") && !url.startsWith("https://"))
					   url = "http://" + url;
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);
			}
		});
		
        //setupMenu();
		MapsInitializer.initialize(ShowMapActivity.this);
		
		String id = getIntent().getExtras().getString("STORE_ID");
		ParseStore s = new ParseStore();
		s.setObjectId(id);
		s.fetchInBackground(new GetCallback<ParseStore>() {

			@Override
			public void done(ParseStore s, ParseException ex) {
				if(ex == null && s != null){
					store = new Store(s);
					if(store != null){
						if(store.getLogoUrl() == null || store.getLogoUrl().trim().equals(""))
							Picasso.with(ShowMapActivity.this).load(store.getImageUrl()).error(R.drawable.e_item).placeholder(R.drawable.e_item).into(icon);
						else
							Picasso.with(ShowMapActivity.this).load(store.getLogoUrl()).error(R.drawable.e_item).placeholder(R.drawable.e_item).into(icon);

						name.setText(store.getName());
						address.setText(store.getDesription());
						if(store.getWebsite() ==null || store.getWebsite().equals("")){
							web.setVisibility(View.GONE);
						}else{
							web.setVisibility(View.VISIBLE);
						}
						
						if(store.getFacebook() ==null || store.getFacebook().equals("")){
							face.setVisibility(View.GONE);
						}else{
							face.setVisibility(View.VISIBLE);
						}
						
						if(store.getTwitter()==null || store.getTwitter().equals("")){
							twit.setVisibility(View.GONE);
						}else{
							twit.setVisibility(View.VISIBLE);
						}
	
						if(store.getNumbers()==null || store.getNumbers().size() == 0 || store.getNumbers().get(0).equals("")){
							call.setVisibility(View.GONE);
						}else{
							call.setVisibility(View.VISIBLE);
						}
						
						store.getItems(new FindCallback<ParseItem>() {
				
							@Override
							public void done(List<ParseItem> items, ParseException e) {
								
								if(e == null){
									if(items != null){
										ArrayList<Item> i = new ArrayList<Item>();
										for(ParseItem pit : items){
											Item it = new Item(pit);
											i.add(it);
										}
										lv.setAdapter(new Adapter_Items(ShowMapActivity.this, R.layout.item_item, i));;
										getTotalHeightofListView(lv);
									}
								}
							}
						});	
						if(googleMap == null)
				           setUpMapIfNeeded();
						else
							addRestaurants(store.getLocation(), store.getName());	
					}	
	  		  	}
			}
		});
       
    }
    
    public void getTotalHeightofListView(ListView listView) {

	    ListAdapter mAdapter = listView.getAdapter();
	    if(mAdapter !=null && !mAdapter.isEmpty()){
		    int totalHeight = 0;
	
		    for (int i = 0; i < mAdapter.getCount(); i++) {
		        View mView = mAdapter.getView(i, null, listView);
	
		        mView.measure(
		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
	
		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	
		        totalHeight += mView.getMeasuredHeight();
	
		    }
	
		    ViewGroup.LayoutParams params = listView.getLayoutParams();
		    params.height = totalHeight
		            + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		    listView.setLayoutParams(params);
		    listView.requestLayout();
	    }
	}


    protected void call(String no) {
    	Intent intent = new Intent(Intent.ACTION_CALL);
		
		intent.setData(Uri.parse("tel:" + no));
		startActivity(intent);		
	}


	@Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.

        	map_frag = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview));
        	googleMap = map_frag.getMap();
        	
    		//addRestaurants();
    		//addMe();
    		
    		googleMap.setOnMarkerClickListener(this);
    		    		     
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
            	googleMap.setMyLocationEnabled(true);
            	googleMap.getUiSettings().setCompassEnabled(false);
            	googleMap.getUiSettings().setZoomControlsEnabled(false);
            	googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            	if(store != null)
					addRestaurants(store.getLocation(), store.getName());	
            }
        }
       
    }


    private void updateCameraLocation(LatLng loc) {
    	if (loc != null) {
    		  CameraUpdate update = CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(loc, 14.0f));
	        	googleMap.moveCamera(update);
        }
	}
    
   


	private void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        String provider = lm.getBestProvider(criteria, true);
        if (provider == null) {
            return ;
        }
        Location loc = lm.getLastKnownLocation(provider);
        if (loc != null) {
            updateCameraLocation(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }
        lm.requestLocationUpdates(provider, 1 * 60 * 1000 , 10, new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
			}
			
			@Override
			public void onLocationChanged(Location loc) {
	            updateCameraLocation(new LatLng(loc.getLatitude(), loc.getLongitude()));
			}
		});        
    }
    
	
	

	

    
  
	private void addRestaurants(LatLng rll, String n) {
		IconGenerator ic = new IconGenerator(this);
		ic.setStyle(IconGenerator.STYLE_PURPLE);
		ic.setColor(getResources().getColor(R.color.faded_red));
		ic.setContentRotation(-90);
		ic.setRotation(90);
		MarkerOptions rsm = new MarkerOptions().position(rll);
		
		Bitmap rsBitmap = ic.makeIcon(n);
		// Changing marker icon
		rsm.icon(BitmapDescriptorFactory.fromBitmap(rsBitmap));
		rsBitmap.recycle();
		
		googleMap.addMarker(rsm);
        updateCameraLocation(rll);

	}


	@Override
	public boolean onMarkerClick(final Marker marker) {
		marker.showInfoWindow();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				marker.hideInfoWindow();				
			}
		}, 3000);
		return true;
	}


	

    
}
