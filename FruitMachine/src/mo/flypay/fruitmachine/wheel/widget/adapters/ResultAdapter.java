package mo.flypay.fruitmachine.wheel.widget.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mo.flypay.fruitmachine.R;
import mo.flypay.fruitmachine.model.HistoryItem;

/**
 * History Items Adapter machine adapter
 */
public class ResultAdapter extends BaseAdapter {

	private ArrayList<HistoryItem> items;
	private LayoutInflater inflater;

	public ResultAdapter(Context context, ArrayList<HistoryItem>arrayList) {
		this.inflater = LayoutInflater.from(context);
		this.items = arrayList;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public HistoryItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if (convertView == null) {
			viewHolder = new ViewHolder(); 
			convertView =  inflater.inflate(R.layout.history_list_item, parent, false);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.snapshot);
			viewHolder.info = (TextView) convertView.findViewById(R.id.info);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		HistoryItem i = getItem(position);
		
		viewHolder.image.setImageBitmap(i.getSnapshot());
		viewHolder.info.setText(i.toString());

		return convertView;
	}
	

	class ViewHolder{
		public TextView info;
		public ImageView image;
	}	
}