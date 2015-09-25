package dev.vision.engizny;


import java.util.Arrays;

import org.apache.http.impl.EnglishReasonPhraseCatalog;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import dev.vision.engizny.CustomAnimation;
import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.Static.LANGUAGE;
import dev.vision.engizny.adapters.NothingSelectedSpinnerAdapter;
import dev.vision.engizny.parse.ParseDistrict;
import dev.vision.engizny.parse.ParseItem;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class Activity_Engizny extends Activity {
	
	private TextView next;
	String TITLE = "ENGIZNY";
	String TITLE_ARABIC = "انجـزنى";
	int ICON= R.drawable.e;

	String[] ar = { "الرحـاب", "التـجمع الخـامس"};
	String[] en = { "El Rehab", "5Th Settlement"};

	String[] ids = {"fB2Q1dIXw5", "n67Jz4ARfZ"};

	String districts[];
	Spinner s;
	ArrayAdapter<String> myAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
		        Window w = getWindow();
		        w.setFlags(
		                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
		                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		        w.setFlags(
		                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   
	        }
	        
		setContentView(R.layout.home);
		s = (Spinner) findViewById(R.id.spinner1);


		districts = Static.LANG == LANGUAGE.ENGLISH	? en :ar;
		
		
		findViewById(R.id.url).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dev-vision.co.uk"));
		        startActivity(browserIntent);				
			}
		});
		

		myAdapter= new ArrayAdapter<String>(this,
                R.layout.spinner_textview, districts );
		
		
		findViewById(R.id.english).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				next.setEnabled(false);
				next.setText("NEXT");

				Static.LANG = LANGUAGE.ENGLISH;				
				districts = en;
				myAdapter= new ArrayAdapter<String>(Activity_Engizny.this,
		                R.layout.spinner_textview, districts );
				
				s.setAdapter(
					      new NothingSelectedSpinnerAdapter(
					    		  myAdapter,
					            R.layout.spinner_textview,
					            Activity_Engizny.this ));
			}
		});
		
		findViewById(R.id.arabic).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				next.setEnabled(false);
				next.setText("دخـول");
				Static.LANG = LANGUAGE.ARABIC;				
				districts = ar;
				myAdapter= new ArrayAdapter<String>(Activity_Engizny.this,
		                R.layout.spinner_textview, districts );
				s.setAdapter(
					      new NothingSelectedSpinnerAdapter(
					    		  myAdapter,
					            R.layout.spinner_textview,
					            Activity_Engizny.this ));
				
			}
		});

		
		next = (TextView) findViewById(R.id.textView1);
		next.setEnabled(false);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(s.getSelectedItemPosition() > 0){
					start(ids[s.getSelectedItemPosition() - 1]);
				}
			}

			
		});
		
		
		s.setAdapter(
			      new NothingSelectedSpinnerAdapter(
			    		  myAdapter,
			            R.layout.spinner_textview,
			            this ));
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position > 0)
				next.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		findViewById(R.id.locate).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
				getLocation();

			}
		});

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
        	ParseQuery<ParseDistrict> disQ = ParseDistrict.getQuery();
        	disQ.whereNear("geopoint", new ParseGeoPoint(loc.getLatitude(), loc.getLongitude()))
        	.getFirstInBackground(new GetCallback<ParseDistrict>() {
				
				@Override
				public void done(ParseDistrict d, ParseException arg1) {
					

					if(d!= null){
						s.setSelection(Arrays.asList(ids).indexOf(d.getObjectId()));
						
						//start(d.getObjectId());
					}
					findViewById(R.id.locate).setEnabled(true);
					findViewById(R.id.progressBar1).setVisibility(View.GONE);
				}
			});
            
        }
	}
	
	private void start(String id) {
		Intent i = new Intent(Activity_Engizny.this, Engizny.class);
		i.putExtra(ParseItem.DISTRICT, id);
		startActivity(i);				
	}

}
