package com.example.alarmmanagerexample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class RingAlarm extends Activity {
	    
	   MediaPlayer mp=null ;
	  EditText editTextMessage,editTextBossMessage;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			        super.onCreate(savedInstanceState);
			        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			                WindowManager.LayoutParams.FLAG_FULLSCREEN);
			        
			        setContentView(R.layout.alarm);
			        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
			        editTextMessage=(EditText)findViewById(R.id.editTextMessage);
			        editTextBossMessage=(EditText)findViewById(R.id.editTextBossMessage);
			        editTextBossMessage.setText(SendingDetector.bossMessage);	
			        mp = MediaPlayer.create(getBaseContext(),R.raw.audio);
			        
			        stopAlarm.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mp.stop();
						//SendingDetector.am.cancel(operation)
							if(editTextMessage.getText().toString().equals("")){
								finish();
								return ;
							} 
								
							Log.e("finish","finish");
							SmsSender.sendLongSMS(SendingDetector.number, editTextMessage.getText().toString());
							SendingDetector.am.cancel(SendingDetector.pendingIntent); 
			                 finish();
				               
						}
					});
			 
			        playSound(this, getAlarmUri());
			    }
			 
			    private void playSound(final Context context, Uri alert) {
			         
			        
			        Thread background = new Thread(new Runnable() {
						public void run() {
							try {
								
	    		               mp.start();
	    		               
							} catch (Throwable t) {
								Log.i("Animation", "Thread  exception "+t);
							}	
				        }
	        	 });
	        	 background.start();
			   }
			 
			    @Override
				protected void onDestroy() {
					super.onDestroy();
					mp.stop();
				}		        //Get an alarm sound. Try for an alarm. If none set, try notification,
			        //Otherwise, ringtone.
			    private Uri getAlarmUri() {
			    	
			        Uri alert = RingtoneManager
			                .getDefaultUri(RingtoneManager.TYPE_ALARM);
			        if (alert == null) {
			            alert = RingtoneManager
			                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			            if (alert == null) {
			                alert = RingtoneManager
			                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			            }
			        }
			        return alert;
			    }
			
}
