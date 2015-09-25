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

@ParseClassName("ParseCategory")
public class ParseCategory extends ParseObject {
     
	public static final String ID = "objectId";
	public static final String NAME = "name";
	public static final String SUB = "subCategories";
	
	public String getName() {
        return getString(NAME);
    }
     
    public void setName(String name) {
        put(NAME,  name);
    }
    
    public void getSub(FindCallback callback) {
	    getRelation(SUB).getQuery().findInBackground(callback);
    }
    
    public void addSub(ParseSub sub) {
	    getRelation(SUB).add(sub);
    }
                    
    public static ParseQuery<ParseCategory> getQuery() {
        return ParseQuery.getQuery(ParseCategory.class);
    }

	
}