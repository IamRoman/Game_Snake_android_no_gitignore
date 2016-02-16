package com.example.SnakeGame;

import java.util.Calendar;

public class Snake {
    public int direction = 0;
    public int length = 2;

    public boolean isGameOver() {return gameOver;}
    boolean gameOver = false;


    public int snakeX[] = new int[gameActivity.DrawView.WIDTH * gameActivity.DrawView.HEIGHT];
    public int snakeY[] = new int[gameActivity.DrawView.WIDTH * gameActivity.DrawView.HEIGHT];


    public Snake(int x0, int y0, int x1, int y1){
        snakeX[0] = x0;
        snakeY[0] = y0;
        snakeX[1] = x1;
        snakeY[1] = y1;
    }

    public void move(){
        for(int d = length; d > 0; d--){
            snakeX[d] = snakeX[d-1];
            snakeY[d] = snakeY[d-1];
        }

        if(direction == 0) snakeX[0]++;
        if(direction == 1) snakeY[0]++;
        if(direction == 2) snakeX[0]--;
        if(direction == 3) snakeY[0]--;

        for(int d = length-1; d > 0; d--){
            if(snakeX[0] == snakeX[d] && snakeY[0] == snakeY[d])
                length = length/2;
        }

        if(snakeX[0] > gameActivity.DrawView.WIDTH - 1) gameOver = true;//snakeX[0] = 0;
        if(snakeX[0] < 0) gameOver = true;//snakeX[0] = gameActivity.DrawView.WIDTH;

        if(snakeY[0] > gameActivity.DrawView.HEIGHT -1) gameOver = true;//snakeY[0] = 0;
        if(snakeY[0] < 0) gameOver = true;//snakeY[0] = gameActivity.DrawView.HEIGHT;

        if(length < 2) length=2;
    }
}
