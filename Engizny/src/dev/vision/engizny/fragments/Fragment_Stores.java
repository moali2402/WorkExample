package dev.vision.engizny.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import dev.vision.engizny.ColorGenerator;
import dev.vision.engizny.Engizny;
import dev.vision.engizny.R;
import dev.vision.engizny.ShowMapActivity;
import dev.vision.engizny.adapters.Adapter_Ads;
import dev.vision.engizny.classes.Store;
import dev.vision.engizny.classes.SubCategory;
import dev.vision.engizny.parse.ParseAds;
import dev.vision.engizny.parse.ParseCategory;
import dev.vision.engizny.parse.ParseStore;
import dev.vision.engizny.parse.ParseSub;
import dev.vision.engizny.parse.ParseAds.TYPES;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment_Stores extends BaseFragment {
	
	
	private static String SUB_ID = "SUB_ID";
	private Adapter_Stores storesAdapter;
	
	ArrayList<Store> stores = new ArrayList<Store>();



	public Fragment_Stores() {}
	

	public static Fragment_Stores newInstance(String oid, String name) {
		Fragment_Stores smf = new Fragment_Stores();
		Bundle b =  new Bundle();
		b.putString(SUB_ID  , oid);
		smf.setArguments(b);

		smf.TITLE = name;
		smf.TITLE_ARABIC = name;
		smf.ICON = R.drawable.shop;
		return smf; 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = super.onCreateView(inflater, container, savedInstanceState);
		return v;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		String id = getArguments().getString(SUB_ID);

		storesAdapter = new Adapter_Stores(getActivity(), R.layout.list_item, stores);
		list.setAdapter(storesAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		         
				Store s = stores.get(position);
				Intent i = new Intent(getActivity(), ShowMapActivity.class);
				i.putExtra("STORE_ID", s.getObjectId());
				startActivity(i);

			}
		});

		try {
			ParseQuery<ParseAds> pads = ParseAds.getQuery();
			pads.whereEqualTo(ParseAds.TYPE, TYPES.SUB.name())
			.findInBackground(new FindCallback<ParseAds>() {
				
				@Override
				public void done(List<ParseAds> adList, ParseException e) {
					if(e==null){
						if(adList != null && adList.size()>0){
							Collections.shuffle(adList);
							Picasso.with(getActivity()).load(adList.get(new Random().nextInt(adList.size())).getAd().getUrl()).noPlaceholder().into(adView);
						}
					}
				}
			});
			
			
			ParseSub sub =  new ParseSub();
			sub.setObjectId(id);
			sub.fetchIfNeeded();
			
			
			sub.getStores(((Engizny)getActivity()).getDistrict_id(), new FindCallback<ParseStore>() {
				

				@Override
				public void done(List<ParseStore> ol, ParseException e) {
					if(e == null ){
						if(ol != null){
							ArrayList<Store> temp = new ArrayList<Store>();
			
							for(ParseStore st : ol){
								Store s = new Store(st);
								temp.add(s);
							}
							stores.clear();
							stores.addAll(temp);
							storesAdapter.notifyDataSetChanged();
						}
					}
				}
			});
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public int getIcon() {
		return ICON;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	class Adapter_Stores extends BaseAdapter{

		ArrayList<Store> stores;
		Context cx;
		LayoutInflater inflater;
		private int view;
		class ViewHolder{
			
			TextView name;
			ImageView image;
			
		}
		
		public Adapter_Stores(Context c, int viewId, ArrayList<Store> sl){
			cx = c;
			inflater = LayoutInflater.from(cx);
			view = viewId;
			stores = sl;
		}

		@Override
		public int getCount() {
			return stores.size();
		}

		@Override
		public Store getItem(int position) {
			return stores.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView == null){
				vh = new ViewHolder();
				convertView = inflater.inflate(view, parent, false);
				
				vh.name = (TextView) convertView.findViewById(R.id.name);
				vh.image = (ImageView) convertView.findViewById(R.id.image);
				
				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();
			}
			
			Store s = getItem(position);
			vh.name.setText(s.getName());
			ColorGenerator g = ColorGenerator.MATERIAL;
			//convertView.setBackgroundColor(g.getRandomColor());
			if(s.getImageUrl() != null)
			Picasso.with(cx).load(s.getImageUrl()).placeholder(new ColorDrawable()).into(vh.image);
			return convertView;		
		}
		
	}
	
	
}
