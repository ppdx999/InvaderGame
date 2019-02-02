package com.example.fujis.invadergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Button {
    //    get view size
    private float view_w, view_h;
//    others
    Context context;
//    Button
    private AttackButton attackButton;
    private MoveButton rightButton,leftButton;

    public Button(float view_w, float view_h, Context context) {
        this.view_w = view_w;
        this.view_h = view_h;
        this.context=context;
        attackButton=new AttackButton(view_w/6,view_h*21/22,view_w);
        rightButton=new MoveButton(view_w*6/8,view_h*10/11,view_w,context,R.drawable.rightbutton);
        leftButton=new MoveButton(view_w/2,view_h*10/11,view_w,context,R.drawable.leftbutton);
    }
    public void draw(Canvas canvas,Paint paint){
        attackButton.draw(canvas,paint);
        rightButton.draw(canvas,paint);
        leftButton.draw(canvas,paint);
    }
//    getting button rect
    public Rect getRectOfAttackButton() {
        return attackButton.Rect;
    }
    public Rect getRectOfRightButton() {
        return rightButton.destRect;
    }
    public Rect getRectOfLeftButton() {
        return leftButton.destRect;
    }
//    inner class which fix a shape of button
    private class AttackButton {
        private float x,y,LENGTH_OF_A_RADIUS;
        private Rect Rect;

        private AttackButton(float x, float y, float width) {
            this.x = x;
            this.y = y;
            this.LENGTH_OF_A_RADIUS = 3 * width / 50;
            this.Rect = new Rect((int)(x - LENGTH_OF_A_RADIUS), (int)(y - LENGTH_OF_A_RADIUS), (int)(x + LENGTH_OF_A_RADIUS), (int)(y + LENGTH_OF_A_RADIUS));
        }
        private void draw(Canvas canvas, Paint paint) {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, LENGTH_OF_A_RADIUS, paint);
        }
    }
    private class MoveButton {
        private float LENGTH_OF_A_SIDE;
        private Bitmap bitmap;
        private Rect srcRect, destRect;

        private MoveButton(float left, float top, float width, Context context,int resourceId) {
            this.LENGTH_OF_A_SIDE = 8 * width / 50;
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            destRect = new Rect((int)left, (int)top, (int)(left + LENGTH_OF_A_SIDE), (int)(top + LENGTH_OF_A_SIDE));
        }
        private void draw(Canvas canvas, Paint paint) {
            canvas.drawBitmap(bitmap, srcRect, destRect, paint);
        }
    }
}


