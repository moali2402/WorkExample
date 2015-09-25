package dev.vision.shopping.center.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

	public class FragmentAdapter extends FragmentStatePagerAdapter {
		ArrayList<Fragment> fragments;
        public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> b) {
            super(fm);
            this.fragments = b;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }