package com.example.fujis.invadergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;

public class Player {
//    LEVEL
    private int LEVEL;
//   reference point of machine of the player
    private float left,top,WIDTH_OF_PLAYER,HEIGHT_OF_PLAYER;
//    length of side of players machine
    private float LENGTH_OF_A_SIDE;
//    length of window view
    private float view_w,view_h;
//    length of machine of moving
    private final int MOVE_LENGTH=15;
//    life points of players
    private int hp;
//    boolean for dead or alive
    private boolean alive;
//    usedByBullet
    private List<Integer> listOfPositionOfBulletFromPlayer;
    private final int MOVE_LENGTH_OF_BULLET=20;

    public Player(float left,float top,float width,float height,int LEVEL){
        this.view_w=width;
        this.view_h=height;
        this.left=left;
        this.top=top;
        this.LEVEL=LEVEL;
        switch (LEVEL){
            case 1:
                this.LENGTH_OF_A_SIDE=width/20;
                hp=5;
                break;
            case 2:
                this.LENGTH_OF_A_SIDE=width/20;
                hp=5;
                break;
            case 3:
                this.LENGTH_OF_A_SIDE=width/18;
                hp=3;
                break;
            case 4:
                this.LENGTH_OF_A_SIDE=width/18;
                hp=3;
                break;

        }
        this.WIDTH_OF_PLAYER=this.LENGTH_OF_A_SIDE*3;
        this.HEIGHT_OF_PLAYER=this.LENGTH_OF_A_SIDE*2;
        alive=true;
        listOfPositionOfBulletFromPlayer=new ArrayList<Integer>();
    }
//  moving machine
    public void moveLeft(){
        if(left>MOVE_LENGTH){
            left=left-MOVE_LENGTH;
        }
    }
    public void moveRight(){
        if(left+WIDTH_OF_PLAYER<view_w-MOVE_LENGTH){
            left=left+MOVE_LENGTH;
        }
    }
//    fire a cannon
    public void attack(){
        listOfPositionOfBulletFromPlayer.add((int)(left+WIDTH_OF_PLAYER/2));
        listOfPositionOfBulletFromPlayer.add((int)top);
    }

//  when a player get damage, this method is used
    public void getDamage(int damagePoint){
        hp-=damagePoint;
        if(hp==0){
            alive=false;
        }
    }

    //    used for drawing
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.YELLOW);
        canvas.drawRect(left,top+LENGTH_OF_A_SIDE,left+LENGTH_OF_A_SIDE,top+2*LENGTH_OF_A_SIDE,paint);
        canvas.drawRect(left+LENGTH_OF_A_SIDE,top,left+2*LENGTH_OF_A_SIDE,top+LENGTH_OF_A_SIDE,paint);
        canvas.drawRect(left+LENGTH_OF_A_SIDE,top+LENGTH_OF_A_SIDE,left+2*LENGTH_OF_A_SIDE,top+2*LENGTH_OF_A_SIDE,paint);
        canvas.drawRect(left+2*LENGTH_OF_A_SIDE,top+LENGTH_OF_A_SIDE,left+3*LENGTH_OF_A_SIDE,top+2*LENGTH_OF_A_SIDE,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("自機のHP "+String.valueOf(this.hp),15,view_h*57/66,paint);
    }
///////////////////////BULLET/////////////////////////////////////
    public void drawBullet(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        for(int i=0;i<listOfPositionOfBulletFromPlayer.size();i=i+2){
            canvas.drawCircle((float)listOfPositionOfBulletFromPlayer.get(i),(float)listOfPositionOfBulletFromPlayer.get(i+1),15,paint);
        }
    }
    public void moveAndRemoveBullet(){
        for (int i=0;i<listOfPositionOfBulletFromPlayer.size();i=i+2) {
            if (this.listOfPositionOfBulletFromPlayer.get(i+1) > 0) {
                listOfPositionOfBulletFromPlayer.set(i+1,listOfPositionOfBulletFromPlayer.get(i+1)-MOVE_LENGTH_OF_BULLET);
            }else{
                listOfPositionOfBulletFromPlayer.remove(i);
                listOfPositionOfBulletFromPlayer.remove(i);
            }
        }
    }
//    getter

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getWIDTH_OF_PLAYER() {
        return WIDTH_OF_PLAYER;
    }

    public float getHEIGHT_OF_PLAYER() {
        return HEIGHT_OF_PLAYER;
    }
    public boolean getAlive(){
        return this.alive;
    }

    public int getHp() {
        return hp;
    }

    public float getView_w() {
        return view_w;
    }

    public float getView_h() {
        return view_h;
    }

    public List<Integer> getListOfPositionOfBulletFromPlayer() {
        return listOfPositionOfBulletFromPlayer;
    }
}
