package com.example.databasecomm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	EditText url ;
	EditText database ;
	EditText user ;
	EditText pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		url = (EditText)findViewById(R.id.editTextHost);
		database = (EditText)findViewById(R.id.editTextDatabase);
		user = (EditText)findViewById(R.id.editTextUsername);
		pass = (EditText)findViewById(R.id.editTextPassword);
		
		Button b= (Button)findViewById(R.id.buttonConnect);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				connn();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void connn(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+url.getText().toString()+":3306/"+database.getText().toString(), user.getText().toString(), pass.getText().toString());
			ResultSet rs = conn.createStatement().executeQuery("SELECT * from user");
			while(rs.next()){
				System.out.println(rs.getString("--> username"));
			}
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
