package com.example.SnakeGame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

public class gameActivity extends Activity {
    Bitmap bitmap; DrawView drawView; MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);

        setContentView(drawView);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_omfg_hello);
        mediaPlayer.start();

        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.main_button);
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;
        Timer timer;

        Display display = getWindowManager().getDefaultDisplay();
        Paint paint;
        String text = "coordinates: ";
        float X = 0, Y = 0;
        int score = 0, level = 0, tick = 0;

        public int SCALE = 25;//32;
        public static final int WIDTH = 20;
        public static final int HEIGHT = 20;
        public static final int converselySPEED = 10;


        Apple apple = new Apple((int)(Math.random() * WIDTH), (int)(Math.random()*HEIGHT));
        Snake snake = new Snake(10, 10, 9, 10);

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
            paint = new Paint();
            timer = new Timer(); timer.schedule(task, 0, 40);//1000/SPEED); // запускаем таймер
            if(isScreenOrientationPORTRAIT()){
                SCALE = display.getWidth()/20;
            }
            else if(!isScreenOrientationPORTRAIT()){
                SCALE = (display.getHeight() - 120)/20;
            }
        }

        TimerTask task = new TimerTask()
        {
            public void run()
            {
                tick++;
                if(tick > (converselySPEED - level)) {
                    snake.move();
                    if(snake.isGameOver()){
                        mediaPlayer.stop();
                        timer.cancel();
                    }

                    if (snake.snakeX[0] == apple.positionX && snake.snakeY[0] == apple.positionY) {
                        apple.setRandomPosition();
                        snake.length++;
                        score++;
                        if (snake.length > 5) {
                            snake.length = 2;
                            level++;
                        }
                    }

                    for (int k = 1; k < snake.length; k++) { //проверка на наличие змеи при установке яблока
                        if (snake.snakeX[k] == apple.positionX && snake.snakeY[k] == apple.positionY)
                            apple.setRandomPosition();
                    }
                    tick = 0;
                }
            }
        };

        private boolean isScreenOrientationPORTRAIT(){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                return true;
            else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                return false;
            else
                return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:

                    if(snake.direction!=0 && x > 0 && x < display.getWidth()/4 && y > (display.getHeight()/4) - 30 && y < ((display.getHeight() - display.getHeight()/4) - 100)) //100, 30 - head
                        //text = "left: ";
                        snake.direction = 2;
                    else if(snake.direction != 2 && x > display.getWidth() - display.getWidth()/4 && x < display.getWidth() && y > (display.getHeight()/4) - 30 && y < ((display.getHeight() - display.getHeight()/4) - 100)) //100, 30 - head
                        //text = "right: ";
                        snake.direction = 0;
                    else if(snake.direction != 1 && x > display.getWidth()/4 && x < display.getWidth() - display.getWidth()/4 && y > 0 && y < (display.getHeight()/4) - 30)
                        //text = "up: ";
                        snake.direction = 3;
                    else if(snake.direction != 3 && x > display.getWidth()/4 && x < display.getWidth() - display.getWidth()/4 && y > ((display.getHeight() - display.getHeight()/4) - 200) && y < display.getHeight())
                        //text = "down: ";
                        snake.direction = 1;
                    else if(x > 10 && x < 110 && y > 526 && y < 564){
                        mediaPlayer.stop();
                        finish();
                    }
                        //text = "btnClick";
                    //else text = "";
                    X = x;//display.getWidth();
                    Y = y;//display.getHeight();
                    return true;
            }
            return false;
        }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

        class DrawThread extends Thread {

            private boolean running = false;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                while (running) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null) continue;

                        canvas.drawColor(Color.GREEN); //фон

                        paint.setColor(Color.BLACK); //кисть

                        canvas.drawBitmap(bitmap, 10, 526, paint);//(display.getHeight() - display.getHeight()/4 - 20), paint);

                        //------------------------------сетка-------------------------------//
                        for (int x = 0; x <= WIDTH * SCALE; x+= SCALE){
                            canvas.drawLine(x,0,x,WIDTH * SCALE, paint);
                        }

                        for (int y = 0; y <= HEIGHT * SCALE; y+= SCALE){
                            canvas.drawLine(0, y, HEIGHT * SCALE, y, paint);
                        }
                        //-----------------------------------------------------------------//

                        paint.setTextSize(20);
                        //canvas.drawText(text + Float.toString(X) + " / " + Float.toString(Y), 10, 25, paint);
                        canvas.drawText("Level: " + level, display.getWidth() - 150, (display.getHeight() - display.getHeight()/4), paint);
                        canvas.drawText("Score: " + score, display.getWidth() - 150, (display.getHeight() - display.getHeight()/5), paint);

                        if(snake.isGameOver()) {
                            paint.setTextSize(38);
                            canvas.drawText("GAME OVER", display.getWidth()/4, display.getHeight() / 4, paint);
                            canvas.drawText("score: " + score, display.getWidth()/3, display.getHeight() / 3, paint);
                        }

                        //------------------------------snake-------------------------------//
                        paint.setColor(Color.BLUE);
                        for (int d = 0; d < snake.length; d++){
                            canvas.drawRect(snake.snakeX[d] * SCALE, snake.snakeY[d] * SCALE, (snake.snakeX[d] * SCALE) + SCALE, (snake.snakeY[d] * SCALE)+SCALE, paint);
                        }
                        //-----------------------------------------------------------------//

                        //---------------------------apple----------------------------------//
                        paint.setColor(Color.RED);
                        canvas.drawRect(apple.positionX * SCALE, apple.positionY * SCALE, (apple.positionX * SCALE) + SCALE, (apple.positionY * SCALE)+SCALE, paint);
                        //-----------------------------------------------------------------//

                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

    }
}