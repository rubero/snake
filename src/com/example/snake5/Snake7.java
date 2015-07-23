package com.example.snake5;

import java.util.ArrayList;
import java.util.Random;

import com.example.snake5.Snake6.pos;

import android.util.Log;

public class Snake7 {
	String TAG = this.getClass().getSimpleName();
	
    // Константы направления
    public static final int DIR_UP = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_DOWN = 3;
    public static final int DIR_LEFT = 4;    
    
    // Сама змея - массив двумерных координат каждого сегмента
    private ArrayList<pos> mSnake = new ArrayList<pos>();
 
    // Текущее направление движения змеи
    int mDirection = DIR_RIGHT;
    
	private static final Random RNG = new Random(); // 
    
    // Класс определяющий позицию
    public class pos {
        int x, y;
        pos(int x, int y) {
            this.x = x; this.y = y;
        }
    }    
	
    Snake7(int oX, int oY) { // начальная позиция
        mSnake.add(new pos(oX, oY));
        mSnake.add(new pos(oX, oY));
        mSnake.add(new pos(oX, oY));
    } 
    
    float yS = GameView7.yS;
    int appleX, appleY;
    void setApple() {
    	
    }
    
    /** Присвоить элементу впередиидущую позицию */
    void setTile() { 

    }
   
    
//    int cWidth = GameView7.cWidth;
//    int width = GameView6.width;
//    int height = GameView6.heightReal;
//    int top = GameView6.top;
    
    /** Расчет движения */
    public boolean thisMove() {
    	
		return false;
    }
    
    public int getDirection() {
        return mDirection;
    }
 
    public void setDirection(int direction) {
        this.mDirection = direction;
    }
 
    public int getSnakeLength() {
        return mSnake.size();
    }
 
    public ArrayList<pos> getmSnake() {
        return mSnake;
    }
}
