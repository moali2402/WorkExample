package mo.flypay.fruitmachine.objects;

import java.util.LinkedList;
import java.util.List;

import android.view.View;
import android.widget.LinearLayout;
import mo.flypay.fruitmachine.views.SlotView;


public class WheelRecycle {
	
	// Cached items
	private List<View> items;
	
	// Wheel view
	private SlotView wheel;
	
	/**
	 * Constructor
	 * @param wheel the wheel view
	 */
	public WheelRecycle(SlotView wheel) {
		this.wheel = wheel;
	}

	/**
	 * Recycles items from specified layout.
	 * There are saved only items not included to specified range.
	 * All the cached items are removed from original layout.
	 * 
	 * @param layout the layout containing items to be cached
	 * @param firstItem the number of first item in layout
	 * @param range the range of current wheel items 
	 * @return the new value of first item number
	 */
	public int recycleItems(LinearLayout layout, int firstItem, Range range) {
		int index = firstItem;
		for (int i = 0; i < layout.getChildCount();) {
			if (!range.contains(index)) {
				recycleView(layout.getChildAt(i), index);
				layout.removeViewAt(i);
				if (i == 0) { // first item
					firstItem++;
				}
			} else {
				i++; // go to next item
			}
			index++;
		}
		return firstItem;
	}
	
	/**
	 * Gets item view
	 * @return the cached view
	 */
	public View getItem() {
		return getCachedView(items);
	}

	
	/**
	 * Clears all views 
	 */
	public void clearAll() {
		if (items != null) {
			items.clear();
		}
	}

	/**
	 * Adds view to specified cache. Creates a cache list if it is null.
	 * @param view the view to be cached
	 * @param cache the cache list
	 * @return the cache list
	 */
	private List<View> addView(View view, List<View> cache) {
		if (cache == null) {
			cache = new LinkedList<View>();
		}
		
		cache.add(view);
		return cache;
	}

	/**
	 * Adds view to cache. Determines view type (item view or empty one) by index.
	 * @param view the view to be cached
	 * @param index the index of view
	 */
	private void recycleView(View view, int index) {
		int count = wheel.getViewAdapter().getCount();
		if (index >= 0 || index < count) {
			while (index < 0) {
				index = count + index;
			}
			index %= count;
			items = addView(view, items);
		}
	}
	
	/**
	 * Gets view from specified cache.
	 * @param cache the cache
	 * @return the first view from cache.
	 */
	private View getCachedView(List<View> cache) {
		if (cache != null && cache.size() > 0) {
			View view = cache.get(0);
			cache.remove(0);
			return view;
		}
		return null;
	}

}
