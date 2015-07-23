package com.example.snake5;

import java.util.ArrayList;

import android.util.Log;

public class Snake {
	String TAG = this.getClass().getSimpleName();
	
    // Константы направления
    public static final int DIR_UP = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_DOWN = 3;
    public static final int DIR_LEFT = 4;
    
    // Очки в игре
    public int mScore = 0;
 
    // Матрица - игровое поле    
    public static int mFieldX = 18; // ширина игрового поля
    public static int mFieldY = 30; // высота игрового поля    
    private int mField[][] = new int[mFieldX][mFieldY];
 
    // Сама змея - массив двумерных координат каждого сегмента
    private ArrayList<pos> mSnake = new ArrayList<pos>();
 
    // Текущее направление движения змеи
    int mDirection = Snake.DIR_RIGHT;
 
    // Параметр по которому определяется должна ли змейка расти
    int isGrowing = 0;    
    
    // класс определяющий позицию
    public class pos {
        int x, y;
        pos(int x, int y) {
            this.x = x; this.y = y;
        }
    }
	
    Snake() {
    	Log.d(TAG, "Snake() ");
        // очищаем игровое поле
        for (int i = 0; i < mFieldX; i++)
            for (int j = 0; j < mFieldY; j++) {
                mField[i][j] = 0;
            }
        // создаем змею
        mSnake.add(new pos(200, 200));        
        mField[2][2] = -1; // каждая клетка поля в которой находится змея отмечается как "-1"
        mSnake.add(new pos(200, 210));
        mField[2][3] = -1;
        mSnake.add(new pos(200, 220));
        mField[2][4] = -1;       
        addFruite(); // добавляем на игровое поле фрукт
    }
	
    // метод добавляет фрукт на игровое поле, код на поле "2"
    private void addFruite() {
        boolean par = false;
        while (!par) {
            int x = (int) (Math.random() * mFieldX);
            int y = (int) (Math.random() * mFieldY);
            if (mField[x][y] == 0) {
                mField[x][y] = 2;
                par = true;
            }
        }
    }
    
 // Этот метод содержит в себе всю логику игры
    // здесь описываются все действия которые должны происходить
    // при каждом перемещении змеи, при этом, учитывается текущее направление и
    // проверяется, может ли змея ходить в указанном направлении
    // собственно вся игровая логика заключена в этом методе
    public boolean nextMove() {
        // смотрим, куда у нас направлена змея сейчас
        switch (this.mDirection) {
        // если на север
        case DIR_UP: {
            // тогда рассчитываем координаты в которые попадет
            // голова змеи на следуюзщем ходу
            int nextX = mSnake.get(mSnake.size() - 1).x;
            int nextY = mSnake.get(mSnake.size() - 1).y - 1;
            // если мы не утыкаемся в верхнюю стенку
            // и если клетка куда мы идем пуста (о чем нам говорит
            // нулевое значение в указанной клетке поля)
            if ((nextY >= 0) && mField[nextX][nextY] == 0) {
                // то мы проверяем, растет ли в данный момент змея
                if (isGrowing > 0) {
                    // если растет, уменьшаем запас роста и не
                    // двигаем хвост змеи
                    isGrowing--;
                } else {
                    // если не растет, то передвигаем хвост змеи
                    mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
                    mSnake.remove(0);
                }
                //Затем перемещаем голову змеи
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                // и на этом все закончилось - возвращаем истину
                return true;
            } else if ((nextY >= 0) && mField[nextX][nextY] == 1) {
                // если мы уткнулись в препятствие возвращаем ложь
                return false;
            } else if ((nextY >= 0) && mField[nextX][nextY] > 1) {
                // А вот если мы уткнулись во фрукт,                
                isGrowing = isGrowing + 2; // увеличиваем запас роста                
                mScore = mScore+10; // добавляем очков!
                // и переносим голову змеи на соответствующую клетку поля
                mField[nextX][nextY] = 0;
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;                
                addFruite(); // и добавляем на поле новый фрукт!
                return true;
            } else { // во всех остальных случаях возвращаем false              
                return false;
            }
        }
        // Здесь все то же самое, только для других направлений
        case DIR_RIGHT: {
            int nextX = mSnake.get(mSnake.size() - 1).x + 1;
            int nextY = mSnake.get(mSnake.size() - 1).y;
            if ((nextX < mFieldX) && mField[nextX][nextY] == 0) {
                if (isGrowing > 0) {
                    isGrowing--;
                } else {
                    mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
                    mSnake.remove(0);
                }
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                return true;
            } else if ((nextX < mFieldX) && mField[nextX][nextY] == 1) {
                return false;
            } else if ((nextX < mFieldX) && mField[nextX][nextY] > 1) {
                isGrowing = isGrowing + 2;
                mScore=mScore+10;
                mField[nextX][nextY] = 0;
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                addFruite();
                return true;
            } else {
                return false;
            }
        }
        case DIR_DOWN: {
            int nextX = mSnake.get(mSnake.size() - 1).x;
            int nextY = mSnake.get(mSnake.size() - 1).y + 1;
            if ((nextX < mFieldX) && mField[nextX][nextY] == 0) {
                if (isGrowing > 0) {
                    isGrowing--;
                } else {
                    mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
                    mSnake.remove(0);
                }
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                return true;
            } else if ((nextX < mFieldX) && mField[nextX][nextY] == 1) {
                return false;
            } else if ((nextX < mFieldX) && mField[nextX][nextY] > 1) {
                isGrowing = isGrowing + 2;
                mScore=mScore+10;
                mField[nextX][nextY] = 0;
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                addFruite();
                return true;
            } else {
                return false;
            }
        }
        case DIR_LEFT: {
            int nextX = mSnake.get(mSnake.size() - 1).x - 1;
            int nextY = mSnake.get(mSnake.size() - 1).y;
            if ((nextX >= 0) && mField[nextX][nextY] == 0) {
                if (isGrowing > 0) {
                    isGrowing--;
                } else {
                    mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
                    mSnake.remove(0);
                }
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                return true;
            } else if ((nextX >= 0) && mField[nextX][nextY] == 1) {
                return false;
            } else if ((nextX >= 0) && mField[nextX][nextY] > 1) {
                isGrowing = isGrowing + 2;
                mScore=mScore+10;
                mField[nextX][nextY] = 0;
                mSnake.add(new pos(nextX, nextY));
                mField[nextX][nextY] = -1;
                addFruite();
                return true;
            } else {
                return false;
            }
        }
        }
        return false;
    }
    
    public int getDirection() {
        return mDirection;
    }
 
    public void clearScore(){
        this.mScore=0;
    }
 
    public void setDirection(int direction) {
        this.mDirection = direction;
    }
 
    public int[][] getmField() {
        return mField;
    }
 
    public int getSnakeLength() {
        return  mSnake.size();
    }
 
    public ArrayList<pos> getmSnake() {
        return mSnake;
    }
    
}
