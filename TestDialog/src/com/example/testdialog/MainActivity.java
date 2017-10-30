package com.example.testdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dialog = new Dialog(MainActivity.this){
			@Override
			public void show(){
				setContentView(R.layout.dialog);
				super.show();
			}
		};
		
		Button buttonDialog = (Button)findViewById(R.id.buttonDialog);
		buttonDialog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.e("MainActivity","Showing");
				dialog.show();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
