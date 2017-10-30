package com.example.voiceexample;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new SpeakingTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class SpeakingTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				Thread.sleep(3000);
				int bufferSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
			
				byte[] buffer = new byte[bufferSize];
				Log.e("Buffer Input", ""+bufferSize);
				AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC,8000,AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
	
				int bufferSizeForOutput = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
				AudioTrack at = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT	 , bufferSizeForOutput, AudioTrack.MODE_STREAM);
				Log.e("Buffer Output", ""+bufferSizeForOutput);
				while(true){
					ar.startRecording();
				
					
					ar.read(buffer,0, bufferSize);
				
					at.write(buffer,0, bufferSizeForOutput);
					at.flush();
					at.play();
				}
			}catch(Exception ex){ex.printStackTrace();}
			return null;
		}
	}
	
}
