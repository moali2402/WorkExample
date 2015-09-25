package dev.vision.shopping.center.Fragments;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import dev.vision.shopping.center.CustomAnimation;
import dev.vision.shopping.center.NFC;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.R.layout;
import dev.vision.shopping.center.adapter.Menu_Adapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuFragment extends ListFragment {
	Menu_Adapter ma;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.back_menu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 ma = new Menu_Adapter(getActivity());
				
		setListAdapter(ma);
        NFC.resolve(getActivity().getIntent());
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int pos, long id) {
		 ma.setSelecteIndex(pos , v);
		 ((Menu_Adapter)lv.getAdapter()).notifyDataSetChanged();
			//((CustomAnimation)getActivity()).sm.setSlidingEnabled(true);
		((CustomAnimation)getActivity()).sm.setMode(SlidingMenu.LEFT);
		((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(null);

		 Fragment newContent = null;
		 if(pos==0){
		    newContent = new HomeFragment();
		 }else if(pos==1){
			newContent = StoresFragment.NewInstance(pos);
		 }else if(pos==2){
		    newContent = DinningFragment.NewInstance(pos);
		 }else if(pos==3){
			newContent = FoodCourtFragment.NewInstance(pos);
		 }else if(pos==4){
			newContent = GourmetFragment.NewInstance(pos);
		 }else if(pos==5){
			newContent = CinemaFragment.NewInstance(pos);
		 }else if(pos==6){
			newContent = OffersFragment.NewInstance(pos);
		 }else if(pos==7){
			newContent = EventsFragment.NewInstance(pos);
		 }else if(pos==8){
			newContent = WhatsFragment.NewInstance(pos);
		 }else if(pos==9){
			//((CustomAnimation)getActivity()).sm.setSlidingEnabled(false);
			((CustomAnimation)getActivity()).sm.setMode(SlidingMenu.LEFT_RIGHT);
			newContent = MapFragment.NewInstance(pos);
			((CustomAnimation)getActivity()).sm.showSecondaryMenu();
		 }else if(pos==10){
			newContent = ParkingFragment.NewInstance(pos);
		 }else if(pos==11){
			newContent = GiftCardFragment.NewInstance(pos);
		 }else if(pos==12){
			//((CustomAnimation)getActivity()).sm.setSlidingEnabled(false);
			newContent = MagazineFragment.NewInstance(pos);
		 }else if(pos==13){
			newContent = SocialFragment.NewInstance(pos);
		 }else if(pos==14){
			newContent = NoticeFragment.NewInstance(pos);
			//newContent = (Fragment) GoogleFragment.NewInstance(null);
		 }else if(pos==15){
			newContent = InfoFragment.NewInstance(pos);
		 }else{
			newContent = SettingsFragment.NewInstance(pos);
		 }
		 
		 if (newContent != null)
			switchFragment(newContent);
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
	
}
