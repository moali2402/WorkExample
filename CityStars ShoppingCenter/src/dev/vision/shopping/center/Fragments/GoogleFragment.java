package dev.vision.shopping.center.Fragments;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import dev.vision.shopping.center.R;



public class GoogleFragment extends SupportMapFragment {

	private View v;
	TextView level;
	private TextView number;
	private GoogleMap googleMap;
	
	public GoogleFragment() {
	}
	
	public static GoogleFragment NewInstance(String levelTxt, String numberTxt) {
		GoogleFragment d = new GoogleFragment();
		Bundle bun = new Bundle();
		bun.putString("level", levelTxt);
		bun.putString("number", numberTxt);
		d.setArguments(bun);
		return d;
	}
	
	public static GoogleFragment NewInstance(Bundle location) {
		GoogleFragment d =new GoogleFragment();
		d.setArguments(location);
		return d;
	}

	
	
	

	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		((ActionBarActivity)getActivity()).getSupportActionBar().hide();
		googleMap = getMap();
		// latitude and longitude
		double latitude = 30.073812;
		double longitude = 31.346257;
		LatLng ll= new LatLng(latitude, longitude);
		// create marker
		MarkerOptions marker = new MarkerOptions().position(ll);
		 
		// Changing marker icon
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_whereami));
		 
		// adding marker
		googleMap.addMarker(marker);
		
		CameraPosition cameraPosition = new CameraPosition.Builder().target(ll).zoom(16).build();
 
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		googleMap.getUiSettings().setZoomControlsEnabled(false); // true to enable
		googleMap.getUiSettings().setZoomGesturesEnabled(false);
		//level.setText(getArguments().getString("level",""));
		//number.setText(getArguments().getString("number", ""));
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}	
}
