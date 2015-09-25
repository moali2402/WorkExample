package dev.vision.engizny.adapters;

import java.util.ArrayList;
import java.util.Random;

import com.squareup.picasso.Picasso;

import dev.vision.engizny.ColorGenerator;
import dev.vision.engizny.R;
import dev.vision.engizny.classes.SubCategory;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Subs extends BaseAdapter{
	
	ArrayList<SubCategory> subList;
	Context cx;
	LayoutInflater inflater;
	private int view;
	class ViewHolder{
		
		TextView name;
		ImageView image;
		
	}
	
	public Adapter_Subs(Context c, int viewId, ArrayList<SubCategory> sl){
		cx = c;
		inflater = LayoutInflater.from(cx);
		view = viewId;
		subList = sl;
	}

	@Override
	public int getCount() {
		return subList.size();
	}

	@Override
	public SubCategory getItem(int position) {
		return subList.get(position);
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
		
		SubCategory cat = getItem(position);
		vh.name.setText(cat.getName());
		ColorGenerator g = ColorGenerator.MATERIAL;

		convertView.setBackgroundColor(g.getRandomColor());
		if(cat.getImageUrl() != null){
			Picasso.with(cx).load(cat.getImageUrl()).placeholder(new ColorDrawable()).into(vh.image);
		}else{ Picasso.with(cx).load(R.drawable.e).into(vh.image);}
		return convertView;
	}
	
}
