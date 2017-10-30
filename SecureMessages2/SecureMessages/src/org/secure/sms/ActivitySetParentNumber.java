package org.secure.sms;

import DataBase.ContactBean;
import DataBase.DatabaseHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySetParentNumber extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
        
		setContentView(R.layout.activitysetparentnumber);
        Button btnP = (Button) findViewById(R.id.numberButton);
        btnP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        TextView number=(TextView)findViewById(R.id.numberText);
		        
		  int rows = 0;
		try {
			rows = SecureMessagesActivity.db.updateContact(new ContactBean(1, "", number.getText().toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				if(rows>0){
				Toast.makeText(getApplicationContext(), "added successfully", Toast.LENGTH_LONG).show();
				finish();
				}
			}
		});

	}
	

}
