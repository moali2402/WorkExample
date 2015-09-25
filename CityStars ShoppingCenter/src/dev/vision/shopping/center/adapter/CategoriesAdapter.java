package dev.vision.shopping.center.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.squareup.picasso.Picasso;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.adapter.Tabbed_Adapter.ViewHolder;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesAdapter extends BaseExpandableListAdapter implements Filterable {

	private final Context mContext;
	private final LayoutInflater mLayoutInflater;

	ArrayList<String> mGroups = new ArrayList<String>();
	HashMap<String, Stores>  mGroupsFiltered = new HashMap<String, Stores>();

	HashMap<String, Stores> childData = new HashMap<String, Stores>();

	public CategoriesAdapter(Context context, HashMap<String, Stores> gro) {
		
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//mChildsDate 
		childData.putAll(gro);
		mGroupsFiltered.putAll(childData);

		for(String s : gro.keySet()){
			mGroups.add(s);
		}
	}

	@Override
	public int getGroupCount() {
		return mGroups.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroups.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.expandable_list_group, parent, false);
			//convertView = new BoldGillsansTextView(mContext);
		}

		final TextView text = (TextView) convertView.findViewById(R.id.sample_activity_list_group_item_text);
		text.setText((String) getGroup(groupPosition));
/*
		final ImageView expandedImage = (ImageView) convertView.findViewById(R.id.sample_activity_list_group_expanded_image);
		final int resId = isExpanded ? R.drawable.minus : R.drawable.plus;
		expandedImage.setImageResource(resId);
		*/

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childData.get((getGroup(groupPosition))).size();
	}

	@Override
	public Store getChild(int groupPosition, int childPosition) {
		return childData.get((getGroup(groupPosition))).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		//if(convertView == null){
		//	convertView = mLayoutInflater.inflate(R.layout.expandabletab_list_item, null, false);
		//}
		/*
		 * ImageView im = (ImageView) convertView.findViewById(R.id.imageView1);
		ImageView fav = (ImageView) convertView.findViewById(R.id.favorite);
		ImageView nav = (ImageView) convertView.findViewById(R.id.navigate);
		ImageView store = (ImageView) convertView.findViewById(R.id.navigate);


		TextView txt = (TextView) convertView.findViewById(R.id.bold_name);
		TextView level = (TextView) convertView.findViewById(R.id.level);
		
		 */
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.expandabletab_list_item, parent, false);
			vh.im = (ImageView) convertView.findViewById(R.id.store_im);
			vh.fav = (ImageView) convertView.findViewById(R.id.favorite);
			vh.nav = (ImageView) convertView.findViewById(R.id.navigate);
			vh.stor_fv = (ImageView) convertView.findViewById(R.id.store_fave);

			vh.txt = (TextView) convertView.findViewById(R.id.bold_name);
			vh.level = (TextView) convertView.findViewById(R.id.level);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag(); 
		}
		
		final Store s = getChild(groupPosition, childPosition);

		Picasso.with(mContext).load(s.getStoreImage()).placeholder(new ColorDrawable(Color.parseColor("#8eb5cf"))).into(vh.im);

		vh.txt.setText(s.getName());
		vh.level.setText("Level " + s.getLevel());
		vh.fav.setActivated(s.isFavorite());
		
		if(s.isFavorite())
			vh.stor_fv.setVisibility(View.VISIBLE);
		else
			vh.stor_fv.setVisibility(View.INVISIBLE);

		
		vh.fav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//toggleFav(v, pos, s);
			}
		});
		
		return convertView;
	}
	

	/*
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
	*/

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public Filter getFilter(){
	    Filter myFilter = new Filter() {


			@Override
	        protected FilterResults performFiltering(CharSequence charSequence) {
	            FilterResults filterResults = new FilterResults();
	            if ( charSequence != null ){
	            	HashMap<String, Stores>  filtered = new HashMap<String, Stores>();
	            	int i =0;
		            for(Stores s : mGroupsFiltered.values()){
		            	Stores l = new Stores();
		            	for(Store st : s){
		            	   if(st.getName().toLowerCase(Locale.US).contains(((String) charSequence).toLowerCase(Locale.US)))
		            		 l.add(st);
		            	}
		            	filtered.put(mGroups.get(i), l);
		            	i++;
		            }
		            childData.clear();
		            childData.putAll(filtered);
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
		ImageView stor_fv;
		ImageView im ;
		ImageView fav ;
		ImageView nav ;

		TextView txt ;
		TextView level ;
	}

}