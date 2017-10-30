package com.example.alarmmanagerexample;

import java.util.List;

import com.example.DataBaseHandler.ContactBean;
import com.example.DataBaseHandler.DatabaseHandler;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

public class AlarmManagerExample extends Activity {
    
	EditText edittextNumber,editTextMyAlternativeNo;
	Button buttonAdd,buttonAddMyAlternativeNo;
	Button buttonShow,buttonShowMyAlternativeNo;
	boolean isAlarmSet = false;
	static DatabaseHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_manager_example);
		db=new DatabaseHandler(this);
		buttonShow=(Button)findViewById(R.id.buttonshow);
		buttonAddMyAlternativeNo=(Button)findViewById(R.id.buttonAddMyAlternativeNo);
		buttonShowMyAlternativeNo=(Button)findViewById(R.id.buttonShowMyAlternativeNo);
		editTextMyAlternativeNo=(EditText)findViewById(R.id.editTextMyAlternativeNo);
		SendingDetector sd=new SendingDetector(this);
		sd.listenForSMS();
	
	
		
	buttonShow.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {
				showRecord();
				Toast.makeText(getApplicationContext(), "Showing Record...", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
	});
	buttonAdd=(Button)findViewById(R.id.buttonAdd);
	buttonAdd.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(buttonAdd.getText().equals("Add")){
				addRecord();
				buttonAdd.setText("Update");
				Toast.makeText(getApplicationContext(), "Number Insert Sucessfully", Toast.LENGTH_LONG).show();
				}else if(buttonAdd.getText()=="Update"){
					updateRecord();
					Toast.makeText(getApplicationContext(), "Data Update Sucessfully", Toast.LENGTH_LONG).show();
				}				
		}
	});
				edittextNumber=(EditText)findViewById(R.id.editTextNumber);
				ContactBean bean = db.getContactBean();
				int count= db.getContactsCount();
				Toast.makeText(this, "count"+ count, Toast.LENGTH_LONG).show();
				if(count>0){
				buttonAdd.setText("Update");
				buttonAddMyAlternativeNo.setText("Update");
				edittextNumber.setText(bean.getPhoneNumber());
	}
			
		
				buttonAddMyAlternativeNo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(buttonAddMyAlternativeNo.getText().equals("Add")){
							updateRecord();
							buttonAddMyAlternativeNo.setText("Update");
							Toast.makeText(getApplicationContext(), "Number Insert Sucessfully", Toast.LENGTH_LONG).show();
							}else if(buttonAddMyAlternativeNo.getText()=="Update"){
								updateRecord();
								Toast.makeText(getApplicationContext(), "Data Update Sucessfully", Toast.LENGTH_LONG).show();
							}				
					}
				});
				
}
		private void showRecord() throws Exception {
			int count= db.getContactsCount();
			if(count>0){
				List<ContactBean> list= db.getAllContacts();
				ContactBean bean = list.get(0);
				edittextNumber.setText(bean.getPhoneNumber());
				editTextMyAlternativeNo.setText(bean.getEmail());
				//Toast.makeText(getApplicationContext(), bean.getPhoneNumber(), Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), bean.getID(), Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), bean.getEmail(), Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), bean.getName(), Toast.LENGTH_LONG).show();
		}
	}
		private void updateRecord(){
			ContactBean bean=db.getContactBean();
			if(!(edittextNumber.getText().equals(""))){
			bean.setPhoneNumber(edittextNumber.getText().toString());		
			}
			if(!(editTextMyAlternativeNo.getText().equals(""))){
			bean.setEmail(editTextMyAlternativeNo.getText().toString());
			Log.e("raja","fsd");
			}
				try {
				db.updateContact(bean);
				} catch (Exception e) {
		// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		private void addRecord() {
				try {
					db.addContact("", edittextNumber.getText().toString(), "");
				} catch (Exception e) {
		// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				Log.e("added","sucessfully");	
}
}