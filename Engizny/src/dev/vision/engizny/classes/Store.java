package dev.vision.engizny.classes;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;

import android.location.Location;
import android.net.Uri;
import dev.vision.engizny.Static;
import dev.vision.engizny.Static.LANGUAGE;
import dev.vision.engizny.parse.ParseStore;
import dev.vision.engizny.parse.ParseSub;

public class Store {
	

	private String name;
	private String image;
	private String logo;

	private ParseStore storeP; 
	public Store() {}
	
	public Store(ParseStore s){
		storeP = s;
		name = s.getName();
		if(s.getImage() != null)
			image = s.getImage().getUrl();
		if(s.getLogo() != null)
			logo = s.getLogo().getUrl();

	}

	public String getName() {
		return name;
	}
	
	public String getImageUrl() {
		return image;
	}
	
	public String getObjectId(){
		return storeP.getObjectId();
	}

	public LatLng getLocation() {
		return new LatLng(storeP.getLatitude(), storeP.getLongitude());
	}

	public List<String> getNumbers() {
		return storeP.getPhone();
	}

	public String getDesription() {
		String temp ="";
		String s = storeP.getDesription();
		if(s!=null && !s.trim().equals("")) temp += s + "\n\n";
				
		if(getNumbers()!=null && getNumbers().size() > 0 && !getNumbers().get(0).equals("")){
			temp += Static.LANG == LANGUAGE.ENGLISH? "Telephone: \n" : "تـليـفون:\n" ;
			for(String n : getNumbers()){
				temp += n+ "\n";
			}
			temp +="\n";
		}
		
		if(storeP.getAddress()!=null && !storeP.getAddress().trim().equals("")){
			temp += Static.LANG == LANGUAGE.ENGLISH? "Address: \n" : "عـنـوان:\n" ;
			temp += storeP.getAddress();
		}
		return temp;
	}

	public void getItems(FindCallback callback) {
		storeP.getItems(callback);
	}

	public String getWebsite() {
		return storeP.getWebsite();
	}

	public String getFacebook() {
		return storeP.getFacebook();
	}
	
	public String getTwitter() {
		return storeP.getTwitter();
	}

	public String getLogoUrl() {
		return logo;
	}
}
