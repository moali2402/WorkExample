package dev.vision.shopping.center.Fragments;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnListViewOnScrollListener;
import com.fortysevendeg.swipelistview.SwipeListView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.adapter.HeaderWrapperAdapter;
import dev.vision.shopping.center.adapter.InstaAdapter;
import dev.vision.shopping.center.adapter.PostsAdapter;
import dev.vision.shopping.center.interfaces.SocialInterface;
import dev.vision.shopping.center.social.FacebookPost;
import dev.vision.shopping.center.social.InstagramPost;
import dev.vision.shopping.center.social.TwitterPost;


public class SocialFragment extends Fragment {
	TabHost tabs;
	private View v;
	int posi;

	private ListView list;
	
	String city_f_feed = "https://graph.facebook.com/"+FacebookPost.PAGE_ID+"/feed?fields=id,message,story&"
			+ "access_token="
			+ "375261972679023%7Ca9f4eb1992e0aa7f1b1edeb66ee44a19";
	
	String city_t_feed = "https://api.twitter.com/1.1/statuses/user_timeline.json?user_id="+ TwitterPost.PAGE_ID;
	
    ArrayList<SocialInterface> posts = new ArrayList<SocialInterface>();
	private LinearLayout header;

	public SocialFragment() {
	}
	
	public static SocialFragment NewInstance(int pos) {
		SocialFragment d =new SocialFragment();
		Bundle bun = new Bundle();
		bun.putInt("pos", pos);
		d.setArguments(bun);
		return d;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.social_tab_list, container,false);
        
             
		//v= inflater.inflate(R.layout.social_tab_list, null);
		list = (ListView) v.findViewById(android.R.id.list);
		header = (LinearLayout) v.findViewById(R.id.sticky);

		ImageView facebook = (ImageView) v.findViewById(R.id.f);
		ImageView twitter = (ImageView) v.findViewById(R.id.t);
		ImageView insta = (ImageView) v.findViewById(R.id.i);
		OnClickListener oc = new OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.f:
					new FacebookFeed().execute();

					break;
				case R.id.t:
					new TwitterFeed().execute();

					break;
				case R.id.i:
					new InstaFeed().execute();

					break;
				default:
					break;
				}
				if(Build.VERSION.SDK_INT > 15)
					list.setBackground(v.getBackground());
				else list.setBackgroundDrawable(v.getBackground());

			}
		};
		facebook.setOnClickListener(oc);
		twitter.setOnClickListener(oc);
		insta.setOnClickListener(oc);

		return v;
	}
	
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.header_height);
        
        QuickReturnListViewOnScrollListener scrollListener = new QuickReturnListViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                        .header(header)
                        .minHeaderTranslation(-headerHeight+15)
                        .isSnappable(true)
                        .build();

        list.setOnScrollListener(scrollListener);
    }
	
	 
	

	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		posi = getArguments().getInt("pos");
		
		int icon = Static.icons[posi];
		String txt  = Static.text[posi];
		((ActionBarActivity)getActivity()).getSupportActionBar().show();
		((ActionBarActivity)getActivity()).getSupportActionBar().setIcon(icon);
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(txt);
		


		new FacebookFeed().execute();

		

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	class FacebookFeed extends AsyncTask<Void, Void, ArrayList<SocialInterface>>{

		@Override
		protected ArrayList<SocialInterface> doInBackground(Void... params) {
            ArrayList<SocialInterface> posts = new ArrayList<SocialInterface>();

	        try {
	            DefaultHttpClient d = new DefaultHttpClient();
	            HttpGet p = new HttpGet(city_f_feed);

	            HttpResponse res = d.execute(p);
	            
	            InputStream in = res.getEntity().getContent();

	            InputStreamReader isw = new InputStreamReader(in);
	            
	            BufferedReader bf = new BufferedReader(isw);
	            
	            StringBuilder builder = new StringBuilder();
	            String l = bf.readLine();
	            while(l!=null){
	            	builder.append(l);
	            	l = bf.readLine();
	            }

                JSONObject ob = new JSONObject(builder.toString());
                JSONArray ja = ob.getJSONArray("data");
                for(int i = 0; i < ja.length(); i++){
                	JSONObject indexed = ja.getJSONObject(i);
                	FacebookPost post = new FacebookPost(indexed);
                	posts.add(post);
                }
                
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
			return posts;
		}

		@Override
		protected void onPostExecute(ArrayList<SocialInterface> result) {
			super.onPostExecute(result);
			if(result != null){
				posts.clear();
				posts.addAll(result);
				//((PostsAdapter)list.getAdapter()).notifyDataSetChanged();
				list.setAdapter(new HeaderWrapperAdapter(getActivity(),new PostsAdapter(getActivity(), posts)));
				
				
			}
		}
	}
	
	class TwitterFeed extends AsyncTask<Void, Void, ArrayList<SocialInterface>>{

		@Override
		protected ArrayList<SocialInterface> doInBackground(Void... params) {
            ArrayList<SocialInterface> posts = new ArrayList<SocialInterface>();
            /*
            HttpClient httpclient = new DefaultHttpClient();
            String consumer_key="consumer_key";
            String consumer_secret="consumer_secret";
            consumer_key=URLEncoder.encode(consumer_key, "UTF-8");
            consumer_secret=URLEncoder.encode(consumer_secret,"UTF-8");
            String authorization_header_string=consumer_key+":"+consumer_secret; 
            byte[] encoded = Base64.encodeBase64(authorization_header_string.getBytes());
            String encodedString = new String(encoded); //converting byte to string
            HttpPost httppost = new HttpPost("https://api.twitter.com/oauth2/token");
            httppost.setHeader("Authorization",encodedString);
            List parameters = new ArrayList();
            parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            httppost.setEntity(new UrlEncodedFormEntity(parameters));
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            ResponseHandler responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httppost, responseHandler);
            */
            
            
	        try {
	            DefaultHttpClient d = new DefaultHttpClient();
	            HttpGet p = new HttpGet(city_t_feed);
	            
	        	p.addHeader("Authorization" , 
	        			"OAuth oauth_consumer_key=\"kmcOvjEugwV0oFpeEeAph6Kqp\","
	        			+ "oauth_nonce=\"2a71564f135a9c73a3e493c1339ff737\","
	        			+ "oauth_signature=\"jRYfx%2FIEo3DP9S69VjmGZeoJizQ%3D\","
	        			+ "oauth_signature_method=\"HMAC-SHA1\","
	        			+ "oauth_timestamp=\"1426168193\","
	        			+ "oauth_token=\"2441749903-0Cd8dfpSvMy4kL2NO682x8WztJPAM3nkMqMTIXt\","
	        			+ "oauth_version=\"1.0\""	        			
	        			);


	            HttpResponse res = d.execute(p);
	            
	            InputStream in = res.getEntity().getContent();

	            InputStreamReader isw = new InputStreamReader(in);
	            
	            BufferedReader bf = new BufferedReader(isw);
	            
	            StringBuilder builder = new StringBuilder();
	            String l = bf.readLine();
	            while(l!=null){
	            	builder.append(l);
	            	l = bf.readLine();
	            }

	            System.out.println(builder.toString());
	            JSONArray ja = new JSONArray(builder.toString());
                for(int i = 0; i < ja.length(); i++){
                	JSONObject indexed = ja.getJSONObject(i);
                	TwitterPost post = new TwitterPost(indexed);
                	posts.add(post);
                }
                
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
			return posts;
		}
		
		@Override
		protected void onPostExecute(ArrayList<SocialInterface> result) {
			super.onPostExecute(result);
			if(result != null){
				posts.clear();
				posts.addAll(result);
				//((PostsAdapter)list.getAdapter()).notifyDataSetChanged();
				list.setAdapter(new PostsAdapter(getActivity(), posts));

				
			}
		}
		
	}
	
	class InstaFeed extends AsyncTask<Void, Void, ArrayList<SocialInterface>>{

		@Override
		protected ArrayList<SocialInterface> doInBackground(Void... params) {
            ArrayList<SocialInterface> posts = new ArrayList<SocialInterface>();

	        try {
	            DefaultHttpClient d = new DefaultHttpClient();
	            HttpGet p = new HttpGet(InstagramPost.API);

	            HttpResponse res = d.execute(p);
	            
	            InputStream in = res.getEntity().getContent();

	            InputStreamReader isw = new InputStreamReader(in);
	            
	            BufferedReader bf = new BufferedReader(isw);
	            
	            StringBuilder builder = new StringBuilder();
	            String l = bf.readLine();
	            while(l!=null){
	            	builder.append(l);
	            	l = bf.readLine();
	            }

                JSONObject ob = new JSONObject(builder.toString());
                JSONArray ja = ob.getJSONArray("data");
                for(int i = 0; i < ja.length(); i++){
                	JSONObject indexed = ja.getJSONObject(i);
                	InstagramPost post = new InstagramPost(indexed);
                	posts.add(post);
                }
                
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
			return posts;
		}

		@Override
		protected void onPostExecute(ArrayList<SocialInterface> result) {
			super.onPostExecute(result);
			if(result != null){
				posts.clear();
				posts.addAll(result);
				list.setAdapter(new HeaderWrapperAdapter(getActivity(),new InstaAdapter(getActivity(), posts)));

				//((InstaAdapter)list.getAdapter()).notifyDataSetChanged();
				
				
			}
		}
		
	}

}
