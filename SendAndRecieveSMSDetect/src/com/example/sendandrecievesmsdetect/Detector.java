package com.example.sendandrecievesmsdetect;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

public class Detector extends ContentObserver{
	Activity parent;
	
	public Detector(Handler handler , Activity parent) {
		super(handler);
		this.parent = parent;
	}
	
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Uri uriSMSURI = Uri.parse("content://sms");
        Cursor cur = parent.getContentResolver().query(uriSMSURI, null, null,null, null);
		cur.moveToNext();
		
		String id = cur.getString(cur.getColumnIndex("_id"));
        String message = cur.getString(cur.getColumnIndex("body"));
        String phoneNumber = cur.getString(cur.getColumnIndex("address"));
        
		
		String protocol = cur.getString(cur.getColumnIndex("protocol"));
		
		if(protocol == null){
			//Recieved Message
		}else{
			// Message Send from Your Mobile 
		}
	}
	
}
