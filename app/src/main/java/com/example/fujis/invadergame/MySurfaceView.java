package com.example.fujis.invadergame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private AppCompatActivity activity;
    private Context context;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder holder=null;
    private MyThread myThread;
    private Intent intent;
    private Bundle bundle;
    private int LEVEL;
//   objects
    private Player player;
    private Enemy enemy;
    private Button button;
//    width and height of view
    private float view_w,view_h;
//    use for time count
    private long t1=0,t2=0;


//    public MySurfaceView(Context context){
//        super(context);
//        this.context=context;
//        init();
//    }

    public MySurfaceView(Context context, AppCompatActivity activity) {
        super(context);
        this.context = context;
        this.activity=activity;
        init();
    }

    private void init(){
        holder=getHolder();
        holder.addCallback(this);
        paint=new Paint();
        canvas=new Canvas();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        view_w=getWidth();
        view_h=getHeight();
        LEVEL=activity.getIntent().getExtras().getInt("LEVEL");
        player=new Player(view_w/2,view_h*3/4,view_w,view_h,LEVEL);
        enemy=new Enemy(view_w/4,view_h/8,view_w,view_h,context,LEVEL);
        button=new Button(view_w,view_h,context);
        myThread=new MyThread(canvas,paint,holder,activity,player,enemy,button);
        myThread.ThreadStarting();
        t1=System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        view_w=width;
        view_h=height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myThread.ThreadDestroying();
    }
//    actions when button is touched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(player.getAlive()) {
            if (tapButton(event, button.getRectOfLeftButton())) {
                player.moveLeft();
            }
            if (tapButton(event, button.getRectOfRightButton())) {
                player.moveRight();
            }
            if (tapButton(event, button.getRectOfAttackButton())) {
                if(System.currentTimeMillis()-t1>200) {
                    player.attack();
                    t1=System.currentTimeMillis();
                }
            }
        }
        return true;
    }
//    used in onTouchEvent method
    private boolean tapButton(MotionEvent event,Rect rect){
        if (event.getX()>rect.left&&event.getX()<rect.right&&event.getY()>rect.top&&event.getY()<rect.bottom){
            return true;
        }else {
            return false;
        }

    }
}
