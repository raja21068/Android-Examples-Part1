package com.example.canvasexample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	DrawingPanel panel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		panel = new DrawingPanel(this);
		setContentView(panel);
		SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		if(sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() !=0){
			Sensor s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		panel.pause();
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		panel.resume();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//Toast.makeText(getApplicationContext(), accuracy+"", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//Toast.makeText(getApplicationContext(), event.sensor+"/"+event.accuracy, Toast.LENGTH_SHORT).show();
		
	}

}
