package com.example.calltracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

//import com.netlync.sociotravel.handle.JSONParser;



import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	  Button btnShowLocation;
	     
	    // GPSTracker class
	    GPSTracker gps;
	    double latitude;
		double longitude;
		TextView textViewlongitude,textViewlatitude;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
		textViewlongitude=(TextView)findViewById(R.id.textViewlatitude);
		textViewlatitude=(TextView)findViewById(R.id.textViewLongitude);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
                // create class object
                gps = new GPSTracker(MainActivity.this);
 
                // check if GPS enabled     
                if(gps.canGetLocation()){
                     
                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();
                     Toast.makeText(getApplicationContext(), ""+latitude, Toast.LENGTH_LONG).show();
                     getLocation GL =new getLocation(longitude,latitude);
                     GL.execute();
                     
                     	
                    // \n is for new line
                    
                   
                    
                
                    //Toast.makeText(getApplicationContext(), "location"+location, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
        });
       
        	
        }
        
	 private class getLocation extends AsyncTask<Void, Void,List<Address>>{
     	double Longitude;
     	double Latitude;
     	
     	getLocation(double Longitude,double Latitude){
     		this.Longitude=Longitude;
     		this.Latitude=Latitude;
     		
     	}
     	
		
			@Override
		protected void onPostExecute(List<Address> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try{
			if(result.size()>0){
				textViewlatitude.setText("0."+result.get(0));
				textViewlongitude.setText("1."+result.get(1));

			 for(int i=0; i<result.size();i++){
					
				 	//Log.e("location",""+ result.get(i));
		       	 }
			}
			
			
			}catch(Exception e){e.printStackTrace();}
	//	Toast.makeText(getApplicationContext(), ""+result.get(0), Toast.LENGTH_LONG).show();   	 
		}


			@Override
			protected List<Address> doInBackground(Void... arg0) {
				String str2 = "";
				  JSONObject job = null;
				  try
			    {
					 
			       	 List<Address> list = getlocation.getStringFromLocation(Latitude, Longitude);
			       	 if(list.size()>0){
			       	 for(int i=0; i<list.size();i++){
			       		Log.e("location:",""+i+". "+ list.get(i));
			       	 }
			       	 }
			       	 
			            return list;
				
			    } catch(Exception ex){ex.printStackTrace();   }
				return null;
			}
	 }
	        
	    
	       
	       
	  


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
