package com.example.pattern;

import group.pals.android.lib.ui.lockpattern.widget.LockPatternView;
import group.pals.android.lib.ui.lockpattern.widget.LockPatternView.Cell;
import group.pals.android.lib.ui.lockpattern.widget.LockPatternView.DisplayMode;
import group.pals.android.lib.ui.lockpattern.widget.LockPatternView.OnPatternListener;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.android_lockpattern.R;

public class MainActivity extends Activity {

	int click = 0;
	int drag = 0;
	LockPatternView lock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		lock = (LockPatternView)findViewById(R.id.lockPatternView1);
		
		lock.setOnPatternListener(new OnPatternListener() {
			
			@Override
			public void onPatternStart() {

			}
			
			@Override
			public void onPatternDetected(List<Cell> pattern) {
				lock.setDisplayMode(DisplayMode.Wrong);	
			}
			
			@Override
			public void onPatternCleared() {
				
			}
			
			@Override
			public void onPatternCellAdded(List<Cell> pattern) {
				
			}
		});
	}
	
}
