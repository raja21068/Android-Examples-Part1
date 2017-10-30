package com.example.serverconntest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        Button b = (Button)findViewById(R.id.buttonConnect);
    	b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText ip = (EditText)findViewById(R.id.editTextIP); 
				EditText port = (EditText)findViewById(R.id.editTextPort); 
				try {
				   Socket socket = new Socket(""+ip.getText(),Integer.parseInt(port.getText().toString()));
				   PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				   out.println("Hello this is the text");
				   port.setText("got output stream");
				   } catch (Exception e) {
					port.setText(""+e.toString());
				}
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
