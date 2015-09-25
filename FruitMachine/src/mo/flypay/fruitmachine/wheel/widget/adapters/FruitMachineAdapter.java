package mo.flypay.fruitmachine.wheel.widget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import mo.flypay.fruitmachine.R;
import mo.flypay.fruitmachine.datasource.Source;
import mo.flypay.fruitmachine.model.Fruit;

/**
 * FruitMachine adapter
 */
public class FruitMachineAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Source source;

	public FruitMachineAdapter(Context context, Source c) {
		this.inflater = LayoutInflater.from(context);
		this.source = c;
	}

	@Override
	public int getCount() {
		return source.getFruitList().size();
	}

	@Override
	public Fruit getItem(int position) {
		return source.getFruit(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if (convertView == null) {
			viewHolder = new ViewHolder(); 
			convertView =  inflater.inflate(R.layout.slot_item, parent, false);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Fruit f = getItem(position);
		viewHolder.image.setImageBitmap(f.getImage());
		
		return convertView;
	}
	
	
	class ViewHolder{
		ImageView image;
	}


		
}