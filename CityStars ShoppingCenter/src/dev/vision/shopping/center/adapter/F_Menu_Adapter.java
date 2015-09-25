package dev.vision.shopping.center.adapter;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.R.drawable;
import dev.vision.shopping.center.R.id;
import dev.vision.shopping.center.R.layout;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class F_Menu_Adapter extends BaseAdapter {
    int icons[] = Static.r_icons;
    
    String text[] = Static.r_text;
    int selecteIndex = -1;
    private Context con;

	public F_Menu_Adapter(Context c){
		con = c;
	}

	@Override
	public int getCount() {
		return icons.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("NewApi") @Override
	public View getView(int arg0, View convertView, ViewGroup parent) {

		LayoutInflater ll = LayoutInflater.from(con);
		View v= ll.inflate(R.layout.slidemenu_listitem,parent , false);
		ImageView im = (ImageView) v.findViewById(R.id.imageView1);
		TextView txt = (TextView) v.findViewById(R.id.textView1);

		im.setImageResource(icons[arg0]);
		txt.setText(text[arg0]);
		v.setBackground(null);
		if(selecteIndex == arg0){
			v.setBackgroundResource(R.drawable.list_back);
		}
		return v;
		
	}
	
	@Override
	public boolean hasStableIds(){
		return true;
		
	}

	public void setSelecteIndex(int pos, View v) {
		
		 v.setBackgroundResource(R.drawable.list_back);
         selecteIndex = pos;
	}
   
}
