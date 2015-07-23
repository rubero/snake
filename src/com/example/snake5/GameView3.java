package com.example.snake5;

import java.util.Timer;
import java.util.TimerTask;
import function.Screen;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class GameView3 extends View {
	private static final String TAG = "GAME";
	int mMoveDelay = 1000;
	private Paint mPaint = new Paint();
	int snake[][];
    int left = 20, right = 20, bottom = 20, top = 50; // Отступы от края холста
    int cell_width;
    static Canvas cnv;
    
	public GameView3(Context context) {
		super(context);
		snake = new int[5][3];
	    // Ширина клетки
	    cell_width = Screen.width / 48; // 480x690	
	    
	final Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {

	    }};	   
	    
	    Runnable runnable = new Runnable() {
	        public void run() {
	            Message msg = handler.obtainMessage();
	            handler.sendMessage(msg);
	        }
	    };
	    
	    Thread thread = new Thread(runnable);
	    thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw");
		cnv = canvas;
	    mPaint.setColor(Color.DKGRAY);
	    cnv.drawPaint(mPaint);
		
	    mPaint.setColor(Color.GREEN);
	    cnv.drawRect(100, 100, 110, 110, mPaint);
	    
//	    for (int i = 0; i < snake.length; i++) {
//	    	float y = (Screen.height + top)/2 - (cell_width * i);
//	    	cnv.drawRect(
//	    			Screen.width/2, 
//	    			y, 
//	    			Screen.width/2 + cell_width, 
//	    			y + cell_width, 
//	    			mPaint);
//	    }	
//	    update();
	    
	    

//	    callTimer();
	    

	}

	public void update() {
		Log.d(TAG, "update " + cnv.getWidth());
//		Canvas cn = GameView3.cnv;	
		GameView3.cnv.drawRect(220, 220, 230, 230, mPaint);	
//		GameView3.this.invalidate();
	}
	
//	
	public void callTimer() {
//        final Handler handler = new Handler();
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {       
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    public void run() {       
//                        update();
//                    }
//                });
//            }
//			@Override
//			public boolean cancel() {
//				return super.cancel();
//			}
//        };
//        timer.schedule(task, 0, 1000);     
		
		
		
    }
}
