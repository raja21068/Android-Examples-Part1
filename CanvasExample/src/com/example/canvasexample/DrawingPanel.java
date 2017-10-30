package com.example.canvasexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingPanel extends SurfaceView implements Runnable{

	SurfaceHolder holder;
	Thread thread;
	boolean isRunning=true;
	Paint paint;
	
	private float circleX = 50f;
	private float circleY = 50f;
	
	public DrawingPanel(Context context) {
		super(context);
		holder = getHolder();
		paint = new Paint();
		paint.setColor(Color.BLACK);
		thread = new Thread(this);
		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	public void run(){
		while(isRunning){
			if(!holder.getSurface().isValid())continue;
			
			Canvas canvas = holder.lockCanvas();
			canvas.drawRGB(255, 255, 255);
			canvas.drawCircle(circleX, circleY, 10f, paint);
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	public void resume(){
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void pause(){	
		isRunning = false;
		while(true){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}
}
