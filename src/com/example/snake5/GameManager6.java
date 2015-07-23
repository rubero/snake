package com.example.snake5;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;

public class GameManager6 extends Thread {
	/** Объект класса */
	private GameView6 view;
	int speed = 600; // период движения
	
	/** Переменная задавания состояния потока рисования */
	boolean running = false;

	/** Конструктор класса */
	public GameManager6(GameView6 view) {
		this.view = view;
	}

	/** Задание состояния потока */
	public void setRunning(boolean run) {
		running = run;
	}

	/** Действия, выполняемые в потоке */
	@SuppressLint("WrongCall")
	public void run() {
		while (running) {
			Canvas canvas = null;			
			try {
				Thread.sleep(speed);
				canvas = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(canvas);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					view.getHolder().unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}