package com.beshoykamal.theflayingbird;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class FlyingBirdView extends View {

    ObjectAnimator objectAnimator;

    private Bitmap bird[]= new Bitmap[2];
    private Bitmap backgroundIMG;
    private Paint scorepaint =new Paint();
    private Bitmap life[]=new Bitmap[2];

    private int birdX = 10;
    private int birdY;
    private int birdspeed,convaswidth,convashight;

    int score , lifeCountOfBird,j ;

    private int yellowX,yellowY,yellowSpeed = 12;
    private Paint yellowPaint = new Paint();

    private int greenX,greenY,greenSpeed = 16;
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed = 20;
    private Paint redPaint = new Paint();

    private boolean touch = false ;

    MediaPlayer flying,get1,wrong, amazzing,good;



    public FlyingBirdView(Context context) {
        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(),R.drawable.sizsmallfly);
        bird[1] = BitmapFactory.decodeResource(getResources(),R.drawable.sizsmallflyb);

        backgroundIMG = BitmapFactory.decodeResource(getResources(),R.drawable.porti1);


        ///// media
        flying = MediaPlayer.create(getContext(), R.raw.jump);
        get1 = MediaPlayer.create(getContext(), R.raw.click);
        wrong = MediaPlayer.create(getContext(), R.raw.wrongs);
        amazzing = MediaPlayer.create(getContext(), R.raw.tada);
        good = MediaPlayer.create(getContext(), R.raw.gameover);

        //// color paint
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        //// color text
        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(70);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);

        life[0]= BitmapFactory.decodeResource(getResources(),R.drawable.heartimgh);
        life[1]= BitmapFactory.decodeResource(getResources(),R.drawable.heartimgg);

        birdY = 550;
        score =0;
        lifeCountOfBird = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(bird,0,0,null);

        convaswidth= canvas.getWidth();
        convashight= canvas.getHeight();

        canvas.drawBitmap(backgroundIMG,0,0,null);

        int minbirdY = bird[0].getHeight();
        int maxbirdY = convashight  - bird[0].getHeight()  * 3;
        birdY= birdY + birdspeed;

        if (birdY < minbirdY){
            birdY = minbirdY;
        }
        if (birdY > maxbirdY){
            birdY = maxbirdY;
        }
        birdspeed = birdspeed + 2;
        if (touch){
            canvas.drawBitmap(bird[1],birdX,birdY,null);
            touch=false;
        }else
            canvas.drawBitmap(bird[0],birdX,birdY,null);


        yellowX = yellowX - yellowSpeed;
        if (hitballChecker(yellowX , yellowY))
        {
            get1.start();
            score = score + 10 ;
            yellowX = -100 ;
        }

        if (yellowX < 0){
            yellowX = convaswidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxbirdY - minbirdY) + minbirdY);
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);


        greenX = greenX - greenSpeed;
        if (hitballChecker(greenX , greenY))
        {
            get1.start();
            score = score + 20 ;
            greenX = -100 ;
        }

        if (greenX < 0){
            greenX = convaswidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxbirdY - minbirdY) + minbirdY);
        }
        canvas.drawCircle(greenX,greenY,25,greenPaint);

        redX = redX - redSpeed;
        if (hitballChecker(redX , redY))
        {
            wrong.start();
            redX = -100 ;
            lifeCountOfBird--;
            if (lifeCountOfBird==0){
                Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                good.start();
                Intent in = new Intent(getContext(),GameOverActivity.class);
                in.putExtra("score",score);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(in);
            }
        }


        if (redX < 0){
            redX = convaswidth + 21;
            redY = (int) Math.floor(Math.random() * (maxbirdY - minbirdY) + minbirdY);
        }
        canvas.drawCircle(redX,redY,30,redPaint);


        canvas.drawText("Score : "+score,30,150,scorepaint);

        for (int i = 0; i < 3 ; i++) {
            int x = (int) (500 + life[0].getWidth() * 0.6 * i);
            int y = 30;
            if (i < lifeCountOfBird){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
                canvas.drawBitmap(life[1],x,y,null);
        }


//////////////////////////////////  change color and speed
        if (score==5000|score==5010){
            amazzing.start();

            Toast.makeText(getContext(), "AMAZING\nYOU ARE AWESOME\nGOT 500 POINT ", Toast.LENGTH_LONG).show();

            score = score+500;

        }

        if ( score==100 | score==110 | score==300 | score==310
                | score==600 | score==610| score==900 | score==910
                | score==1200 | score==1210| score==1500 | score==1510
                | score==1800 | score==1810| score==2000 | score==2010
                | score==2300 | score==2310| score==3000 | score==3010
        | score==3500 | score==3510| score==4000 | score==4010) {
            score = score+20;
            changeColorANDSpeed();
            lifeCountOfBird++;
        }
//        Integer u = 1;
//        j = 100 * u++;
//        if ( score == 105||score==110 || score== l.length ||score == l.length + 5||score == l.length + 10) {

    }

    private void changeColorANDSpeed() {

        String[] H={"LEVEL 2","LEVEL 3","LEVEL 4","LEVEL 5","LEVEL 6","LEVEL 7","LEVEL 8","LEVEL 9","FINAL SPEED","FINAL SPEED"};

        int[] C={Color.MAGENTA,Color.GRAY,Color.YELLOW,Color.BLUE,Color.BLACK,Color.WHITE,Color.CYAN,Color.DKGRAY,Color.MAGENTA};
        int[] C2={Color.BLUE,Color.BLACK,Color.WHITE,Color.CYAN,Color.DKGRAY,Color.MAGENTA,Color.MAGENTA,Color.GRAY,Color.YELLOW};

        int[] Back={R.drawable.porti2,R.drawable.porti5,R.drawable.porti7,R.drawable.porti8,R.drawable.backe
                ,R.drawable.backf ,R.drawable.backd,R.drawable.porti3,R.drawable.backcircle};


        amazzing.start();

        if (j==9)
            j--;
        backgroundIMG = BitmapFactory.decodeResource(getResources(),Back[j]);
            yellowPaint.setColor(C[j]);
            yellowPaint.setAntiAlias(false);

        greenPaint.setColor(C2[j]);
        greenPaint.setAntiAlias(false);

                redSpeed=redSpeed+5;
            greenSpeed=greenSpeed+4;
            yellowSpeed=greenSpeed+5;

            Toast.makeText(getContext(), ""+H[j], Toast.LENGTH_SHORT).show();
        j++;
            return;


//        if (score>=200){
//            greenPaint.setColor(Color.MAGENTA);
//            greenPaint.setAntiAlias(false);
//            yellowSpeed=20;
//            greenSpeed=25;
//            redSpeed=30;
//        }
//        if (score>=120){
//            yellowPaint.setColor(Color.GRAY);
//            yellowPaint.setAntiAlias(false);
//
//            redSpeed=40;
//            greenSpeed=35;
//            yellowSpeed=30;
//        }
//
//        if (score>=400){
//            yellowPaint.setColor(Color.BLACK);
//            yellowPaint.setAntiAlias(false);
//
//        }

        }

    public boolean hitballChecker(int x , int y){

        if (birdX < x && x < (birdX + bird[0].getWidth()) && birdY < y && y < (birdY + bird[0].getHeight()) )
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN){

            touch = true;
            birdspeed = -22;
            flying.start();
        }
        return true;
    }
}
