package dev.vision.engizny.fragments;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseQuery;

import dev.vision.engizny.CustomAnimation;
import dev.vision.engizny.R;
import dev.vision.engizny.R.layout;
import dev.vision.engizny.adapters.Menu_Adapter;
import dev.vision.engizny.interfaces.TitledIconed;
import dev.vision.engizny.parse.ParseCategory;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_Menu extends ListFragment {
	Menu_Adapter ma;
	ArrayList<TitledIconed> al = new ArrayList<TitledIconed>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.back_menu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			getFragments();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ma = new Menu_Adapter(getActivity(), al);
		 
		setListAdapter(ma);
		/*
		Fragment newContent = al.get(0);
		 
		 if (newContent != null)
			switchFragment(newContent);
			*/
	}
	
	private void getFragments() throws ParseException {
		//ParseQuery<ParseCategory> pcat = ParseCategory.getQuery();
		

		al.clear();
		
		//Fragment_Engizny ef = Fragment_Engizny.newInstance();
		MainAdFragment ef = new MainAdFragment();

		al.add(ef);

		//for(ParseCategory temp : pcat.find()){
		Fragment_Shopping sup = Fragment_Shopping.newInstance();
		al.add(sup);
		Fragment_Health hea = Fragment_Health.newInstance();
		al.add(hea);
		Fragment_Education edu = Fragment_Education.newInstance();
		al.add(edu);
		Fragment_Financial fin = Fragment_Financial.newInstance();
		al.add(fin);
		Fragment_RealState rs = Fragment_RealState.newInstance();
		al.add(rs);
		Fragment_Restaurants res = Fragment_Restaurants.newInstance();
		al.add(res);
		Fragment_Entertainment ent = Fragment_Entertainment.newInstance();
		al.add(ent);
		Fragment_PersonalService ps = Fragment_PersonalService.newInstance();
		al.add(ps);
		Fragment_Kids kids = Fragment_Kids.newInstance();
		al.add(kids);
		Fragment_Government gov = Fragment_Government.newInstance();
		al.add(gov);
		Fragment_News news = Fragment_News.newInstance();
		al.add(news);
		Fragment_Cars cars = Fragment_Cars.newInstance();
		al.add(cars);

		Fragment_HomeServices homes = Fragment_HomeServices.newInstance();
		al.add(homes);
		Fragment_Pets pets = Fragment_Pets.newInstance();
		al.add(pets);
		Fragment_Ramadan rmd = Fragment_Ramadan.newInstance();
		al.add(rmd);

		/*Fragment_SuperMarket smf = Fragment_SuperMarket.newInstance();
		Fragment_Maintenance mf = Fragment_Maintenance.newInstance();
		Fragment_Restaurants rf = Fragment_Restaurants.newInstance();
		Fragment_Pharmacies pf = Fragment_Pharmacies.newInstance();
		Fragment_Laundry lf = Fragment_Laundry.newInstance();
		Fragment_LIMO lif = Fragment_LIMO.newInstance();
		Fragment_Market maf = Fragment_Market.newInstance();
		Fragment_Vallet vf = Fragment_Vallet.newInstance();

		Fragment_eShopping esf = Fragment_eShopping.newInstance();
		Fragment_OtherServices osf = Fragment_OtherServices.newInstance();
		 */

		/*
		al.add(smf);
		al.add(mf);
		al.add(rf);
		al.add(pf);
		al.add(lf);
		al.add(lif);
		al.add(maf);
		al.add(vf);
		al.add(esf);
		al.add(osf);
		 */
	}

	@Override
	public void onListItemClick(ListView lv, View v, int pos, long id) {
		 ma.setSelecteIndex(pos , v);
		 ((Menu_Adapter)lv.getAdapter()).notifyDataSetChanged();
		 TitledIconed newContent = al.get(pos);
		 
		 if (newContent != null)
			switchFragment(newContent);
	}
	
	// the meat of switching the above fragment
	private void switchFragment(TitledIconed fragment) {
		Activity ac = getActivity();
		if (ac == null)
			return;

		if (ac instanceof CustomAnimation) {
			CustomAnimation ra = (CustomAnimation) ac;
			ra.switchContent((Fragment) fragment);
		}
	}	
	
}
