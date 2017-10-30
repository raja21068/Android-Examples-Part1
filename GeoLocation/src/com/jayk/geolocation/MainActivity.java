package com.jayk.geolocation;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener{

	private LocationManager mgr;
    private String best;
    Location location;
    public static double myLocationLatitude;
    public static double myLocationLongitude;
    
    public String appName = "Geo Location";

    TextView tv;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        best = mgr.getBestProvider(criteria, true);
        location = mgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        dumpLocation(location);
        
        tv= (TextView)findViewById(R.id.textView);
    }

    public void onLocationChanged(Location location) {
        dumpLocation(location);
        String str = location.getProvider();
        tv.setText("onLocationChanged > "+str);
    }

    public void onProviderDisabled(String provider) {
        Log.e(appName, "onProviderDisabled> "+myLocationLatitude+" >> "+myLocationLongitude);
        tv.setText("onProviderDisabled> "+provider);
    }

    public void onProviderEnabled(String provider) {
        Log.e(appName, "onProviderEnabled> "+myLocationLatitude+" >> "+myLocationLongitude);
        tv.setText("onProviderEnabled > "+provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(appName, "onStatusChanged> "+myLocationLatitude+" >> "+myLocationLongitude);
        tv.setText("onStatusChanged > "+provider);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgr.requestLocationUpdates(best, 15000, 10, this);
    }

    private void dumpLocation(Location l) {

        if (l != null){
        	myLocationLatitude = l.getLatitude();
            myLocationLongitude = l.getLongitude();
            Log.e(appName, "> "+myLocationLatitude+","+myLocationLongitude);
            
            Geocoder geocoder=new Geocoder(MainActivity.this,Locale.getDefault());
            try{
            	List<Address> list = geocoder.getFromLocation(myLocationLatitude, myLocationLongitude, 1);
            	if(list.size() > 0 ){
            		for(int i=0;i<list.get(0).getMaxAddressLineIndex();i++){
            			String display = "";
            			display+=list.get(0).getAddressLine(i)+"\n";
            			Log.e(appName, display);
            		}
            	}
            }catch(Exception e){
            	e.printStackTrace();
            }
            
            
        }
    }
}
