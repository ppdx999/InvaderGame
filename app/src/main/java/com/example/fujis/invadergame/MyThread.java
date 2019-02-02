package com.example.fujis.invadergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import java.util.Random;

public class MyThread implements Runnable {
    private Thread thread=null;
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder holder;
    private AppCompatActivity activity;
//    objects
    private Player player;
    private Enemy enemy;
    private Button button;
    private Collision collision;
//
    private Random random;
    private int randomValue;
//    use for stop thread
    private long t1=0,t2=0;
    private long longForPreventingSuccessiveDamage=0;
    private long longForPreventingSuccessiveDamageOfEnemy=0;

    public MyThread(Player player, Enemy enemy, Button button, Canvas canvas, Paint paint, SurfaceHolder holder){
        this.player=player;
        this.enemy=enemy;
        this.button=button;
        this.canvas=canvas;
        this.paint=paint;
        this.holder=holder;
        collision=new Collision(player,enemy);
        random=new Random();
        thread=new Thread(this);
    }

    public MyThread(Canvas canvas, Paint paint, SurfaceHolder holder,AppCompatActivity activity, Player player, Enemy enemy, Button button) {
        this.canvas = canvas;
        this.paint = paint;
        this.holder = holder;
        this.activity=activity;
        this.player = player;
        this.enemy = enemy;
        this.button = button;
        collision=new Collision(player,enemy);
        thread=new Thread(this);
    }

    public void ThreadStarting(){
        thread.start();
    }
    public void ThreadDestroying(){
        thread=null;
    }
    @Override
    public void run() {
        while (player.getAlive()&&enemy.getAlive()) {
            synchronized (this) {
//                stopping thread for a fixed time
                t1 = System.currentTimeMillis();
                enemy.actions();
                player.moveAndRemoveBullet();
                enemy.moveAndRemoveBullet();
                collision.collision();
                draw(holder);
                t2 = System.currentTimeMillis();
                if (t2 - t1 < 16) {
                    try {
                        thread.sleep(16 - (t2 - t1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        draw(holder);
        try {
            thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread=null;
        activity.finish();

    }
    private synchronized void draw(SurfaceHolder holder){
        canvas=holder.lockCanvas();
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        if(player.getAlive()&&enemy.getAlive()) {
            player.drawBullet(canvas,paint);
            button.draw(canvas,paint);
            if(!collision.isPlayerGettingDamagedAFewSecondsAgo()) {
                player.draw(canvas, paint);
            }else{
                if(System.currentTimeMillis()-longForPreventingSuccessiveDamage>30){
                    player.draw(canvas,paint);
                    longForPreventingSuccessiveDamage=System.currentTimeMillis();
                }
            }

            enemy.drawBullet(canvas,paint);
            if(!collision.isEnemyGettingDamagedAFewSecondsAgo()) {
                enemy.draw(canvas, paint);
            }else{
                if(System.currentTimeMillis()-longForPreventingSuccessiveDamageOfEnemy>30){
                    enemy.draw(canvas,paint);
                    longForPreventingSuccessiveDamageOfEnemy=System.currentTimeMillis();
                }
            }
        }else if(!player.getAlive()){
            paint.setColor(Color.WHITE);
            paint.setTextSize(70);
            canvas.drawText("GAME OVER",canvas.getWidth()/3,canvas.getHeight()/2,paint);
        }else if(!enemy.getAlive()){
            paint.setColor(Color.WHITE);
            paint.setTextSize(70);
            canvas.drawText("COMPLETE",canvas.getWidth()/3,canvas.getHeight()/2,paint);
        }
        holder.unlockCanvasAndPost(canvas);
    }
}
