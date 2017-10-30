package com.example.alarmmanagerexample;

import com.example.DataBaseHandler.ContactBean;
import com.example.alarmmanagerexample.AlarmManagerExample;
import com.example.alarmmanagerexample.RingAlarm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;



@SuppressLint("HandlerLeak")
public class SendingDetector{

	ContactBean bean;
	Activity parent;
	public static AlarmManager am;
	public static PendingIntent pendingIntent;
	public static String number;
	public static String bossMessage;
	public SendingDetector(Activity act){
		parent = act;
	}
	
	public void listenForSMS(){
	    Log.v("SMSTEST", "STARTED LISTENING FOR SMS OUTGOING");
	    Handler handle = new Handler(){};

	    SMSObserver myObserver = new SMSObserver(handle);

	    ContentResolver contentResolver = parent.getContentResolver();
	    contentResolver.registerContentObserver(Uri.parse("content://sms"),true, myObserver);
	}

	private class SMSObserver extends ContentObserver{

    
	    public SMSObserver(Handler handler) {
	        super(handler);
	    }

	    public void onChange(boolean selfChange) {
	        super.onChange(selfChange);
	        Uri uriSMSURI = Uri.parse("content://sms");
	        Cursor cur = parent.getContentResolver().query(uriSMSURI, null, null, null, null);
	        cur.moveToFirst();

	        for(int i=0 ; i<cur.getColumnCount(); i++){
	        	System.out.print("key: "+ cur.getColumnName(i));
	        	System.out.println("value: "+ cur.getString(i));
	        	
	        	
	        }
	        String idCheck = "";
	        String id = cur.getString(cur.getColumnIndex("_id"));
	        String content = cur.getString(cur.getColumnIndex("body"));
	        String no=cur.getString(cur.getColumnIndex("address"));
	        
	        	        
	        if(id.equals(idCheck))return;
	        idCheck = id;
	        bean = AlarmManagerExample.db.getContactBean();
 	        
	        number = bean.getPhoneNumber();
	            Log.e("fsd","fsd"+no);
			
	       Intent intent = new Intent(parent, RingAlarm.class);
	        pendingIntent = PendingIntent.getActivity(parent,
	            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	         am =(AlarmManager)parent.getSystemService(Activity.ALARM_SERVICE);
	       
	        String protocol = cur.getString(cur.getColumnIndex("protocol"));
	        if(protocol == null){
	        	if(no.equals(number)){
	        		Log.e("test","null");
	            am.cancel(pendingIntent);
	        	}
	        	
	        }
	        else{
	        		try {
	        			if(no.equals(number)){
	        				Log.e("fsd","fsd");
	    	        //Create a new PendingIntent and add it to the AlarmManager
	    	        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
	    	        		7500,pendingIntent);
	    	        bossMessage=content;
	    	        //am.cancel(pendingIntent);
	    	        Log.e("fsd","fsd");
	    	        parent.getApplicationContext();
	    	        SmsSender.sendLongSMS(bean.getEmail(), content.toString());
	    				}
	    	        
	    		  } catch (Exception e) {}
	        } 
	    }
	}

}



