package dev.vision.engizny.fragments;

import com.squareup.picasso.Picasso;

import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.interfaces.TitledIconed;
import dev.vision.engizny.views.AdImageView;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class AdFragment extends Fragment{
		
	private static final String URL = "AD_URL";
	private ImageView v;

	public static AdFragment newInstance(String ad_url) {
		AdFragment smf = new AdFragment();
		Bundle b =  new Bundle();
		b.putString(URL , ad_url);
		smf.setArguments(b);
		return smf; 
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= new AdImageView(getActivity());
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		String url = getArguments().getString(URL);
		if(url != null)
			Picasso.with(getActivity()).load(url).fit().placeholder(new ColorDrawable()).into(v);
			
	}
	
	
}
