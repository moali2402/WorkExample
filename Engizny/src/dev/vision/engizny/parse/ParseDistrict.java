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

@ParseClassName("ParseDistrict")
public class ParseDistrict extends ParseObject {
     
	public static final String ID = "objectId";

    
    public static ParseQuery<ParseDistrict> getQuery() {
        return ParseQuery.getQuery(ParseDistrict.class);
    }


	
}