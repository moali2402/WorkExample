package dev.vision.engizny.fragments;

import dev.vision.engizny.CustomAnimation;
import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.adapters.NothingSelectedSpinnerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class Fragment_Engizny extends BaseFragment {
	
	
	private View v;
	private TextView next;
	
	static Fragment_Engizny hf;


	public Fragment_Engizny() {}
	

	public static Fragment_Engizny newInstance() {
		if(hf == null )
			hf = new Fragment_Engizny();
			hf.TITLE = "ENGIZNY";
			hf.TITLE_ARABIC = "انجـزنى";
			hf.ICON= R.drawable.e;
		return hf; 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.home, null);
		v.findViewById(R.id.url).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dev-vision.co.uk"));
		        startActivity(browserIntent);				
			}
		});
		
		next = (TextView) v.findViewById(R.id.textView1);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switchFragment(new MainAdFragment());
			}
		});
		
		return v;
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
	
	
	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		String[] ob = {"الرحاب", "مدينتى", "التجمع الخامس", "الشروق"};
		Spinner s = (Spinner) getView().findViewById(R.id.spinner1);
		ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_textview, ob );
		
		s.setAdapter(
			      new NothingSelectedSpinnerAdapter(
			    		  myAdapter,
			            R.layout.spinner_textview,
			            getActivity()));
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
