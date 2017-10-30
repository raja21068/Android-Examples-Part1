package org.secure.sms;

import java.util.List;

import DataBase.ContactBean;
import DataBase.DatabaseHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecureMessagesActivity extends Activity 
{
	public static DatabaseHandler db;
	
	
	Intent intentParentNumer;
	 TextView t;
	 TextView pass;
	 TextView number;
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setTheme( android.R.style.Theme_Light );
        setContentView(R.layout.main);
     
   
        t=(TextView)findViewById(R.id.textView1);
  		pass=(TextView)findViewById(R.id.passText);
        number=(TextView)findViewById(R.id.numberText);
        intentParentNumer = new Intent("org.secure.sms.ACTIVITYSETPARENTNUMBER");
        
        db = new DatabaseHandler(this);
        
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts.."); 
        System.out.println("Reading All Contacts");
        List<ContactBean> contacts = null;
		try {
			contacts = db.getAllContacts();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}       
         
        for (ContactBean cn : contacts) {
        	
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getPassword() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
            Toast.makeText(getApplicationContext(), log, Toast.LENGTH_LONG).show();
            System.out.println("data"+log);
            Log.d("Name: ", log);
    }
        
        
     // Change Label        
        int record=db.getContactsCount();
  		Toast.makeText(getApplicationContext(), "Record: "+record,Toast.LENGTH_LONG).show();
        System.out.println("Record: "+record);
  		if(record>0){
  		t.setText("Enter Password");
  		}
  		
  		
 
     
        //--------------Pass Button-----------------------------
         
        Button buttonPass = (Button)findViewById(R.id.passButton);
        buttonPass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "teswe", Toast.LENGTH_SHORT).show();
				
			//check text field
				if(pass.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Plz Enter Password",Toast.LENGTH_SHORT);
				}
			//insert password in database
			
//  			Toast.makeText(getApplicationContext(), t.getText(), Toast.LENGTH_LONG).show();
	  		if(t.getText().equals("Set Password")){
	  			Toast.makeText(getApplicationContext(), t.getText(), Toast.LENGTH_LONG).show();
	  			System.out.println("insert password..");
		        try {
					db.addContact(new ContactBean(pass.getText().toString(),""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        t.setText("Enter Password");
				startActivity(intentParentNumer);
						
	  		}
	  	
			
			int passCheck=db.getPassCheck(pass.getText().toString());	
				if(passCheck>0){
					
					startActivity(intentParentNumer);
					
				}
			}
		});
        
        //--------------Exit Button-----------------------------
        Button buttonExit = (Button)findViewById(R.id.buttonexit);
        buttonExit.setOnClickListener(new View.OnClickListener(
        			) {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
        
        /**
         * You can also register your intent filter here.
         * And here is example how to do this.
         *
         * IntentFilter filter = new IntentFilter( "android.provider.Telephony.SMS_RECEIVED" );
         * filter.setPriority( IntentFilter.SYSTEM_HIGH_PRIORITY );
         * registerReceiver( new SmsReceiver(), filter );
        **/
        
/*        this.findViewById( R.id.UpdateList ).setOnClickListener( this );
    }

    ArrayList<String> smsList = new ArrayList<String>();
    
	public void onItemClick( AdapterView<?> parent, View view, int pos, long id ) 
	{
		try 
		{
		    	String[] splitted = smsList.get( pos ).split("\n"); 
			String sender = splitted[0];
			String encryptedData = "";
			for ( int i = 1; i < splitted.length; ++i )
			{
			    encryptedData += splitted[i];
			}
			String data = sender + "\n" + StringCryptor.decrypt( new String(SmsReceiver.PASSWORD), encryptedData );
			Toast.makeText( this, data, Toast.LENGTH_SHORT ).show();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void onClick( View v ) 
	{
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query( Uri.parse( "content://sms/inbox" ), null, null, null, null);

		int indexBody = cursor.getColumnIndex( SmsReceiver.BODY );
		int indexAddr = cursor.getColumnIndex( SmsReceiver.ADDRESS );
		
		if ( indexBody < 0 || !cursor.moveToFirst() ) return;
		
		smsList.clear();
		
		do
		{
			String str = "Sender: " + cursor.getString( indexAddr ) + "\n" + cursor.getString( indexBody );
			smsList.add( str );
		}
		while( cursor.moveToNext() );

		
		ListView smsListView = (ListView) findViewById( R.id.SMSList );
		smsListView.setAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, smsList) );
		smsListView.setOnItemClickListener( this );
	
	*/
}
}
