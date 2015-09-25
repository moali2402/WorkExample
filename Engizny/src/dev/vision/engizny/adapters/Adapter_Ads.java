package dev.vision.engizny.adapters;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import dev.vision.engizny.R;
import dev.vision.engizny.classes.SubCategory;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Ads extends FragmentPagerAdapter{

	ArrayList<Fragment> frags; 
	
	public Adapter_Ads(FragmentManager fm, ArrayList<Fragment> l) {
		super(fm);
		frags = l;
	}

	@Override
	public Fragment getItem(int arg0) {
		return frags.get(arg0);
	}

	@Override
	public int getCount() {
		return frags.size();
	}
		
}
