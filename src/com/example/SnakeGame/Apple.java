package com.example.SnakeGame;

public class Apple {
    public int positionX;
    public int positionY;

    public Apple(){
        this.positionX = 0;
        this.positionY = 0;
    }

    public Apple(int startX, int startY){
        this.positionX = startX;
        this.positionY = startY;
    }

    public void setRandomPosition(){
        positionX = (int) (Math.random()* gameActivity.DrawView.WIDTH);
        positionY = (int) (Math.random()* gameActivity.DrawView.HEIGHT);
    }
}
