package com.example.fujis.invadergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {
//    LEVEL
    private int LEVEL;
    //    reference point of enemy
    private float left,top,WIDTH_OF_ENEMY,HEIGHT_OF_ENEMY,view_w,view_h;
    //    used by drawing players machine;
    private Bitmap bitmap;
    private Rect srcRect,destRect;
    //    length of machine of moving
    private int MOVE_LENGTH;
    //    life points of a enemy
    private int hp;
    //    boolean for dead or alive
    private boolean alive;
//    use for bullet
    private List<Integer> listOfPositionOfBulletFromEnemy;
    private int MOVE_LENGTH_OF_BULLET;
//    used in a thread
    private Random random;
    private int randomValue;
    private long controlHowOftenEnemyDoAction=0;

    public Enemy(float left, float top, float width, float height, Context context,int LEVEL){
        this.view_w=width;
        this.view_h=height;
        this.left=left;
        this.top=top;
        this.LEVEL=LEVEL;
        switch (LEVEL){
            case 1:
                this.WIDTH_OF_ENEMY=view_w/50*30;
                hp=5;
                this.MOVE_LENGTH=50;
                this.MOVE_LENGTH_OF_BULLET=20;
                break;
            case 2:
                this.WIDTH_OF_ENEMY=view_w/50*20;
                hp=10;
                this.MOVE_LENGTH=60;
                this.MOVE_LENGTH_OF_BULLET=20;
                break;
            case 3:
                this.WIDTH_OF_ENEMY=view_w/50*15;
                hp=10;
                this.MOVE_LENGTH=70;
                this.MOVE_LENGTH_OF_BULLET=30;
                break;
            case 4:
                this.WIDTH_OF_ENEMY=view_w/50*15;
                hp=5;
                this.MOVE_LENGTH=80;
                this.MOVE_LENGTH_OF_BULLET=40;
                break;
        }
        this.HEIGHT_OF_ENEMY=view_w/50*15;
        this.alive=true;
        this.listOfPositionOfBulletFromEnemy=new ArrayList<Integer>();
        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy);
        srcRect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        destRect=new Rect((int)this.left,(int)this.top,(int)(left+WIDTH_OF_ENEMY),(int)(top+HEIGHT_OF_ENEMY));
        controlHowOftenEnemyDoAction=System.currentTimeMillis();
        this.random=new Random();
    }
    //  moving machine
    public void moveLeft(){
        if(left>MOVE_LENGTH){
            left=left-MOVE_LENGTH;
        }
    }
    public void moveRight(){
        if(left+WIDTH_OF_ENEMY<view_w-MOVE_LENGTH){
            left=left+MOVE_LENGTH;
        }
    }
//    fire a cannon
    public void attack(){
        if(LEVEL!=4) {
            listOfPositionOfBulletFromEnemy.add((int) ((left + WIDTH_OF_ENEMY + left) / 2));
            listOfPositionOfBulletFromEnemy.add((int) (top + HEIGHT_OF_ENEMY));
        }else if(LEVEL==4){
            randomValue=random.nextInt(3);
            switch (randomValue){
                case 0:
                    listOfPositionOfBulletFromEnemy.add((int) left+10);
                    break;
                case 1:
                    listOfPositionOfBulletFromEnemy.add((int) ((left + WIDTH_OF_ENEMY + left) / 2));
                    break;
                case 2:
                    listOfPositionOfBulletFromEnemy.add((int) (left + WIDTH_OF_ENEMY-10 ));
                    break;
            }
            listOfPositionOfBulletFromEnemy.add((int) (top + HEIGHT_OF_ENEMY));
        }

    }
    public void actions(){
        //                actions of enemy
        if (this.alive&&System.currentTimeMillis()-controlHowOftenEnemyDoAction>200) {
            randomValue = random.nextInt(5);
            switch (randomValue) {
                case 0:
                    moveLeft();
                    break;
                case 1:
                    moveRight();
                    break;
                case 2:
                    attack();
                    break;
                case 3:
                    moveLeft();
                    attack();
                    break;
                case 4:
                    moveRight();
                    attack();
                    break;
                case 5:
                    break;
            }
//                    control how often enemy do action
            controlHowOftenEnemyDoAction=System.currentTimeMillis();
        }
    }
    //  when a enemy get damage, this method is used
    public void getDamage(int damagePoint) {
        hp -= damagePoint;
        if (hp == 0) {
            alive = false;
        }
    }
    //    used for drawing
    public void draw(Canvas canvas, Paint paint){
        destRect.set((int)left,(int) top,(int)(left+WIDTH_OF_ENEMY),(int)(top+HEIGHT_OF_ENEMY));
        canvas.drawBitmap(bitmap,srcRect,destRect,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("敵のHP "+String.valueOf(hp),15,40,paint);
    }
    //    getter

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getWIDTH_OF_ENEMY() {
        return WIDTH_OF_ENEMY;
    }

    public float getHEIGHT_OF_ENEMY() {
        return HEIGHT_OF_ENEMY;
    }

    public int getHp() {
        return hp;
    }

    public boolean getAlive(){
        return this.alive;
    }
////////////////////////BULLET//////////////////////////////////
    public void drawBullet(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        for(int i=0;i<listOfPositionOfBulletFromEnemy.size()-1;i=i+2){
            canvas.drawCircle((float)listOfPositionOfBulletFromEnemy.get(i),(float)listOfPositionOfBulletFromEnemy.get(i+1),15,paint);
        }
    }
    public void moveAndRemoveBullet(){
        for (int i=0;i<listOfPositionOfBulletFromEnemy.size();i=i+2) {
            if (this.listOfPositionOfBulletFromEnemy.get(i+1) < view_h*9/10) {
                listOfPositionOfBulletFromEnemy.set(i+1,listOfPositionOfBulletFromEnemy.get(i+1)+MOVE_LENGTH_OF_BULLET);
            }else{
                listOfPositionOfBulletFromEnemy.remove(i);
                listOfPositionOfBulletFromEnemy.remove(i);
            }
        }
    }
//    getter
    public List<Integer> getListOfPositionOfBulletFromEnemy() {
        return listOfPositionOfBulletFromEnemy;
    }
}
