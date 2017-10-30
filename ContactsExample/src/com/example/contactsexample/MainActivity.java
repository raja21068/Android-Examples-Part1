package com.example.contactsexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	ListView listContacts;
	ProgressBar progressBar;
	ArrayAdapter<String> adapter;
	ArrayList data;
	MainActivity main ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listContacts = (ListView)findViewById(R.id.listViewSearch);
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		main = this;
		
		adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1);
		new ContactTask().execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void fetchContacts(ArrayAdapter<String> adapter) {

		String phoneNumber = null;
		String email = null;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;

		ContentResolver contentResolver = getContentResolver();

		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {
				StringBuffer output = new StringBuffer();
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

				if (hasPhoneNumber > 0) {

					output.append(name);

					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						output.append(phoneNumber);
					}

					phoneCursor.close();

					// Query and loop for every email of the contact
//					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
//
//					while (emailCursor.moveToNext()) {
//
//						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
//
//						output.append("\nEmail:" + email);
//
//					}

//					emailCursor.close();
				}
				//Log.e("Contact->MainActivity", ""+output);
				adapter.add(output.toString());
				//data.add(output.toString());
			}//end while

			//outputText.setText(output);
		}
	}

	private class ContactTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			data = new ArrayList();
			progressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			fetchContacts(adapter);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
//			SimpleAdapter adapter = new SimpleAdapter(main, data, android.R.layout.simple_list_item_2, new String[] {"title", "phone"}, new int[] {android.R.id.text1, android.R.id.text2});
			
			listContacts.setAdapter(adapter);
			progressBar.setVisibility(View.GONE);
		}
		
	}
	
}
