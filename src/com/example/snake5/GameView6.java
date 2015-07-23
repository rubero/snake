package com.example.snake5;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView6 extends SurfaceView implements OnTouchListener  {
	String TAG = this.getClass().getSimpleName();
	Paint mPaint, cPaint, aPaint;
	private Bitmap bmp; // Загружаемая картинка
	private SurfaceHolder holder; // Наше поле рисования
	private GameManager6 gameLoopThread; // Объект класса GameManager
	static int cWidth = 8; // Размер головы (четный) = ширина ободка
	static int width; // размеры холста игры 480x690, середина 240x345
	static int pY, pX;
	static int height, heightReal;
	static int top = 18;  // сдвиг сверху (четный)
	Snake6 mSnake;
	int t = 0;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		mSnake.setDirection(getDirection(event.getX(), event.getY())); // направление	
		return true;
	}
	
	/** Остановка потока */
	public void stopRunning() {
		gameLoopThread.setRunning(false);
	}
	
	/**  Поиск выбранного направления */
	int getDirection(float x, float y) {
		int dir;
		float y1 = y - height/2; // расстояние от центра
		if (x > width/2) {	
			if (Math.abs(y1) < height/4)
				dir = Snake.DIR_RIGHT;
			else
				dir = y1 > 0 ? Snake.DIR_DOWN : Snake.DIR_UP;
		} else {
			if (Math.abs(y1) < height/4)
				dir = Snake.DIR_LEFT;
			else
				dir = y1 > 0 ? Snake.DIR_DOWN : Snake.DIR_UP;
		}
		if (Math.abs(mSnake.getDirection() - dir) != 2) return dir; // проверка на текущее направление
		return mSnake.getDirection();
	}
	
	public GameView6(Context context) {
		super(context);
		setOnTouchListener(this);		
		
		mPaint = new Paint();
		mPaint.setColor(Color.GRAY); // 0xFFFFFFFF	
		
		cPaint = new Paint(); // край поля
		cPaint.setColor(Color.GREEN); // 0xFFFFFFFF	
		cPaint.setStyle(Paint.Style.STROKE);
		cPaint.setStrokeWidth(cWidth);
		cPaint.setAntiAlias(true);
		
		cPaint.setDither(true);
		cPaint.setStyle(Paint.Style.STROKE);
		cPaint.setStrokeJoin(Paint.Join.ROUND);
		cPaint.setStrokeCap(Paint.Cap.ROUND);
		
		aPaint = new Paint(); // фрукт
		aPaint.setColor(Color.RED);
		
		gameLoopThread = new GameManager6(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) { }
				}
			}
			
			public void surfaceCreated(SurfaceHolder holder) {
				 gameLoopThread.setRunning(true);
				 gameLoopThread.start();				 
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				
				mSnake.setApple2();
			}
		});
		
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic);		
	}

	/** Отрисовка объектов */
	@Override
	protected void onDraw(Canvas canvas) {	
		if (!gameLoopThread.running) return;
		mSnake.thisMove(); // расчет положения
//		Log.d(TAG, canvas.getWidth() +"x"+ canvas.getHeight());
		canvas.drawColor(Color.BLUE);

//		Log.d(TAG, "------ " + mSnake.mDirection + " ------");
		for (int i = 0; i < mSnake.getSnakeLength(); i++) {		
			int mX = mSnake.getmSnake().get(i).x;
			int mY = mSnake.getmSnake().get(i).y;
			if (i==0) Log.d(TAG, i + "; " + mX + "x" + mY);
			canvas.drawRect(mX, mY, mX + cWidth, mY + cWidth, mPaint);
		}
		
		// Поле
		canvas.drawRect(pX, pY, width-cWidth/2, height-cWidth/2 + top, cPaint);
		canvas.drawRect(mSnake.appleX, mSnake.appleY, mSnake.appleX + cWidth, mSnake.appleY + cWidth, aPaint);
		t++; if (t == 10) {mSnake.setApple2(); t = 0;}
	}
	
	/** Определить ширину холста игры */
	
    @Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);	 
	    if (pX == 0) { // один раз выполнить	    
		    width = MeasureSpec.getSize(widthMeasureSpec);
		    heightReal = MeasureSpec.getSize(heightMeasureSpec);
		    height = 10 * Math.abs((heightReal - top)/10); // чтоб 10 в конце
		    Log.d(TAG, "heightReal: " + width + "x" + heightReal);
		    Log.d(TAG, "поле:       " + width + "x" + height);
//	    240x394
		    pX = cWidth/2; // 4
		    pY = cWidth/2 + top; // 22
		    Log.d(TAG, "pX-pY: " + pX + "x" + pY);
		    
		    top = heightReal - height; // 24
		    Log.d(TAG, "top: " + top);
		    
		    int ox = width/2;
		    int oy = pY + cWidth/2 + cWidth*5;
		    mSnake = new Snake6(ox, oy);
		    Log.d(TAG, "mSnake: " + ox + "x" + oy);
	    }
    }
}
