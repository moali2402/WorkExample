package dev.vision.shopping.center.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.squareup.picasso.Picasso;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class Tabbed_Adapter extends BaseAdapter implements Filterable, SectionIndexer {
    int icons[] = Static.icons;
    ArrayList<String> mSuggestions = null;
    Stores allStores;
    Stores favStores;
    String text[] = Static.text;
    int selecteIndex = 0;
    private Context con;
    Stores Stores;
    SwipeListView lv;
    Stores oldStores;
    
	HashMap<String, Integer> mapIndex;
	String[] sections;



	public Tabbed_Adapter(Context c, Stores stores, Stores allStores, Stores favStores, SwipeListView lv){
		con = c;
		Stores = stores;
		mapIndex = new LinkedHashMap<String, Integer>();
		
		for (int x = 0; x < Stores.size(); x++) {
			String n = Stores.get(x).getName();
			String ch = n.substring(0, 1);
			ch = ch.toUpperCase(Locale.US);

			// HashMap will prevent duplicates
			mapIndex.put(ch, x);
		}
		Set<String> sectionLetters = mapIndex.keySet();

		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

		Collections.sort(sectionList);

		sections = new String[sectionList.size()];

		sectionList.toArray(sections);

		Collections.sort(Stores, new Comparator<Store>()
		{
		    @Override
		    public int compare(Store s1, Store s2)
		    {
		        return s1.getName().compareToIgnoreCase(s2.getName());
		    }
		});
		
		this.allStores = allStores;
		this.favStores = favStores;

		this.lv =lv;
		oldStores = new Stores();
        oldStores.addAll(Stores);
	}

	@Override
	public int getCount() {
		return Stores.size();
	}

	@Override
	public Object getItem(int arg0) {
		return Stores.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	boolean down;
	@SuppressLint("NewApi") @Override
	public View getView(final int pos, View convertView, ViewGroup parent) {
		LayoutInflater ll = LayoutInflater.from(con);
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = ll.inflate(R.layout.tab_list_item, parent ,false);
			vh.im = (ImageView) convertView.findViewById(R.id.store_im);
			vh.fav = (ImageView) convertView.findViewById(R.id.favorite);
			vh.nav = (ImageView) convertView.findViewById(R.id.navigate);
			vh.stor_fv = (ImageView) convertView.findViewById(R.id.store_fave);
			vh.featured = (ImageView) convertView.findViewById(R.id.featured);

			vh.txt = (TextView) convertView.findViewById(R.id.bold_name);
			vh.level = (TextView) convertView.findViewById(R.id.level);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag(); 
		}
		final Store s = Stores.get(pos);
		
		Picasso.with(con).load(s.getStoreImage()).placeholder(new ColorDrawable(Color.parseColor("#8eb5cf"))).into(vh.im);

		vh.txt.setText(s.getName());
		vh.level.setText("Level " + s.getLevel());
		vh.fav.setActivated(s.isFavorite());
		
		if(s.isFavorite())
			vh.stor_fv.setVisibility(View.VISIBLE);
		else
			vh.stor_fv.setVisibility(View.INVISIBLE);

		if(s.isFeatured())
			vh.featured.setVisibility(View.VISIBLE);
		else
			vh.featured.setVisibility(View.INVISIBLE);

		
		vh.fav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleFav(v, pos, s);
			}
		});
		
		//final View f = convertView;
		/*
		convertView.findViewById(R.id.front).setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				if(e.getAction() == MotionEvent.ACTION_MOVE || e.getAction() == MotionEvent.ACTION_CANCEL){
					down = false;
				}else{
					if(e.getAction() == MotionEvent.ACTION_DOWN){
						down=true;
					}else if(e.getAction() == MotionEvent.ACTION_UP && down){
						down = false;
					    lv.performItemClick(f, pos, pos);
					}
				}
				return false;
			}
		});
		*/

		return convertView;
		
	}
	
	protected void toggleFav(View v, int pos, Store s) {
		int index =allStores.indexOf(s);
		int favSize = favStores.size();
		lv.closeOpenedItems();

		if(v.isActivated()){
			v.setActivated(false);
			favStores.remove(s);
			allStores.get(index).setFavorite(false);
		}else{
			v.setActivated(true);
			allStores.get(index).setFavorite(true);
			favStores.add(index >  favSize ? favSize : index, s);
		}
		((Tabbed_Adapter)lv.getAdapter()).notifyDataSetChanged();		

	}

	@Override
	public boolean hasStableIds(){
		return true;
		
	}
	
	@Override
	public Filter getFilter(){
	    Filter myFilter = new Filter() {


			@Override
	        protected FilterResults performFiltering(CharSequence charSequence) {
	            FilterResults filterResults = new FilterResults();
	            if ( charSequence != null ){
	
		            
		            Stores.clear();
		            for(Store s : oldStores){
		            	if(s.getName().toLowerCase(Locale.US).startsWith(((String) charSequence).toLowerCase(Locale.US)))
		            		Stores.add(s);
		            }
	            }else{
	            	
	            }
	            return filterResults;
	        }

	        @Override
	        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
	            if ( filterResults != null && filterResults.count > 0 )
	                notifyDataSetChanged();
	            else
	                notifyDataSetInvalidated();
	        }
	    };
	    return myFilter;
	}

	
	
	public class ViewHolder{
		public ImageView featured;
		ImageView stor_fv;
		ImageView im ;
		ImageView fav ;
		ImageView nav ;

		TextView txt ;
		TextView level ;

	}


	@Override
	public int getPositionForSection(int section) {
		return mapIndex.get(sections[section]);
	}
	
	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}

}
