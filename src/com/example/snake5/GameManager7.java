package com.example.snake5;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;

public class GameManager7 extends Thread {
	/** ������ ������ */
	private GameView7 view;
	int speed = 600; // ������ ��������
	
	/** ���������� ��������� ��������� ������ ��������� */
	boolean running = false;

	/** ����������� ������ */
	public GameManager7(GameView7 view) {
		this.view = view;
	}

	/** ������� ��������� ������ */
	public void setRunning(boolean run) {
		running = run;
	}

	/** ��������, ����������� � ������ */
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