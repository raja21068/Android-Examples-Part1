package com.example.gpsexample;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener{

	private LocationManager locationManager;
	
	TextView longitude , latitude, altitude;
	Button buttonExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0.0F, this, Looper.getMainLooper());
		
	}
	
	private void init(){
		longitude = (TextView)findViewById(R.id.textViewLongitude);
		latitude = (TextView)findViewById(R.id.textViewLatitude);
		altitude = (TextView)findViewById(R.id.textViewAltitude);
		buttonExit = (Button)findViewById(R.id.buttonExit);
		buttonExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		longitude.setText("Longitude: "+location.getLongitude());
		latitude.setText("Latitude: "+location.getLatitude());
		altitude.setText("Altitude: "+location.getAltitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS Turned Off", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS Turned On", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//Toast.makeText(getApplicationContext(), "GPS Status Changed", Toast.LENGTH_LONG).show();
		
	}

}
