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

@ParseClassName("ParseSub")
public class ParseSub extends ParseObject {
     
	public static final String ID = "objectId";
	public static final String NAME = "name";
	public static final String STORES = "stores";
	public static final String IMAGE = "image";

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
    
    public void getStores(String district_id, FindCallback callback) {
	    ParseQuery<ParseObject> q = getRelation(STORES).getQuery();
	    ParseDistrict d = new ParseDistrict();
	    d.setObjectId(district_id);
	    q.whereEqualTo(ParseItem.DISTRICT, d)
	    .whereEqualTo(ParseItem.LOCKED, false)
	    .findInBackground(callback);
    }
    
    public void addStore(ParseStore s) {
	    getRelation(STORES).add(s);
    }
                    
    public static ParseQuery<ParseSub> getQuery() {
        return ParseQuery.getQuery(ParseSub.class);
    }

	
}