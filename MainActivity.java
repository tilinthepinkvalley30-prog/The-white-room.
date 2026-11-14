package com.whiteroom.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(new RoomView());
    }
    class RoomView extends View {
        Paint p = new Paint();
        float playerX=180, direction=0, joyX=0, joyY=0, scale, ox, oy;
        boolean dragging=false;
        RoomView(){super(MainActivity.this);p.setAntiAlias(false);}
        void rect(Canvas c,int col,float x,float y,float w,float h){
            p.setColor(col);p.setShader(null);c.drawRect(x,y,x+w,y+h,p);
        }
        @Override protected void onDraw(Canvas real){
            super.onDraw(real);
            scale=Math.min(getWidth()/360f,getHeight()/640f);
            ox=(getWidth()-360*scale)/2;oy=(getHeight()-640*scale)/2;
            real.drawColor(0xffbdbdbd);
            real.save();real.translate(ox,oy);real.scale(scale,scale);
            rect(real,0xffdddddd,0,0,360,640);
            p.setShader(new LinearGradient(0,0,0,520,0xffeeeeee,0xffcccccc,Shader.TileMode.CLAMP));
            real.drawRect(0,0,360,520,p);p.setShader(null);
            rect(real,0xffc2c2c2,0,520,360,120);
            rect(real,0xffaaaaaa,0,516,360,4);
            rect(real,0xffd0d0d0,18,0,2,516);rect(real,0xffd0d0d0,340,0,2,516);
            rect(real,0xffb8b8b8,0,558,360,1);rect(real,0xffb8b8b8,0,602,360,1);
            p.setColor(0x44777777);real.drawOval(playerX-19,515,playerX+19,525,p);
            rect(real,0xfff8f8f8,playerX-9,472,18,35);
            rect(real,Color.WHITE,playerX-11,470,22,4);
            rect(real,0xfff5f5f5,playerX-8,448,16,18);
            rect(real,0xffeeeeee,playerX-10,451,2,12);rect(real,0xffeeeeee,playerX+8,451,2,12);
            rect(real,0xff9e2020,playerX-6,455,12,2);
            rect(real,0xff9e2020,playerX-6,461,3,2);rect(real,0xff9e2020,playerX+3,461,3,2);
            rect(real,0xff222222,playerX-15,476,5,25);rect(real,0xff222222,playerX+10,476,5,25);
            rect(real,0xff222222,playerX-7,506,5,12);rect(real,0xff222222,playerX+2,506,5,12);
            p.setColor(0x55777777);real.drawCircle(66,505,52,p);
            p.setColor(0xff888888);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);real.drawCircle(66,505,52,p);p.setStyle(Paint.Style.FILL);
            p.setColor(0xff777777);real.drawCircle(66+joyX,505+joyY,18,p);
            p.setColor(0xffeeeeee);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);real.drawCircle(66+joyX,505+joyY,18,p);p.setStyle(Paint.Style.FILL);
            real.restore();
            playerX=Math.max(18,Math.min(342,playerX+direction*2.0f));
            postInvalidateDelayed(16);
        }
        @Override public boolean onTouchEvent(MotionEvent e){
            float x=(e.getX()-ox)/scale,y=(e.getY()-oy)/scale;
            switch(e.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if(dragging||(x<140&&y>430)){
                        dragging=true;float dx=x-66,dy=y-505,d=(float)Math.hypot(dx,dy),max=30;
                        if(d>max){dx=dx/d*max;dy=dy/d*max;}
                        joyX=dx;joyY=dy;direction=Math.max(-1,Math.min(1,dx/max));
                    }return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    dragging=false;joyX=0;joyY=0;direction=0;return true;
            }return true;
        }
    }
}
