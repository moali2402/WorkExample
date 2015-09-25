package dev.vision.shopping.center.adapter;
import java.util.ArrayList;
import java.util.Locale;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.api.Store;
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

public class SampleAdapter extends BaseExpandableListAdapter implements Filterable {

	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	static ArrayList<String> mGroups = new ArrayList<String>();
	static ArrayList<String>  mGroupsFiltered = new ArrayList<String>();

	static{
		mGroups.add("H&M");
		mGroups.add("NewLook");
		mGroups.add("Chilis");
		mGroups.add("Next");
		mGroupsFiltered.addAll(mGroups);
	}
	
	
	
	private final int[] mGroupDrawables = {
	};

	private final String[][] mChildsName = {
			{"15% Off"},
			{"20% Off"},
			{"5% Off","Free Delivery"},
			{"Summer Sale","10% Off Shorts"}
	};
	
	private final String[][] mChildsDate = {
			{"1.5"},
			{"July 21 - August 21"},
			{"For 1 week starting 1st October","Today"},
			{"September 1","september 30"}
	};

	public SampleAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		return mChildsName[mGroupsFiltered.indexOf(getGroup(groupPosition))].length;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChildsName[mGroupsFiltered.indexOf(getGroup(groupPosition))][childPosition];
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

		text.setText((String) getChild(groupPosition,childPosition));
		date.setText(mChildsDate[mGroupsFiltered.indexOf(getGroup(groupPosition))][childPosition]);
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
	            	ArrayList<String> l = new ArrayList<String>();
		            for(String s : mGroupsFiltered){
		            	if(s.toLowerCase(Locale.US).contains(((String) charSequence).toLowerCase(Locale.US)))
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