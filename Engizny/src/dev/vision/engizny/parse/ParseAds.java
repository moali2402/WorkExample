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

@ParseClassName("Ad")
public class ParseAds extends ParseObject {
     
	public static final String ID = "objectId";
	public static final String STORE = "for";
	public static final String AD = "ad";
	public static final String PRICE = "price";
	public static final String TYPE = "type";

	public enum TYPES{
    	MAIN,SUB;
    }
    
    public ParseFile getAd() {
        return getParseFile(AD);
    }
     
    public void setImage(ParseFile ad) {
        put(AD,  ad);
    }
    
    public String getPrice() {
        return getString(PRICE);
    }
     
    public void setPrice(String price) {
        put(PRICE,  price);
    }

    public String getType() {
        return getString(TYPE);
    }
     
    public void setType(String t) {
        put(TYPE,  t);
    }
    
    public ParseStore getStore() {
        return (ParseStore) get(STORE);
    }
     
    public void setPrice(ParseStore store) {
        put(STORE,  store);
    }
                    
    public static ParseQuery<ParseAds> getQuery() {
        return ParseQuery.getQuery(ParseAds.class);
    }


	
}