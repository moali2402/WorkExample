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

@ParseClassName("ParseItem")
public class ParseItem extends ParseObject {
     
	public static final String ID = "objectId";
	public static final String NAME = "name";
	public static final String STORE = "store";
	public static final String IMAGE = "image";
	public static final String PRICE = "price";
	public static final String DISTRICT = "district";
	public static final String LOCKED = "locked";

	public String getName() {
        return getString(NAME);
    }
     
    public void setName(String name) {
        put(NAME,  name);
    }
    
    public ParseFile getImage() {
        return getParseFile(IMAGE);
    }
     
    public void setImage(ParseFile image) {
        put(IMAGE,  image);
    }
    
    public int getPrice() {
        return getNumber(PRICE).intValue();
    }
     
    public void setPrice(Number price) {
        put(PRICE,  price);
    }
    
    public boolean getLocked() {
        return getBoolean(LOCKED);
    }
     
    public void setLocked(boolean locked) {
        put(LOCKED,  locked);
    }

    public ParseStore getStore() {
        return (ParseStore) get(STORE);
    }
     
    public void setPrice(ParseStore store) {
        put(STORE,  store);
    }
                    
    public static ParseQuery<ParseItem> getQuery() {
        return ParseQuery.getQuery(ParseItem.class);
    }
}