package dev.vision.engizny.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.squareup.picasso.Picasso;

import dev.vision.engizny.ColorGenerator;
import dev.vision.engizny.R;
import dev.vision.engizny.classes.Item;
import dev.vision.engizny.classes.SubCategory;
import dev.vision.engizny.parse.ParseItem;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Items extends BaseAdapter{
	
	ArrayList<Item> items;
	Context cx;
	LayoutInflater inflater;
	private int view;
	class ViewHolder{
		
		TextView name;
		ImageView image;
		TextView price;
		
	}
	
	public Adapter_Items(Context c, int viewId, ArrayList<Item> itemL){
		cx = c;
		inflater = LayoutInflater.from(cx);
		view = viewId;
		items = itemL;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Item getItem(int position) {
		return items.get(position);
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
			
			vh.name = (TextView) convertView.findViewById(R.id.bold_name);
			vh.image = (ImageView) convertView.findViewById(R.id.store_im);
			vh.price = (TextView) convertView.findViewById(R.id.price);

			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		
		Item cat = getItem(position);
		vh.name.setText(cat.getName());
		vh.price.setText(cat.getPriceText());

		if(cat.getImageUrl() != null)
			Picasso.with(cx).load(cat.getImageUrl()).error(R.drawable.e_item).placeholder(new ColorDrawable()).into(vh.image);
		else Picasso.with(cx).load(R.drawable.e).into(vh.image);

		return convertView;
	}
	
}
