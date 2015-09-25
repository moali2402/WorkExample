package dev.vision.shopping.center;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Spinner;
import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Point.TYPE;
import dev.vision.shopping.center.api.BaseLocation;
import dev.vision.shopping.center.api.Conveyor;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.api.Stores;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.CityStarsNFC;

public class Static {
	static Context ctx = MyApplication.getAppContext();

	public static int icons[] = { 
			R.drawable.menu_home, 
			R.drawable.menu_stores, 
			R.drawable.menu_dining, 
    		R.drawable.menu_foodcentral, 
    		R.drawable.gourmet, 
    		R.drawable.menu_movies, 
    		R.drawable.menu_promo,
    		R.drawable.menu_calendar,
    		R.drawable.menu_whatsnew,
    		R.drawable.menu_map,
    		//R.drawable.menu_hours,
    		R.drawable.menu_parking,
    		R.drawable.menu_giftcard,
    		R.drawable.magazine_cr, 
    		R.drawable.menu_social,
    		R.drawable.menu_notice,
    		//R.drawable.menu_directions,
    		R.drawable.menu_info, 
    		R.drawable.menu_settings
    };
	
	public static int r_icons[] = { 
		R.drawable.map_legend_list_customers, 
		R.drawable.map_legend_list_elevator, 
		R.drawable.map_legend_list_escalator, 
		R.drawable.map_legend_list_washrooms, 
		R.drawable.atm
	};
	
	public static String r_text[] = { 
		"Customer Service",
		"Elevator",
		"Escalators",
		"W.C.",
		"ATM"
    };
    
	public static String text[] = { 
		"WELCOME",
		"STORES",
		"DINNING",
		"FOOD COURT", 
		"GOURMET",
		"MOVIES",
		"OFFERS",
		"EVENTS",
		"WHAT'S NEW",
		"MAP",
		//"HOURS",
		"PARKING",
		"GIFT CARDS",
		"MAGAZINE",
		"SOCIAL",
		"NOTICE",
		//"VISIT US",
		"INFO",
		"SETTINGS"
    };
	
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	
	public static ArrayList<Conveyor> Conveyors = new ArrayList<Conveyor>();

	public static Stores Stores = new Stores();
	public static Stores dinning = new Stores();
	public static Stores favdinning = new Stores();
    public static HashMap<String,Stores> Catagorised = new HashMap<String,Stores>();
	public static Stores favoStore = new Stores();
	public static ArrayList<Store> favoShops = new ArrayList<Store>();

	public static ArrayList<BaseLocation> WCs = new ArrayList<BaseLocation>();
	public static ArrayList<BaseLocation> ATMs = new ArrayList<BaseLocation>();
	public static ArrayList<BaseLocation> CustomerS = new ArrayList<BaseLocation>();

	/*
	static{
		String storesstring = loadJSONFromAsset("stores.json");
		String dinningstring = loadJSONFromAsset("dinning.json");

		JSONArray jo;
		try {
			jo = new JSONArray(storesstring);
		    for (int i = 0 ;  i <jo.length(); i++) {
				Store s = new Store();
				JSONObject json;
				json = new JSONObject(jo.get(i).toString());
				String name = json.optString("name");
				int level = json.optInt("level");
				String number = json.optString("number");
				String logo = json.optString("logo");
				String category = json.optString("category","Other");
				boolean favorite = json.optBoolean("favorite");
				int x = json.optInt("x");
				int y = json.optInt("y");

				
				category = category.isEmpty()? "Other" : category;
				
				s.setName(name);
				s.setLevel(level);
				s.setNumber(number);
				s.setFavorite(favorite);
				//s.setLogo(logo);
				s.setCategory(category);
				s.setLocation(new Point(x, y));
				Stores.add(s);
				if(favorite)
					favoStore.add(s);
				if(Catagorised.containsKey(category.toUpperCase())){
					Catagorised.get(category.toUpperCase()).add(s);
				}else{
					Stores st = new Stores();
					st.add(s);
					Catagorised.put(category.toUpperCase(), st);
				}
				
			}			
		    
		    jo = new JSONArray(dinningstring);
		    for (int i = 0 ;  i <jo.length(); i++) {
				Store s = new Store();
				JSONObject json;
				json = new JSONObject(jo.get(i).toString());
				String name = json.optString("name");
				int level = json.optInt("level");
				String number = json.optString("number");
				String logo = json.optString("logo");
				boolean favorite = json.optBoolean("favorite");
				int x = json.optInt("x");
				int y = json.optInt("y");

				
				s.setName(name);
				s.setLevel(level);
				s.setNumber(number);
				s.setFavorite(favorite);
				//s.setLogo(logo);
				s.setLocation(new Point(x, y));

				dinning.add(s);
				if(favorite)
					favdinning.add(s);
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
*/	
	static{
		String citystarsdata = loadJSONFromAsset("level1.json");

		JSONObject js;
		try {
			js = new JSONObject(citystarsdata);
			JSONArray jo = js.optJSONArray("stores");

		    for (int i = 0 ;  i <jo.length(); i++) {
				Store s = new Store();
				JSONObject json;
				json = new JSONObject(jo.get(i).toString());
				String name = json.optString("name");
				int level = json.optInt("level");
				String number = json.optString("number");
				String logo = json.optString("logo");
				String category = json.optString("category","Other");
				boolean favorite = json.optBoolean("favorite");
				boolean featured = json.optBoolean("featured");
				int x = json.optInt("x");
				int y = json.optInt("y");

				
				category = category.isEmpty()? "Other" : category;
				
				s.setName(name);
				s.setLevel(level);
				s.setNumber(number);
				s.setFavorite(favorite);
				s.SetFeatured(featured);
				s.setStoreImage(logo);
				s.setCategory(category);
				s.setLocation(new Point(x, y, TYPE.STORE));
				if(featured)
					Stores.add(0,s);
				else
					Stores.add(s);
				
				if(favorite){
					if(featured)
						favoStore.add(0,s);
					else
						favoStore.add(s);
				}
				
				

				if(Catagorised.containsKey(category.toUpperCase())){
					Catagorised.get(category.toUpperCase()).add(s);
				}else{
					Stores st = new Stores();
					st.add(s);
					Catagorised.put(category.toUpperCase(), st);
				}
				
			}			
		    
		    
		    jo = js.optJSONArray("dining");
		    for (int i = 0 ;  i <jo.length(); i++) {
				Store s = new Store();
				JSONObject json;
				json = new JSONObject(jo.get(i).toString());
				String name = json.optString("name");
				int level = json.optInt("level");
				String number = json.optString("number");
				String logo = json.optString("logo");
				boolean favorite = json.optBoolean("favorite");
				boolean featured = json.optBoolean("featured");

				int x = json.optInt("x");
				int y = json.optInt("y");

				
				s.setName(name);
				s.setLevel(level);
				s.setNumber(number);
				s.setFavorite(favorite);
				s.SetFeatured(featured);
				s.setStoreImage(logo);
				s.setLocation(new Point(x, y, TYPE.STORE));

				if(featured)
					dinning.add(0,s);
				else
					dinning.add(s);
				
				if(favorite){
					if(featured)
						favdinning.add(0,s);
					else
						favdinning.add(s);
				}
				
			}
		    
		    jo = js.optJSONArray("customers");
		    for (int i = 0 ;  i <jo.length(); i++) {
		    	BaseLocation s = new BaseLocation();
				JSONObject json = new JSONObject(jo.get(i).toString());
				int level = json.optInt("level");
				int x = json.optInt("x");
				int y = json.optInt("y");
				s.setLevel(level);
				s.setLocation(new Point(x, y, TYPE.CUSTOMER_SERVICE));

				CustomerS.add(s);	
				
			}
		    
		    jo = js.optJSONArray("conveyor");
		    for (int i = 0 ;  i <jo.length(); i++) {
		    	Conveyor s = new Conveyor();
				JSONObject json = new JSONObject(jo.get(i).toString());
				int id = json.optInt("id");
				String type = json.optString("type");
				int level = json.optInt("level");
				int x = json.optInt("x");
				int y = json.optInt("y");
				s.setID(id);
				s.setLevel(level);
				s.setLocation(new Point(x, y, TYPE.valueOf(type)));

				Conveyors.add(s);				
			}
		    
		    jo = js.optJSONArray("wc");
		    for (int i = 0 ;  i <jo.length(); i++) {
		    	BaseLocation s = new BaseLocation();
				JSONObject json = new JSONObject(jo.get(i).toString());
				int level = json.optInt("level");
				int x = json.optInt("x");
				int y = json.optInt("y");
				s.setLevel(level);
				s.setLocation(new Point(x, y, TYPE.WC));

				WCs.add(s);	
				
			}
		    
		    jo = js.optJSONArray("atm");
		    for (int i = 0 ;  i <jo.length(); i++) {
		    	BaseLocation s = new BaseLocation();
				JSONObject json = new JSONObject(jo.get(i).toString());
				int level = json.optInt("level");
				int x = json.optInt("x");
				int y = json.optInt("y");
				s.setLevel(level);
				s.setLocation(new Point(x, y, TYPE.ATM));

				ATMs.add(s);	
			}
		    
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setContext(Context con){
		ctx =  con;
	}

	public static String loadJSONFromAsset(String file) {
	     String json = null;
	     try {

	         InputStream is = ctx.getAssets().open(file);

	         int size = is.available();

	         byte[] buffer = new byte[size];

	         is.read(buffer);

	         is.close();

	         json = new String(buffer, "UTF-8");


	     } catch (IOException ex) {
	         ex.printStackTrace();
	         return null;
	     }
	     return json;

	 }

	
	public static ArrayList<String> LoadStrings(String fileName){
		Scanner s;
		ArrayList<String> list = new ArrayList<String>();

		try {
			InputStream is = ctx.getAssets().open(fileName);
			s = new Scanner(is);
		
			while (s.hasNextLine()){
				 String ss = s.nextLine();
				 
			     list.add(ss);

			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	 }
	
	@SuppressLint("CommitPrefEdits") 
	public static void saveLocation(CityStarsNFC cf){
		SharedPreferences save = ctx.getSharedPreferences("dev.vision.shoppingcentre", Context.MODE_PRIVATE);
		Editor ed = save.edit();
		saveBundle(ed, "", cf.getBundle());
	}
	
	private static void saveBundle(Editor ed, String header, Bundle location) {
	    Set<String> keySet = location.keySet();
	    Iterator<String> it = keySet.iterator();

	    while (it.hasNext()){
	        String key = it.next();
	        Object o = location.get(key);
	        if (o == null){
	            ed.remove(header + key);
	        } else if (o instanceof Integer){
	            ed.putInt(header + key, (Integer) o);
	        } else if (o instanceof Long){
	            ed.putLong(header + key, (Long) o);
	        } else if (o instanceof Boolean){
	            ed.putBoolean(header + key, (Boolean) o);
	        } else if (o instanceof CharSequence){
	            ed.putString(header + key, ((CharSequence) o).toString());
	        } else if (o instanceof Bundle){
	            saveBundle(ed,header + key, ((Bundle) o));
	        }
	    }

	    ed.commit();
	}
	
	public static CityStarsNFC getLocation(){
		SharedPreferences save = ctx.getSharedPreferences("dev.vision.shoppingcentre", Context.MODE_PRIVATE);
		CityStarsNFC cf = null;
		String saved = save.getString("NFC", "");
		if(CityStarsNFC.isStarsNFC(saved))
			cf = new CityStarsNFC(saved);
		return cf;
	}
}
