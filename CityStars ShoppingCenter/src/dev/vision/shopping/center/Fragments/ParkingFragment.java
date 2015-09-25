package dev.vision.shopping.center.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.CityStarsNFC;



public class ParkingFragment extends Fragment implements OnClickListener {
	int posi;

	private View v;



	private FragmentActivity c;

	private AnimationDrawable nfcAnimation;

	private ImageView nfc;

	
	public ParkingFragment() {
	}
	
	public static ParkingFragment NewInstance(int pos) {
		ParkingFragment d =new ParkingFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.parking_fragment, container, false);
		v.findViewById(R.id.scanbarcode).setOnClickListener(this);
		v.findViewById(R.id.wheresmycar).setOnClickListener(this);

	    nfc = (ImageView) v.findViewById(R.id.ImageView03);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	      super.onViewCreated(view, savedInstanceState);
	      nfcAnimation = (AnimationDrawable) nfc.getDrawable();
	      nfc.post(new Runnable() {
			
				@Override
				public void run() {
					nfcAnimation.start();				
				}
	      });
	}
	
	@Override
	public void onPause() {
		  nfcAnimation.stop();
	      super.onPause();
	}

	@Override
	public void onResume() {
	      super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.scanbarcode:
				startScan();
				break;
			case R.id.wheresmycar:
				CityStarsNFC cf = Static.getLocation();
				if(cf == null){
					new SweetAlertDialog(c, SweetAlertDialog.ERROR_TYPE)
			        .setTitleText("No Parking History")
			        .setContentText("You have not registered your parking")
			        .show();
				}else
					switchFragment(CarLocationFragment.NewInstance(cf));
				break;
			default:
				break;
		}
		
	}
	
	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		posi = getArguments().getInt("pos",1);
		
		c = getActivity();
		int icon = Static.icons[posi];
		String txt  = Static.text[posi];
		((ActionBarActivity)getActivity()).getSupportActionBar().show();
		((ActionBarActivity)getActivity()).getSupportActionBar().setIcon(icon);
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(txt);		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
		 
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	

	private void startScan(){
		Activity ac = getActivity();
		if (ac == null)
			return;

		if (ac instanceof CustomAnimation) {
			CustomAnimation ra = (CustomAnimation) ac;
			ra.startScan();
		}
	}
	
	private void switchFragment(Fragment fragment) {
		Activity ac = getActivity();
		if (ac == null)
			return;

		if (ac instanceof CustomAnimation) {
			CustomAnimation ra = (CustomAnimation) ac;
			ra.switchContent(fragment);
		}
	}	
}
