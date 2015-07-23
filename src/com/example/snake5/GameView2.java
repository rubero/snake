package com.example.snake5;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class GameView2 extends View {
	String TAG = "GameView2";
	private Paint mPaint = new Paint();
	int width;
	int height;
	int cell_width;
	int top;
	
    @Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    // Определить ширину холста игры
	    width = MeasureSpec.getSize(widthMeasureSpec);
	    height = MeasureSpec.getSize(heightMeasureSpec);
	    Log.d(TAG, width + "x" + height);
		}
    
	public GameView2(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas){
	    super.onDraw(canvas);

	    // Ширина клетки
	    cell_width = width / 48; // 480x690
	    
	    // Отступы от края холста
	    int left = 20, right = 20, bottom = 20; 
	    top = 50;
	    
	    // Кол-во клеток в ширину и высоту
	    int countCellsX = width / cell_width - (2 * left / cell_width);
	    int countCellsY = height / cell_width - ((top + bottom) / cell_width);
	    
		// Закрашиваем холст
	    mPaint.setColor(Color.DKGRAY);
	    canvas.drawPaint(mPaint);
	    
	    // Рисуем текст
	    mPaint.setColor(Color.GRAY);  
	    mPaint.setStyle(Paint.Style.FILL); //стиль заливки
	    mPaint.setAntiAlias(true); // режим сглаживания
	    mPaint.setTextSize(16);
	    canvas.drawText("Счет:", left, top/2 + 16/2, mPaint);
	    
	    // Цвет линий
	    mPaint.setColor(Color.GRAY);
//	    mPaint.setStyle(Paint.Style.FILL);
	    // Линия вертикальная
	    for (int i = 0; i <= countCellsX; i++) {
	    	canvas.drawLine(
	    			left + cell_width * i, 
	    			top, 
	    			left + cell_width * i, 
	    			height - bottom, 
	    			mPaint);
	    }
	    
	    // Линия горизонтальная
	    for (int i = 0; i <= countCellsY; i++) {
	    	canvas.drawLine(
	    			left, 
	    			top + cell_width * i, 
	    			width - right, 
	    			top + cell_width*i, 
	    			mPaint);
	    }
	    
	    
	    // Рисуем змейку
	    int snake[][] = new int[5][3];
	    mPaint.setColor(Color.WHITE);
	    
	    for (int i = 0; i < snake.length; i++) {
	    	float y = (height + top)/2 - (cell_width * i);
	    	canvas.drawRect(
	    			width/2, 
	    			y, 
	    			width/2 + cell_width, 
	    			y + cell_width, 
	    			mPaint);
	    }
	    P(canvas);
	}
	
	public Timer timer;
	public int posMeat, GlobDir = 1, scores;
	public Random r = new Random(System.currentTimeMillis());

	public void P(final Canvas canvas) {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int p;
			public void run() {
				p++;
				
			    for (int i = 0; i < 5; i++) {
			    	float y = (height + top)/2 - (cell_width * i) - (cell_width * p);
			    	Log.d(TAG, ""+y);
			    	canvas.drawRect(
			    			width/2, 
			    			y, 
			    			width/2 + cell_width, 
			    			y + cell_width, 
			    			mPaint);
			    }
			    
			}
		}, 0, 500);
	}
}
