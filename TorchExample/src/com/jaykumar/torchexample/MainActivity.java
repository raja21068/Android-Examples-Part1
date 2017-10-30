package com.jaykumar.torchexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	Camera camera;
	boolean flashOn = true;
	Parameters params;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setCamera();
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(flashOn){
					params.setFlashMode(Parameters.FLASH_MODE_OFF);
					System.out.println(Parameters.FLASH_MODE_OFF);
					camera.setParameters(params);
					//camera.stopPreview();
					flashOn = !flashOn;
					btn.setBackgroundResource(R.drawable.off);
				}else{
						System.out.println("TORCH> "+Parameters.FLASH_MODE_TORCH);
						System.out.println("AUTO> "+Parameters.FLASH_MODE_AUTO);
						System.out.println("ON> "+Parameters.FLASH_MODE_ON);
						System.out.println("REDEYE>"+Parameters.FLASH_MODE_RED_EYE);
						params.setFlashMode(Parameters.FLASH_MODE_TORCH);
						camera.setParameters(params);
					//camera.startPreview();
					flashOn = !flashOn;
					btn.setBackgroundResource(R.drawable.on);
				}
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setCamera();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (camera != null) {
			camera.release();
			camera = null;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}
	
	public void setCamera(){
		camera = Camera.open();
		boolean hasFlash = getApplicationContext().getPackageManager()
		        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		 
		if (!hasFlash) {
		    // device doesn't support flash
		    // Show alert message and close the application
		    AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
		            .create();
		    alert.setTitle("Error");
		    alert.setMessage("Sorry, your device doesn't support flash light!");
		    alert.setButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            // closing the application
		            finish();
		        }
		    });
		    alert.show();
		    return;
		}
		params = camera.getParameters();
		
	}

}
