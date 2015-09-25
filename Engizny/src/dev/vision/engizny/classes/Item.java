package dev.vision.engizny.classes;

import java.text.DecimalFormat;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;

import android.location.Location;
import dev.vision.engizny.Static;
import dev.vision.engizny.Static.LANGUAGE;
import dev.vision.engizny.parse.ParseItem;
import dev.vision.engizny.parse.ParseStore;
import dev.vision.engizny.parse.ParseSub;

public class Item {
	

	private String name;
	private String image;
	private int price;

	private ParseItem item; 
	public Item() {}
	
	public Item(ParseItem s){
		item = s;
		name = s.getName();
		price = s.getPrice();

		if(s.getImage() != null)
		image = s.getImage().getUrl();
	}

	public String getName() {
		return name;
	}
	
	public String getImageUrl() {
		return image;
	}
	
	public String getObjectId(){
		return item.getObjectId();
	}

	public String getPriceText() {
		DecimalFormat df = new DecimalFormat("#.00"); 

		return df.format((price/100f)) + " L.E";
	}

	public int getPrice() {
		return price;
	}

}
