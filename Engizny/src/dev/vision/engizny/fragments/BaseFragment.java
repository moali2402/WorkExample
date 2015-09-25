package dev.vision.engizny.fragments;

import java.lang.reflect.Field;

import com.squareup.picasso.Picasso;

import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.Static;
import dev.vision.engizny.Static.LANGUAGE;
import dev.vision.engizny.interfaces.TitledIconed;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

public abstract class BaseFragment extends Fragment implements TitledIconed{
		
	public View v;
	private ImageView icon;
	public ListView list;

	String TITLE;
	String TITLE_ARABIC;
	int ICON;
	ImageView adView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.fragment_base, null);
		icon = (ImageView) v.findViewById(R.id.menu);
		list = (ListView) v.findViewById(R.id.listView1);
		adView = (ImageView) v.findViewById(R.id.ad);

		setIcon();

		icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Engizny) getActivity()).toggle();				
			}
		});
		return v;
	}
	
	public void setIcon(){
		Picasso.with(getActivity()).load(getIcon()).fit().into(icon);
	}
	
	@Override
	public int getIcon() {
		return ICON;
	}

	@Override
	public String getTitle() {
		return Static.LANG == LANGUAGE.ENGLISH? TITLE : TITLE_ARABIC;
	}

	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}
}
