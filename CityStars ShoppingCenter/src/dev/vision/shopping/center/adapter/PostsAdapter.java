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

public class PostsAdapter extends BaseAdapter {
	public class ViewHolder {
		TextView name;
		TextView story;
		TextView message;
		TextView button;
		ImageView pp;


	}

	

	
	ArrayList<SocialInterface> posts;
	Context ctx;
	public PostsAdapter(Context c, ArrayList<SocialInterface> posts){
		this.posts = posts;
		this.ctx = c;
	}

	@Override
	public int getCount() {
		return posts.size();
	}

	@Override
	public SocialInterface getItem(int position) {
		return posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;

		if(convertView == null || convertView.getTag() == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(R.layout.f_item_card, null);
			vh.name = (TextView) convertView.findViewById(R.id.username);
			vh.story = (TextView) convertView.findViewById(R.id.story);
			vh.message = (TextView) convertView.findViewById(R.id.message);
			vh.button = (TextView) convertView.findViewById(R.id.button);
			vh.pp = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		SocialInterface post = getItem(position);
		
	    Picasso.with(ctx).load(post.getProfilePic()).into(vh.pp);
	    vh.button.setTag(post);
	    vh.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = getOpenFacebookIntent((FacebookPost) v.getTag());
				ctx.startActivity(i);
			}
		});
	    vh.name.setText("Citystars Heliopolis");
	    vh.story.setText(post.getStory());
		Linkfiy(post.getMessage(), vh.message);
		return convertView;
	}

	public Intent getOpenFacebookIntent(FacebookPost post) {

		   try {
		    ctx.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse(post.getInLink()));
		   } catch (Exception e) {
		    return new Intent(Intent.ACTION_VIEW, Uri.parse(post.getRedirectLink()));
		   }
		   
	}
	
	private void Linkfiy(String a, TextView textView) {

        Pattern urlPattern = Patterns.WEB_URL;
        Pattern mentionPattern = Pattern.compile("(@[A-Za-z0-9_-]+)");
        Pattern hashtagPattern = Pattern.compile("#(\\w+|\\W+)");

        Matcher o = hashtagPattern.matcher(a);
        Matcher mention = mentionPattern.matcher(a);
        Matcher weblink = urlPattern.matcher(a);


        SpannableString spannableString = new SpannableString(a);
        //#hashtags

        while (o.find()) {

            spannableString.setSpan(new NonUnderlinedClickableSpan(ctx,o.group(),
                    0), o.start(), o.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        // --- @mention
        while (mention.find()) {
            spannableString.setSpan(
                    new NonUnderlinedClickableSpan(ctx,mention.group(), 1), mention.start(), mention.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        //@weblink
        while (weblink.find()) {
            spannableString.setSpan(
                    new NonUnderlinedClickableSpan(ctx,weblink.group(), 2), weblink.start(), weblink.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
	
	

}

