package com.example.snake5;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView5 extends SurfaceView implements SurfaceHolder.Callback {
	private DrawThread drawThread;

	public GameView5(Context context) {
		super(context);
		getHolder().addCallback(this);
//		Canvas canvas = null;
//		canvas = getHolder().lockCanvas();
//		super.onDraw(canvas);
//		this.draw(getHolder().lockCanvas());
//		invalidate();
	}

	 @Override
	 public void onDraw(Canvas canvas) {
		 
		 super.onDraw(canvas);
		 canvas.drawColor(Color.RED);
		 Log.d("GameView5", "onDraw" + canvas.getWidth());

	 }	
	
	@Override
	// был изменен формат или размер SurfaceView
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("GameView", "surfaceChanged");
	}

	@Override
	// SurfaceView создан и готов к отображению информации
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("GameView", "surfaceCreated");
//		Canvas cv = holder.lockCanvas(null);
//		onDraw(cv); // onDraw(holder.lockCanvas(null));
//		cv.drawColor(Color.GREEN);		
		drawThread = new DrawThread(getHolder());
		drawThread.setRunning(true);
		drawThread.start();
//		holder.lockCanvas(null).drawColor(Color.GREEN);
		this.draw(holder.lockCanvas());
		invalidate();
		
	}

	void go(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setColor(Color.BLUE); // 0xFFFFFFFF
		canvas.drawRect(100, 100, 110, 110, mPaint);
		Log.d("", "canvas" + canvas.getWidth());
	}

	@Override
	// вызывается перед тем, как SurfaceView будет уничтожен
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		drawThread.setRunning(false);
		while (retry) {
			try {
				drawThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	// В этом потоке будет происходить рисование.
	class DrawThread extends Thread {
		private boolean running = false;
		private SurfaceHolder surfaceHolder;
		Canvas canvas;

		public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;
		}

		public void setRunning(boolean running) {
			this.running = running;
		}

		@Override
		public void run() {
			while (running) {
				canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas(null);
					if (canvas == null)
						continue;
					
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
}
