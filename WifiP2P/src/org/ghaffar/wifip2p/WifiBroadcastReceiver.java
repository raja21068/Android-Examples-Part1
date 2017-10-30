package org.ghaffar.wifip2p;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.Toast;

public class WifiBroadcastReceiver extends BroadcastReceiver{

	WifiP2pManager manager;
	MainActivity activity;
	Channel channel;

	
	
	public WifiBroadcastReceiver(WifiP2pManager manager, Channel channel, MainActivity activity) {
		this.manager = manager;
		this.activity = activity;
		this.channel = channel;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		
		if(action.equals(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)){
			
			NetworkInfo info = (NetworkInfo)intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
			if(info.isConnected()){
				manager.requestConnectionInfo(channel, (ConnectionInfoListener)activity);
			}
			
		}else if(action.equals(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION)){
			
		}else if(action.equals(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)){
			
			manager.requestPeers(channel, (PeerListListener)activity);
			
//			manager.requestPeers(channel, new PeerListListener(){
//
//				@Override
//				public void onPeersAvailable(WifiP2pDeviceList peers) {
//						ArrayAdapter<WifiP2pDevice> adapter = new ArrayAdapter<WifiP2pDevice>(activity, android.R.layout.simple_list_item_1);
//						adapter.addAll(peers.getDeviceList());
//
//						ListView listView = (ListView)activity.findViewById(R.id.listView1);
//						listView.setAdapter(adapter);
//				}
//			});
		}else if(action.equals(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)){
			
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			
			if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
				Toast.makeText(activity, "WIFI is Enable", Toast.LENGTH_SHORT).show();
			}else if(state == WifiP2pManager.WIFI_P2P_STATE_DISABLED){
				Toast.makeText(activity, "WIFI is Disable", Toast.LENGTH_SHORT).show();
			}
			
		}else if(action.equals(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)){
			
		}
	}
}