package com.jk.smssending;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button buttonSend;
	EditText etPhone, etMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etPhone = (EditText)findViewById(R.id.editTextPhone);
		etMessage = (EditText)findViewById(R.id.editTextMessage);
		buttonSend = (Button)findViewById(R.id.buttonSend);
		
		buttonSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String no = etPhone.getText().toString();
				String mes = etMessage.getText().toString();
				SmsManager manager = SmsManager.getDefault();
				manager.sendTextMessage(no, null, mes, null, null);
			}
		});
		listenForSMS();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void listenForSMS(){
	    Log.v("SMSTEST", "STARTED LISTENING FOR SMS OUTGOING");
	    Handler handle = new Handler(){};

	    SMSObserver myObserver = new SMSObserver(handle);

	    ContentResolver contentResolver = getContentResolver();
	    contentResolver.registerContentObserver(Uri.parse("content://sms"),true, myObserver);
	}

	private class SMSObserver extends ContentObserver{

	    int count = 0;
	    String lastMessage = null;
	    String idCheck="";
	    
	    public SMSObserver(Handler handler) {
	        super(handler);
	    }

	    public void onChange(boolean selfChange) {
	        super.onChange(selfChange);
	        //Log.v("SMSTEST", "HIT ON CHANGE");

	        Uri uriSMSURI = Uri.parse("content://sms");
	        
	        Cursor cur = getContentResolver().query(uriSMSURI, null, null,
	                     null, null);
	        cur.moveToNext();
	        //OUTER TEST
	        String id = cur.getString(cur.getColumnIndex("_id"));
	        String content = cur.getString(cur.getColumnIndex("body"));
	        if(id.equals(idCheck))return;
	        idCheck = id;
	        Toast.makeText(getApplicationContext(), id+":"+count, Toast.LENGTH_SHORT).show();
	        Log.v("ALERT", content);
	        // String protocol = cur.getString(cur.getColumnIndex("protocol"));
	       // if(protocol == null){
	            count++;
	        //    Log.v("SMSTEST", "SMS SENT: count: " + count);
	        //    String content = cur.getString(cur.getColumnIndex("body"));

	       // }else{
	         //   Log.v("SMSTEST", "SMS RECIEVED");
	       // }
	    }
	}
	
}
