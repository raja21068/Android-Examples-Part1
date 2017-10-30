package org.ghaffar.wifip2p;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements ConnectionInfoListener, PeerListListener {
	
	String LOG_TAG = "Ghaffar";
	
	private WifiP2pManager manager;
	private Channel channel;
	private WifiBroadcastReceiver broadcastReceiver;
	private WifiP2pInfo info;

	private IntentFilter intentFilter;

	ProgressDialog pd;

	boolean isGroupedFormed;
	
	private int freq = 8000;
	private AudioRecord audioRecord = null;

	private AudioTrack audioTrack = null;
	byte[] buffer = new byte[freq];
	
	private ClientThread clientThread;
	private ServerThread serverThread;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper() , new WifiP2pManager.ChannelListener(){
			@Override
			public void onChannelDisconnected() {
				Toast.makeText(MainActivity.this, "Channel is Disconnected", Toast.LENGTH_LONG).show();
			}
        });
        
        broadcastReceiver = new WifiBroadcastReceiver(manager, channel, this);
        
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
        pd = new ProgressDialog(MainActivity.this);
		pd.setTitle("Searching");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setIndeterminate(true);
		pd.setMessage("Finding Peers");
		
		Button stopBtn = (Button)findViewById(R.id.button2);
		stopBtn.setVisibility(Button.INVISIBLE);
		stopBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				TextView tv = (TextView)findViewById(R.id.textView1);
				tv.setText("------");
				
				if(serverThread != null){
					serverThread.stopThread();
					serverThread = null;
				}
				
				if(clientThread != null){
					clientThread.stopThread();
					clientThread = null;
				}
				
				if(info != null){
					info = null;
				}
				
				if(manager != null){
					manager.removeGroup(channel, null);
				}
				
				Button btn = (Button)v;
				btn.setVisibility(Button.INVISIBLE);
			}});
        
        Button searchBtn = (Button)findViewById(R.id.button1);
        searchBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				manager.discoverPeers(channel, new WifiP2pManager.ActionListener(){

					@Override
					public void onSuccess() {
						pd.show();
					}

					@Override
					public void onFailure(int reason) {
						Toast.makeText(MainActivity.this, "Discovering Peers Failed", Toast.LENGTH_LONG).show();
					}
				});
			}
        });
        
        ListView lv = (ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				if(info != null)
					return;
				
				WifiP2pDevice device = (WifiP2pDevice) parent.getItemAtPosition(position);        		
				
        		final WifiP2pConfig config = new WifiP2pConfig();
				config.deviceAddress = device.deviceAddress;

				manager.connect(channel, config, new WifiP2pManager.ActionListener(){

					@Override
					public void onSuccess() {
						Toast.makeText(MainActivity.this, "Connection Successfull ", Toast.LENGTH_LONG).show();
					}
	
					@Override
					public void onFailure(int reason) {
						Toast.makeText(MainActivity.this, "connection Failed", Toast.LENGTH_LONG).show();
					}
				});
			}
        });
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if(serverThread != null){
			serverThread.stopThread();
			serverThread = null;
		}

		if(clientThread != null){
			clientThread.stopThread();
			clientThread = null;
		}

		if (manager != null) {
			manager.removeGroup(channel, null);
			manager.cancelConnect(channel, null);
		}
	}

	@Override
	public void onConnectionInfoAvailable(WifiP2pInfo info) {
		
		this.info = info;
		TextView tv = (TextView) findViewById(R.id.textView1);

		Toast.makeText(this, "Connection is Avalible", Toast.LENGTH_LONG).show();
		Button stopBtn = (Button)findViewById(R.id.button2);
		stopBtn.setVisibility(Button.VISIBLE);
		
		if (info.groupFormed && info.isGroupOwner) {
			tv.setText("Connected As GroupOwner");
			
			if(serverThread == null){
				serverThread = new ServerThread(9090);
				serverThread.start();
			}

		} else if (info.groupFormed) {
			tv.setText("Connected As Client");
			if(clientThread == null){
				clientThread = new ClientThread(info.groupOwnerAddress, 9090);
				clientThread.start();
			}
		}
	}
	
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peers) {
			ArrayAdapter<WifiP2pDevice> adapter = new ArrayAdapter<WifiP2pDevice>(this, android.R.layout.simple_list_item_1);
			adapter.addAll(peers.getDeviceList());

			ListView listView = (ListView)findViewById(R.id.listView1);
			listView.setAdapter(adapter);
			
			pd.dismiss();
	}
	
	class ServerThread extends Thread{
		
		int port;
		boolean stop;
		
		ServerThread(int port){
			this.port = port;
		}
		
		@Override
		public synchronized void start() {
			stop = false;
			super.start();
		}
		
		public void run(){
			
			ServerSocket server = null;
			Socket client = null;
			InputStream is = null;
			try{
				
				android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
				
				server = new ServerSocket(port);
				
				final int bufferSize = AudioRecord.getMinBufferSize(freq,
			            AudioFormat.CHANNEL_CONFIGURATION_MONO,
			            AudioFormat.ENCODING_PCM_16BIT);
				
				 audioTrack = new AudioTrack(AudioManager.ROUTE_HEADSET, freq,
				            AudioFormat.CHANNEL_CONFIGURATION_MONO,
				            MediaRecorder.AudioEncoder.AMR_NB, bufferSize,
				            AudioTrack.MODE_STREAM);
				 
				 audioTrack.setPlaybackRate(freq);
				 
				 final byte[] buffer = new byte[bufferSize];
				
				client = server.accept();
				is = client.getInputStream();
				
//				Toast.makeText(MainActivity.this, client.getInetAddress().toString(), Toast.LENGTH_LONG).show();
				
				audioTrack.play();
				
				while(!stop){			
					readAndPlay(audioTrack, is, buffer);
				}
			}catch(Exception ex){
				Log.d(LOG_TAG, "EXCEPTION IN SERVERSOCKET");
			}finally{
				try{
					if(is != null)
						is.close();
					if(audioTrack != null){
						audioTrack.stop();
						audioTrack.release();
					}
					if(client != null)
						client.close();
					if(server != null)
						server.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}
		
		public void stopThread(){
			stop = true;
		}
	};
	
	public void readAndPlay(AudioTrack audioTrack, InputStream is, byte buffer[])throws IOException{
		int bytesReaded = is.read(buffer, 0, buffer.length);
		audioTrack.write(buffer, 0, bytesReaded);
	}
	
	class ClientThread extends Thread{
		
		InetAddress address;
		int port;
		boolean stop;
		OutputStream os;
		
		public synchronized void start() {
			stop = false;
			super.start();
		};
		
		ClientThread(InetAddress address, int port){
			this.address = address;
			this.port = port;
		}

		public void run(){
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		    final int bufferSize = AudioRecord.getMinBufferSize(freq,
		            AudioFormat.CHANNEL_CONFIGURATION_MONO,
		            AudioFormat.ENCODING_PCM_16BIT);

		    audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, freq,
		            AudioFormat.CHANNEL_CONFIGURATION_MONO,
		            MediaRecorder.AudioEncoder.AMR_NB, bufferSize);
		    
		    final byte[] buffer = new byte[bufferSize];
		    Socket socket = null;
		    
		    try{
		    	
		    	socket = new Socket(address, port);
		    	os = socket.getOutputStream();
		    	
			    audioRecord.startRecording();
			    
			    byte send[] = "Ghaffar".getBytes();
			    
			    while (!stop) {
	                try {
	                	recordAndSend(audioRecord, os, buffer);
	                } catch (Throwable t) {
	                    Log.e(LOG_TAG, "write failed");
	                    t.printStackTrace();
	                }
	            }
			    
		    }catch(Exception ex){
		    	Log.d(LOG_TAG, "ERROR IN CLIENTSOCKET");
		    }finally{
		    	try{
			    	if(os != null)
			    		os.close();
			    	if(audioRecord != null){
			    		audioRecord.stop();
			    		audioRecord.release();
			    	}
			    	if(socket !=null)
			    		socket.close();
				    
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}
		    	
		    }
		}
		
		public void stopThread(){
			stop = true;
		}
	}
	
	public void recordAndSend(AudioRecord audioRecord, OutputStream os, byte buffer[]) throws IOException{
		int bytesReaded = audioRecord.read(buffer, 0, buffer.length);
        os.write(buffer, 0, bytesReaded);
	}
	
	public InetAddress getClientAddress() throws IOException {
		Enumeration<NetworkInterface> enu = NetworkInterface.getNetworkInterfaces();
		while(enu.hasMoreElements()){
			NetworkInterface ni = enu.nextElement();
			Enumeration<InetAddress> enuAddress = ni.getInetAddresses();
			while(enuAddress.hasMoreElements()){
				InetAddress address = enuAddress.nextElement();
				if(!address.isLoopbackAddress()){
					if(address instanceof Inet4Address){
						return address;
					}
				}
			}
		}
		return null;
	}
}