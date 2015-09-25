package dev.vision.shopping.center.adapter;

import java.util.ArrayList;
import java.util.Locale;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.squareup.picasso.Picasso;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.api.Movies;
import dev.vision.shopping.center.api.Movie;
import dev.vision.shopping.center.api.Movies;
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

public class Movies_Tabbed_Adapter extends BaseAdapter implements Filterable {
    ArrayList<String> mSuggestions = null;

    String text[] = Static.text;
    int selecteIndex = 0;
    private Context con;
    Movies Movies;
    SwipeListView lv;
    Movies oldMovies;
	boolean down;


	public Movies_Tabbed_Adapter(Context c, Movies movies, SwipeListView lv){
		con = c;
		Movies = movies;
		this.lv =lv;
		oldMovies = new Movies();
        oldMovies.addAll(Movies);
	}

	@Override
	public int getCount() {
		return Movies.size();
	}

	@Override
	public Object getItem(int arg0) {
		return Movies.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@SuppressLint("NewApi") @Override
	public View getView(final int pos, View convertView, ViewGroup parent) {
		LayoutInflater ll = LayoutInflater.from(con);
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = ll.inflate(R.layout.movies_list_item, parent ,false);
			vh.im = (ImageView) convertView.findViewById(R.id.imageView1);
			vh.nav = (ImageView) convertView.findViewById(R.id.navigate);

			vh.txt = (TextView) convertView.findViewById(R.id.bold_name);
			vh.desc = (TextView) convertView.findViewById(R.id.desc);
			vh.ticP = (TextView) convertView.findViewById(R.id.ticketP);

			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag(); 
		}
		final Movie s = Movies.get(pos);
		Picasso.with(con).load(s.getPoster()).into(vh.im);
		//vh.im.setImageResource(s.get);
		vh.txt.setText(s.getName());
		vh.desc.setText(s.getDescription());
		vh.ticP.setText(s.getTicketPrice());
		
		
		

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
	
		            
		            Movies.clear();
		            for(Movie s : oldMovies){
		            	if(s.getName().toLowerCase(Locale.US).startsWith(((String) charSequence).toLowerCase(Locale.US)))
		            		Movies.add(s);
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
		public TextView ticP;
		public TextView desc;
		ImageView im ;
		ImageView nav ;

		TextView txt ;

	}

}
