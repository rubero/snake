package com.example.snake5;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;

public class GameView7 extends SurfaceView implements OnTouchListener {
	String TAG = this.getClass().getSimpleName();
	public int posMeat, GlobDir = 1, scores;
	private GameManager7 gameLoopThread;
	private Canvas mCanvas;
	Snake7 mSnake;
	static int height, width;
	public static int yS; // координаты поля
	Bitmap bitmap;
    Bitmap bitmapAlpha;
    Paint bPaint, paint, lPaint, iPaint, tPaint;
    Rect bRect, myRect, iRect;
    float[] ptsH, ptsV;
    Path path;
    float xT, yT; // координаты текста
    
	@Override
	protected void onDraw(Canvas canvas) {	
		canvas.drawRect(iRect, iPaint);
		canvas.drawText("Счет: ", xT, yT, tPaint);
		canvas.drawRect(myRect, paint);
		canvas.drawRect(bRect, bPaint); // бордюр
		canvas.drawLines(ptsH, lPaint); // сетка
		canvas.drawLines(ptsV, lPaint); // вертикальная сетка
	}
	
	public GameView7(Context context) {
		super(context);		
		SurfaceHolder holder = getHolder();
		
		gameLoopThread = new GameManager7(this);
		
		setOnTouchListener(this);
		
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override
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
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				 gameLoopThread.setRunning(true);
				 gameLoopThread.start();				 
			}
	
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				Log.d(TAG, width +";"+ height);
				// 480x690 (с шапкой)
				int y = 60; // отступ сверху (заданный)
				int hS = 11; // ширина тела змеи
				int hB = hS/2; // ширина бордюра (0 - без бордюра)
				
				// Расчет отступа (снизу)
				int ny = height - hB; // последняя/промежуточная y-координата
				Log.d(TAG, "lastY: " + ny);
				int n = 0; // кол-во строк
				while (ny > y) {
					n++; ny -= hS; // вверх по сетке на ширину змеи
				}
				y = ny; // первая строка сетки
				
				/**************** Текст *************************/
				tPaint = new Paint();
				tPaint.setColor(Color.WHITE);
				tPaint.setTextSize(14);
				tPaint.setAntiAlias(true);
				xT = 14; yT = y/2;
				
				/**************** Сетка *************************/
				lPaint = new Paint(); 
				lPaint.setColor(Color.YELLOW);					
				ptsH = new float[n*4];
				int o = 0;		
				for (int i = 1; i < n; i++) {
					int yy = y + (hS * i);
					ptsH[o++] = hS + hB; // x1
					ptsH[o++] = yy; // y1					
					ptsH[o++] = width - hS - hB; // x2
					ptsH[o++] = yy; //y2
				}
				y -= hB; // y-координата с учетом бордюра
				
				int x = width/2;
				ptsV = new float[8];
				o = 0;	
				for (int i = 1; i < 2; i++) {
					ptsV[o++] = x * i; // x1
					ptsV[o++] = y + hS + hB; // y1					
					ptsV[o++] = x * i; // x2
					ptsV[o++] = height - hB - hS; //y2
				}
				
				/**************** Поле информации ****************/
				iRect = new Rect();
				iRect.set(0, 0, width, y);//задаем координаты		
			  	iPaint = new Paint(); // кисть		
			  	iPaint.setColor(Color.BLUE);// цвет кисти	
			  	iPaint.setStyle(Paint.Style.FILL);// тип - заливка				
				/**************** Поле внутр. ********************/ 
				bRect = new Rect();
				bRect.set(hB, y + hB, width - hB, height - hB);//задаем координаты		
			  	bPaint = new Paint(); // кисть		
			  	bPaint.setColor(Color.GREEN);// цвет кисти	
			  	bPaint.setStyle(Paint.Style.FILL);// тип - заливка				
				
				/**************** Поле внешнее *******************/
				myRect = new Rect();
				myRect.set(0, y, width, height);//задаем координаты		
			  	paint = new Paint(); // кисть		
				paint.setColor(Color.RED);// цвет кисти	
				paint.setStyle(Paint.Style.FILL);// тип - заливка
				
				yS = y + hB; // y-координата поля
				
				/**************** Запуск змеи ****************/
				mSnake = new Snake7(100, yS);
				mSnake.setApple();
			}
		});	
	}
	
	float x, y;
	public boolean onTouch(View v, MotionEvent event) {
		x = (event.getActionMasked() == MotionEvent.ACTION_DOWN) ? event
				.getX() : x;
		y = (event.getActionMasked() == MotionEvent.ACTION_DOWN) ? event
				.getY() : y;
		setDir((event.getActionMasked() == MotionEvent.ACTION_UP) ? ((Math
				.abs(event.getX() - x) > Math.abs(event.getY() - y) && (event
				.getX() - x) > 0) ? 3
				: (((Math.abs(event.getX() - x) > Math.abs(event.getY() - y)) && (event
						.getX() - x) <= 0) ? 2
						: ((event.getY() - y) > 0 ? 1 : 0)))
				: GlobDir);
//		Log.d(TAG, x + "; " + y);
		return true;
	}
	
	public void setDir(int dir) {
		GlobDir = ((dir + GlobDir == 1) || (dir + GlobDir == 5)) ? GlobDir
				: dir;
	}
	
}
