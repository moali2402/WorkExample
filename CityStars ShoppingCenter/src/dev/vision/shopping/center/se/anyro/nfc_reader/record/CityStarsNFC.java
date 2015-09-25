package dev.vision.shopping.center.se.anyro.nfc_reader.record;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Point.TYPE;

public class CityStarsNFC {

	private String level;
	private String number;
	private Point location;
	private String jsonText;

	public CityStarsNFC(String mText) {
		this.jsonText = mText;
		try {
			JSONObject j = new JSONObject(mText);

			level =  j.optString("level", "");
			number = j.optString("number", "");
			location = new Point(j.optInt("x"), j.optInt("y"), TYPE.CAR);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public static boolean isStarsNFC(String cont) {
		return cont != null && cont.contains("CITYSTARS.DEV-VISION");
	}

	public Bundle getBundle() {

		Bundle b = new Bundle();
		b.putString("NFC", jsonText);
		return b;
	}

}
