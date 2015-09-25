package dev.vision.shopping.center.map;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.http.util.LangUtils;

import dev.vision.shopping.center.R;
import dev.vision.shopping.center.map.Polygon.Builder;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Region;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProxAlertActivity extends Service {
	
	private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATE = 60 * 1000; // in Milliseconds
	
	private static final long POINT_RADIUS = 205; // in Meters
	private static final long PROX_ALERT_EXPIRATION = -1; 
	private static final String POINT_LATITUDE_KEY = "POINT_LATITUDE_KEY";
	private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY";
	private static final String PROX_ALERT_INTENT = "dev.vision.mall.ProximityAlert";
	
	
	private LocationManager locationManager;
	private PendingIntent proximityIntent;
	private boolean alreadyInside;
	private Polygon polygon;
	
    @Override
    public void onCreate() {
    	
        super.onCreate();
        
        Intent intent = new Intent(PROX_ALERT_INTENT);
        proximityIntent = PendingIntent.getBroadcast(ProxAlertActivity.this, 0, intent, 0);
        
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        //CityStars
        LatLng[] thePath = {new LatLng(30.071805138184363, 31.348628997802734),new LatLng(30.071285192811743, 31.343843936920166),new LatLng(30.07511044130564, 31.342921257019043),new LatLng(30.07473906443857, 31.348371505737305)};
        
        
        //LatLng[] thePath = {new LatLng(30.107489142364862, 31.334574222564697),new LatLng(30.10678375615452, 31.3349711894989),new LatLng(30.107071480085246, 31.335636377334595),new LatLng(30.107786145578526, 31.335196495056152)};
		Builder polygonBuilder = Polygon.Builder();
		
		for(LatLng l: thePath){
			polygonBuilder.addVertex(new Point(l.Latitude, l.Longitude));
		}
		polygon =polygonBuilder.build();
        
		

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);  
        registerReceiver(new ProximityIntentReceiver(), filter);
		
        locationManager.requestLocationUpdates(
        		LocationManager.NETWORK_PROVIDER, 
        		MINIMUM_TIME_BETWEEN_UPDATE, 
        		MINIMUM_DISTANCECHANGE_FOR_UPDATE,
        		new PolygonLocationListener()
        );

        // saveProximityAlertPoint(30.073249, 31.345941);
    }
    
    private void saveProximityAlertPoint() {
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	if (location==null) {
    		Toast.makeText(this, "No last known location. Aborting...", Toast.LENGTH_LONG).show();
    		return;
    	}
    	saveCoordinatesInPreferences((float)location.getLatitude(), (float)location.getLongitude());
    	addProximityAlert(location.getLatitude(), location.getLongitude());
	}
    
    private void saveProximityAlertPoint(double latit, double longit) {
    	
    	saveCoordinatesInPreferences((float)latit, (float)longit);
    	addProximityAlert(latit, longit);
	}

	private void addProximityAlert(double latitude, double longitude) {
		
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        
        locationManager.addProximityAlert(
    		latitude, // the latitude of the central point of the alert region
    		longitude, // the longitude of the central point of the alert region
    		POINT_RADIUS, // the radius of the central point of the alert region, in meters
    		PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration 
    		proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
       );
        
       IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);  
       registerReceiver(new ProximityIntentReceiver(), filter);
       
	}

    private void saveCoordinatesInPreferences(float latitude, float longitude) {
    	SharedPreferences prefs = this.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
    	SharedPreferences.Editor prefsEditor = prefs.edit();
    	prefsEditor.putFloat(POINT_LATITUDE_KEY, latitude);
    	prefsEditor.putFloat(POINT_LONGITUDE_KEY, longitude);
    	prefsEditor.commit();
    }
    
    private Location retrievelocationFromPreferences() {
    	SharedPreferences prefs = this.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
    	Location location = new Location("POINT_LOCATION");
    	location.setLatitude(prefs.getFloat(POINT_LATITUDE_KEY, 0));
    	location.setLongitude(prefs.getFloat(POINT_LONGITUDE_KEY, 0));
    	return location;
    }
    
    public class MyLocationListener implements LocationListener {
    	public void onLocationChanged(Location location) {
    		Location pointLocation = retrievelocationFromPreferences();
    		float distance = location.distanceTo(pointLocation);
    		Toast.makeText(ProxAlertActivity.this, 
    				"Distance from Point:"+distance +" / "+location.getAccuracy(), Toast.LENGTH_LONG).show();
    		
    	}
    	public void onStatusChanged(String s, int i, Bundle b) {			
    	}
    	public void onProviderDisabled(String s) {
    	}
    	public void onProviderEnabled(String s) {			
    	}
    }
    


	public class PolygonLocationListener implements LocationListener {
		public void onLocationChanged(Location location) {
			broadCastNotify(location);	
		}
		public void onStatusChanged(String s, int i, Bundle b) {			
		}
		public void onProviderDisabled(String s) {
		}
		public void onProviderEnabled(String s) {			
		}
		
		private void broadCastNotify(Location location) {

			Point p = new Point(location.getLatitude(), location.getLongitude());
			

			boolean isInside = polygon.contains(p) ;
			
	        Intent i = new Intent();

			if(isInside){
				if(!alreadyInside){
			        i.putExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);

					try {
						proximityIntent.send(ProxAlertActivity.this, 0,i);
					} catch (CanceledException e) {
						e.printStackTrace();
					}
					alreadyInside=true;
				}
			}else{
				if(alreadyInside){
			        i.putExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
	
					try {
						proximityIntent.send(ProxAlertActivity.this, 0,i);
					} catch (CanceledException e) {
						e.printStackTrace();
					}
					alreadyInside=false;
				}
			}
			//Toast.makeText(ProxAlertActivity.this, 
    		//		"Is In :"+ isInside, Toast.LENGTH_SHORT).show();
    						
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	class LatLng
    {
        double Latitude;
        double Longitude;

        LatLng(double lat, double lon)
        {
            Latitude = lat;
            Longitude = lon;
        }
    }
	
	/*

    boolean PointIsInRegion(double x, double y, LatLng[] thePath)
    {
        int crossings = 0;

        LatLng point = new LatLng (x, y);
        int count = thePath.length;
        // for each edge
        for (int i=0; i < count; i++) 
        {
            LatLng a = thePath [i];
            int j = i + 1;
            if (j >= count) 
            {
                j = 0;
            }
            LatLng b = thePath [j];
            if (RayCrossesSegment(point, a, b)) 
            {
                crossings++;
            }
        }
        // odd number of crossings?
        return (crossings % 2 == 1);
    }

    boolean RayCrossesSegment(LatLng point, LatLng a, LatLng b)
    {
    	double px = point.Longitude;
    	double py = point.Latitude;
    	double ax = a.Longitude;
    	double ay = a.Latitude;
    	double bx = b.Longitude;
    	double by = b.Latitude;
        if (ay > by)
        {
            ax = b.Longitude;
            ay = b.Latitude;
            bx = a.Longitude;
            by = a.Latitude;
        }
            // alter longitude to cater for 180 degree crossings
        if (px < 0) { px += 360; };
        if (ax < 0) { ax += 360; };
        if (bx < 0) { bx += 360; };

        if (py == ay || py == by) py += 0.00000001;
        if ((py > by || py < ay) || (px > Math.max(ax, bx))) return false;
        if (px < Math.min(ax, bx)) return true;

        float red = (float) ((ax != bx) ? ((by - ay) / (bx - ax)) : Float.MAX_VALUE);
        float blue = (float) ((ax != px) ? ((py - ay) / (px - ax)) : Float.MAX_VALUE);
        return (blue >= red);
    }
    */
}