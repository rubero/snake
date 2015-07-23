package com.example.snake5;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

public class GameSurface extends View {
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private float canvasSize;
	private Paint paint, mBitmapPaint;

    //переводим dp в пиксели
    public float convertDpToPixel(float dp,Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi/160f);
    }
	
	public GameSurface(Context context) {
		super(context);
		// размер игрового поля
		int horizontalCountOfCells = 10;
		int verticalCountOfCells = 10;		
		
		canvasSize = (int) convertDpToPixel(300, context);
		mBitmap = Bitmap.createBitmap((int) canvasSize, (int) canvasSize,
				Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		
		// определяем параметры кисти, которой будем рисовать сетку и атомы
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(5f);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		
		// рисуем сетку
		for (int x = 0; x < horizontalCountOfCells + 1; x++)
			mCanvas.drawLine((float) x * canvasSize / horizontalCountOfCells,
					0, (float) x * canvasSize / horizontalCountOfCells,
					canvasSize, paint);
		for (int y = 0; y < verticalCountOfCells + 1; y++)
			mCanvas.drawLine(0, (float) y * canvasSize / verticalCountOfCells,
					canvasSize, (float) y * canvasSize / verticalCountOfCells,
					paint);
	} 
	
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    }
}