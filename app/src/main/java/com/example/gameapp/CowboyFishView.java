package com.example.gameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CowboyFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private int whiteX, whiteY, whiteSpeed = 16;
    private Paint whitePaint = new Paint();

    private int mangentaX, mangentaY, mangentaSpeed = 20;
    private Paint mangentaPaint = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();


    private int score, lifeCounterOfFish;

    private boolean touch = false;

    private Bitmap backroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];


    public CowboyFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);

        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.imagine);

        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(false);

        mangentaPaint.setColor(Color.MAGENTA);
        mangentaPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life [0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);

        life [1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;

        score = 0;
        lifeCounterOfFish = 4;


    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getWidth();

        canvas.drawBitmap(backroundImage, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishy = canvasHeight - fish[0].getHeight() * 4;

        fishY += fishSpeed;

        if(fishY < minFishY) {
            fishY = minFishY;
        }

        if(fishY > maxFishy) {
            fishY = maxFishy;
        }
        fishSpeed += 2;

        if(touch){
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else{
            canvas.drawBitmap(fish[0], fishX, fishY, null);

        }

        whiteX -= whiteSpeed;

        if(hitBallChecker(whiteX,whiteY)) {
            score += 20;
            whiteX = -100;
        }

        if(whiteX < 0) {
            whiteX = canvasWidth + 21;
            whiteY = (int) Math.floor(Math.random() * (maxFishy - minFishY)) + minFishY ;
        }
        canvas.drawCircle(whiteX, whiteY, 25, whitePaint);

        mangentaX -= mangentaSpeed;

        if(hitBallChecker(mangentaX, mangentaY)) {
            lifeCounterOfFish += 1;
            mangentaSpeed ++;
            whiteSpeed ++;
            redSpeed ++;
            mangentaX = -100;
        }

        if(mangentaX < 0) {
            mangentaX = canvasWidth + 21;
            mangentaY = (int) Math.floor(Math.random() * (maxFishy - minFishY)) + minFishY ;
        }
        canvas.drawCircle(mangentaX, mangentaY, 25, mangentaPaint);

        redX -= redSpeed;

        if(hitBallChecker(redX,redY)) {

            redX = -100;
            lifeCounterOfFish--;

            if(lifeCounterOfFish == 0) {
                Toast.makeText(getContext(), "Game Over:(", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("Scorul tau este de:", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if(redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishy - minFishY)) + minFishY ;
        }
        canvas.drawCircle(redX, redY, 25, redPaint);

        canvas.drawText("Scor: " + score, 20, 60, scorePaint);

        for(int i = 0; i < 4; i++){
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 40;

            if(i < lifeCounterOfFish){
                canvas.drawBitmap(life[0], x, y, null);
            } else{
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

    }

    public boolean hitBallChecker(int x, int y){
        if(fishX < x && x < (fishX + fish[0].getWidth())  && fishY < y && y < (fishY + fish[0].getHeight())){
            return true;
        }
        return false;
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishSpeed = -22;

        }
        return  true;
    }
}
