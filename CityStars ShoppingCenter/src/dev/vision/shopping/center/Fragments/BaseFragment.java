package dev.vision.shopping.center.Fragments;

import dev.vision.shopping.center.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class BaseFragment extends Fragment{
	
	TabHost tabs;

	public TabSpec createTab(final String tag, final String title, int Content){
		/*
		tabs.newTabSpec(tag); 
        spec.setContent(R.id.tab3);
        spec.setIndicator(title);
        */
        
        final View tab = LayoutInflater.from(tabs.getContext()).
            inflate(R.layout.tab, null);
        ((TextView)tab.findViewById(R.id.tab_text)).setText(title);

        return tabs.newTabSpec(tag).setIndicator(tab).setContent(Content);
    }

}
