package com.example.snake5;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends Activity  {
	static int height; 
	static int width;
	GameView6 game6;
	GameView7 game7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		
		super.onCreate(savedInstanceState);

		int gameVer = 7;

		switch (gameVer) {
			case 1: this.setContentView(new GameSurface(this)); break; 
			case 2: this.setContentView(new GameView2(this)); break; 			
			case 3: this.setContentView(new GameView3(this)); break; 	
			case 4: this.setContentView(new GameView4(this)); break; 
			case 5: this.setContentView(new GameView5(this)); break; 
			case 6: 
				game6 = new GameView6(this);
				this.setContentView(game6); break; 
			case 7: 
				game7 = new GameView7(this);
				this.setContentView(game7); break; 
		}
	}
	
	@Override
	protected void onPause() {
//		game.stopRunning();
		super.onPause();	
//		gameLoopThread.start();
	}
	
	@Override
	protected void onResume() {
//		game.stopRunning();
		super.onPause();	
//		gameLoopThread.start();
	}
}
