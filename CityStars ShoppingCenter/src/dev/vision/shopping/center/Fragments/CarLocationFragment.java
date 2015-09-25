package dev.vision.shopping.center.Fragments;

import org.json.JSONException;
import org.json.JSONObject;

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
import dev.vision.shopping.center.se.anyro.nfc_reader.record.CityStarsNFC;



public class CarLocationFragment extends Fragment {

	private View v;
	TextView level;
	private TextView number;
	
	public CarLocationFragment() {
	}
	
	/*
	public static CarLocationFragment NewInstance(String levelTxt, String numberTxt) {
		CarLocationFragment d =new CarLocationFragment();
		Bundle bun = new Bundle();
		bun.putString("level", levelTxt);
		bun.putString("number", numberTxt);
		d.setArguments(bun);
		return d;
	}*/
	
	public static Fragment NewInstance(CityStarsNFC cf) {
		CarLocationFragment d =new CarLocationFragment();
		d.setArguments(cf.getBundle());
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.car_location_fragment, null);
		level = (TextView) v.findViewById(R.id.level);
		number = (TextView) v.findViewById(R.id.number);

		return v;
	}
	
	

	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		((ActionBarActivity)getActivity()).getSupportActionBar().hide();
		
		String d = getArguments().getString("NFC","");
		if(CityStarsNFC.isStarsNFC(d)){
			CityStarsNFC cfnc = new CityStarsNFC(d);
			level.setText(cfnc.getLevel());
			number.setText(cfnc.getNumber());
		}
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}	
}
