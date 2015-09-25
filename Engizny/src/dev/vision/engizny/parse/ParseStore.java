package dev.vision.engizny.parse;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;





import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

@ParseClassName("ParseStore")
public class ParseStore extends ParseObject {
     
	public static final String ID = "objectId";
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String WEBSITE = "website";
	public static final String FACEBOOK = "facebook";
	public static final String TWITTER = "twitter";
	public static final String NUMBERITEMS = "numberItems";
	public static final String ITEMS = "items";
	public static final String IMAGE = "image";
	public static final String LOCKED = "locked";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String DESCRIPTION = "description";
	private static final String LOGO = "logo";


	public String getName() {
        return getString(NAME);
    }
     
    public void setName(String name) {
        put(NAME,  name);
    }
    
    public String getAddress() {
        return getString(ADDRESS);
    }
     
    public void setAddress(String addr) {
        put(ADDRESS, addr);
    }
    
    public String getWebsite() {
        return getString(WEBSITE);
    }
     
    public void setWebsite(String web) {
        put(WEBSITE, web);
    }
    
    public String getFacebook() {
        return getString(FACEBOOK);
    }
     
    public void setFacebook(String fb) {
        put(FACEBOOK, fb);
    }
    
    public String getTwitter() {
        return getString(TWITTER);
    }
     
    public void setTwitter(String tw) {
        put(TWITTER, tw);
    }
    
    public Number getNumber() {
        return getNumber(NUMBERITEMS);
    }
     
    public void setNumber(int no) {
        put(NUMBERITEMS, no);
    }
    
    public List<String> getPhone() {
        return getList(PHONE);
    }
     
    public void addPhone(String phone) {
        add(PHONE,  phone);
    }
    
    public ParseFile getImage() {
        return getParseFile(IMAGE);
    }
     
    public void setImage(ParseFile image) {
        put(IMAGE,  image);
    }
    
    public void getItems(FindCallback callback) {
	    getRelation(ITEMS).getQuery().findInBackground(callback);
    }
    
    public void addItem(ParseItem i) {
	    getRelation(ITEMS).add(i);;
    }
    
    public boolean isLocked() {
        return getBoolean(LOCKED);
    }
     
    public void setLocked(boolean l) {
        put(LOCKED, l);
    }
                    
    public static ParseQuery<ParseStore> getQuery() {
        return ParseQuery.getQuery(ParseStore.class);
    }

	public double getLongitude() {
		return Double.parseDouble(getString(LONGITUDE));
	}

	public double getLatitude() {
		return Double.parseDouble(getString(LATITUDE));
	}

	public String getDesription() {
		return getString(DESCRIPTION);
	}

	public ParseFile getLogo() {
		return getParseFile(LOGO);
	}
}