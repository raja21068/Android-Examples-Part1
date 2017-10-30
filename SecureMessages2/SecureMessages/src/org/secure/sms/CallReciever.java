package org.secure.sms;

import java.util.List;

import DataBase.ContactBean;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallReciever extends BroadcastReceiver{        

	
    private static final String TAG = "PhoneStatReceiver"; 

    private static boolean incomingFlag = false;

    private static String incoming_number = null;



    public void onReceive(Context context, Intent intent) {

       

            if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){                        

                    incomingFlag = false;

                    String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);        

                    Log.i(TAG, "call OUT:"+phoneNumber);   
                    Toast.makeText(context, "call OUT:"+phoneNumber, Toast.LENGTH_LONG).show();
                   
                    //send message to parent number
                    try{
                    	  List<ContactBean> l = SecureMessagesActivity.db.getAllContacts();
                          if(l.size()>0){
                           SmsSender.sendLongSMS(l.get(0).getPhoneNumber(), "call OUT:"+phoneNumber);
                          }else{
                           Toast.makeText( context, "call OUT:"+phoneNumber, Toast.LENGTH_SHORT ).show();
                          }
                    }catch(Exception ex){
                    	ex.printStackTrace();
                    }
                    
                    
                    System.out.println("call OUT:"+phoneNumber);

            }else{                        

                    
            TelephonyManager tm =(TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);                        

                    switch (tm.getCallState()) {

                    		case TelephonyManager.CALL_STATE_RINGING:

                            incomingFlag = true;

                            incoming_number = intent.getStringExtra("incoming_number");

                            Log.i(TAG, "RINGING :"+ incoming_number);
                            Toast.makeText(context,"RINGING :"+incoming_number, Toast.LENGTH_LONG).show();
                            System.out.println("RINGING :"+incoming_number);
                            
                          //send message to parent number
                            try{
                            	  List<ContactBean> l = SecureMessagesActivity.db.getAllContacts();
                                  if(l.size()>0){
                                   SmsSender.sendLongSMS(l.get(0).getPhoneNumber(), "RINGING :"+incoming_number);
                                  }else{
                                   Toast.makeText( context, "RINGING :"+incoming_number, Toast.LENGTH_SHORT ).show();
                                  }
                            }catch(Exception ex){
                            	ex.printStackTrace();
                            } 
                            
                            
                            break;

                    case TelephonyManager.CALL_STATE_OFFHOOK:                                

                            if(incomingFlag){

                                    Log.i(TAG, "incoming ACCEPT :"+ incoming_number);
                                    Toast.makeText(context, "incoming ACCEPT :"+ incoming_number, Toast.LENGTH_LONG).show();
                                    System.out.println("RINGING :"+"incoming ACCEPT :"+ incoming_number);
                                    
                                  //send message to parent number
                                    try{
                                    	  List<ContactBean> l = SecureMessagesActivity.db.getAllContacts();
                                          if(l.size()>0){
                                           SmsSender.sendLongSMS(l.get(0).getPhoneNumber(),"incoming ACCEPT :"+ incoming_number);
                                          }else{
                                           Toast.makeText( context, "incoming ACCEPT :"+ incoming_number, Toast.LENGTH_SHORT ).show();
                                          }
                                    }catch(Exception ex){
                                    	ex.printStackTrace();
                                    } 
                                            
                                    
                            }

                            break;
                    case TelephonyManager.CALL_STATE_IDLE:                                

                            if(incomingFlag){

                                    Log.i(TAG, "incoming IDLE");                                
                                    Toast.makeText(context, "Incoming IDLE", Toast.LENGTH_LONG).show();
        
                            }

                            break;

                    } 

            }

    }
}
    

