package dev.vision.shopping.center.adapter;

import java.util.ArrayList;
import java.util.Locale;

import com.fortysevendeg.swipelistview.SwipeListView;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseFromAdapter extends BaseAdapter implements Filterable{


	    int icons[] = Static.icons;
	    ArrayList<String> mSuggestions = null;
	    String text[] = Static.text;
	    int selecteIndex = 0;
	    private Context con;
	    Stores Stores;
	    Stores oldStores;

		public ChooseFromAdapter(Context c, Stores allStores){
			con = c;
			this.Stores=new Stores();
			this.Stores.addAll(allStores);

			oldStores = new Stores();
	        oldStores.addAll(allStores);
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
			convertView = LayoutInflater.from(con).inflate(R.layout.list_child_item, null);
			final Store s = Stores.get(pos);
			
			((TextView)convertView.findViewById(R.id.bold_name)).setText(s.getName() +" - Level "+ s.getLevel());

			return convertView;
			
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
			            filterResults.count=Stores.size();
		            }else{
		            	//Stores.addAll(oldStores);
		            }
		            filterResults.count=Stores.size();

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

