package com.example.snake5;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.View;

public class GameView4 extends View {
	private Paint mPaint;
	public Handler myHandler;

	public GameView4(Context context) {
		super(context);
		// Настройка шрифта
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(16);
		mPaint.setColor(Color.GREEN); // 0xFFFFFFFF
		mPaint.setStyle(Style.FILL);

		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 0) {
					invalidate(); // Принудительная перерисовка виджета
				}
			}
		};
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(100, 100, 110, 110, mPaint);
	}

	public void MyTimer() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myHandler.sendEmptyMessage(0);
			}
		});
		t.start();
	}
	
	

}
