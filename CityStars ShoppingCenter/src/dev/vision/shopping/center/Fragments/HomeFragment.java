package dev.vision.shopping.center.Fragments;

import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.R.drawable;
import dev.vision.shopping.center.R.id;
import dev.vision.shopping.center.R.layout;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeFragment extends Fragment {
	private ImageView Front;
	private ImageView BACK;
	private ImageView show_hide;
	private View v;
	private int current = 2;
	int drawable[] ={R.drawable.main_1, R.drawable.main_2 , R.drawable.main_3, R.drawable.main_4, R.drawable.main_5};
	private TextView notice;

	public HomeFragment() { }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.activity_main, null);
		Front = (ImageView) v.findViewById(R.id.ImageView01);
		BACK = (ImageView) v.findViewById(R.id.imageView1);
		show_hide = (ImageView) v.findViewById(R.id.show_hide_menu);
		notice = (TextView) v.findViewById(R.id.textView1);
		notice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switchFragment(NoticeFragment.NewInstance(14));
			}
		});

		return v;
	}
	
	// the meat of switching the above fragment
		private void switchFragment(Fragment fragment) {
			Activity ac = getActivity();
			if (ac == null)
				return;

			if (ac instanceof CustomAnimation) {
				CustomAnimation ra = (CustomAnimation) ac;
				ra.switchContent(fragment);
			}
		}
	
	@SuppressLint("NewApi") 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		((ActionBarActivity)getActivity()).getSupportActionBar().hide();
		ObjectAnimator anim_out = ObjectAnimator.ofFloat(show_hide, "alpha", 1f, 0.2f);
		anim_out.setDuration(1500);
		anim_out.setRepeatCount(ObjectAnimator.INFINITE);
		anim_out.setRepeatMode(ObjectAnimator.REVERSE);
		anim_out.start();
		show_hide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((CustomAnimation)getActivity()).toggle();
			}
		});
		Front.setImageResource(drawable[0]);
		BACK.setImageResource(drawable[1]);
		BACK.setAlpha(0f);
		
	
		new Handler().postDelayed(new Runnable() {
			

			@Override
			public void run() {
				ImageViewAnimatedChange(getActivity(), Front, BACK, current);

			}
		}, 3000);	

	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	

	
	@SuppressLint("NewApi") public void ImageViewAnimatedChange(Context c, final ImageView v, final ImageView v2, final int id) {

		ObjectAnimator anim_out = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f);
		ObjectAnimator anim_in = ObjectAnimator.ofFloat(v2, "alpha", 0f, 1f);

		
		anim_out.setDuration(1000);
		anim_in.setDuration(1000);
	
		
	    AnimatorSet sa = new AnimatorSet();
	    sa.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {

			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				v.setImageResource(drawable[id]); 
		        current = id+1 < drawable.length ? id+1 : 0;

		    	new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
		            	ImageViewAnimatedChange(getActivity(), v2, v, current);								
					}
				}, 3000);				
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    sa.play(anim_out).with(anim_in);
	    sa.start();
	    
       
    	
	}


}
