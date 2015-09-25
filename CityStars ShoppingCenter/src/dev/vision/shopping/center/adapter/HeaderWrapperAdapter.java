package dev.vision.shopping.center.adapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.picasso.Picasso;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.interfaces.SocialInterface;
import dev.vision.shopping.center.social.FacebookPost;
import dev.vision.shopping.center.views.NonUnderlinedClickableSpan;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HeaderWrapperAdapter extends BaseAdapter {
	
	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	Context ctx;
	BaseAdapter adapter;
	public HeaderWrapperAdapter(Context cx,BaseAdapter adapter){
		this.adapter = adapter;
		ctx = cx;
	}

	@Override
	public int getCount() {
		return adapter.getCount() + 1;
	}

	@Override
	public Object getItem(int position) {
		return adapter.getItem(position-1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public int getItemViewType(final int position) {
		 if (isPositionHeader(position))
	            return TYPE_HEADER;
		return TYPE_ITEM;
	}
	
	

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);

		if(type == TYPE_HEADER){
			convertView = LayoutInflater.from(ctx).inflate(R.layout.header, null);
		}else{
			convertView = adapter.getView(position-1, convertView, parent);
		}
		
		return convertView;
	}
}

