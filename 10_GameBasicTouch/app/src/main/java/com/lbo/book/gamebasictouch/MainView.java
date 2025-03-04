package com.lbo.book.gamebasictouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tomcat2 on 2015-05-31.
 */
public class MainView extends View {

    String[] strTouchEvent = new String[30];

    public MainView(Context context) {
        super(context);

    }
    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(36);
        paint.setColor(Color.BLACK);
        for(int i=0; i< 30; i++){
            if(strTouchEvent[i] != null) {
                canvas.drawText(strTouchEvent[i], 10, i* 36, paint);
            }
        }
    }
    private void setTouchText(String str){
        for(int i=29; i> 0; i--){
            strTouchEvent[i] = strTouchEvent[i-1];
        }
        strTouchEvent[0] = str;
        invalidate();
    }
    int pointerId = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        final int action = event.getAction();
        pointerId = event.getPointerId(0);
        final float x = event.getX();
        final float y = event.getY();

        switch(action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                setTouchText("ACTION_DOWN:" + x + "," + y + ":" + pointerId);
                break;
            case MotionEvent.ACTION_UP:
                setTouchText("ACTION_UP:" + x + "," + y + ":" + pointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                setTouchText("ACTION_MOVE:" + x + "," + y + ":" + pointerId);
                break;
            case MotionEvent.ACTION_CANCEL:
                setTouchText("ACTION_CANCEL:" + x + "," + y + ":" + pointerId);
                break;
        }
        return true;
    }


}
