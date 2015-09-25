package dev.vision.shopping.center.adapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;
import dev.vision.shopping.center.views.BoldGillsansTextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class offerAdapter extends BaseExpandableListAdapter implements Filterable {

	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	static Stores mGroups = new Stores();
	static Stores  mGroupsFiltered = new Stores();
	 
	HashMap<Store, ArrayList<String[]>> childData = new HashMap<Store, ArrayList<String[]>>();

	public offerAdapter(Context context, HashMap<Store, ArrayList<String[]>> gro) {
		
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//mChildsDate 
		childData.putAll(gro);
		for(Store s : gro.keySet()){
			mGroups.add(s);
		}
		mGroupsFiltered.addAll(mGroups);
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
		text.setText(((Store) getGroup(groupPosition)).getName());
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
	public String[] getChild(int groupPosition, int childPosition) {
		return childData.get((getGroup(groupPosition))).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.expandable_list_child_item, null, false);
		}

		final TextView text = (TextView) convertView.findViewById(R.id.bold_name);
		final TextView date = (TextView) convertView.findViewById(R.id.date);

		text.setText(getChild(groupPosition,childPosition)[0]);
		date.setText(getChild(groupPosition,childPosition)[1]);
		return convertView;
	}

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
	            	Stores l = new Stores();
		            for(Store s : mGroupsFiltered){
		            	if(s.getName().toLowerCase(Locale.US).contains(((String) charSequence).toLowerCase(Locale.US)))
		            		l.add(s);
		            }
		            mGroups.clear();
		            mGroups.addAll(l);
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
}