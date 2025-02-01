package com.lbo.book.gamebasicmultitouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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


        switch(action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();
                setTouchText("POINTER_ID:" + pointerId + ",ACTION_DOWN:" + x + "," + y);
                break;
            }
            case MotionEvent.ACTION_UP: {
                final float x = event.getX();
                final float y = event.getY();
                setTouchText("POINTER_ID:" + pointerId + ",ACTION_UP:" + x + "," + y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float x = event.getX();
                final float y = event.getY();
                setTouchText("POINTER_ID:" + pointerId + ",ACTION_MOVE:" + x + "," + y);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                final float x = event.getX();
                final float y = event.getY();
                setTouchText("POINTER_ID:" + pointerId + ",ACTION_CANCEL:" + x + "," + y);
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                final int pointerIndex =
                        (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerIndexId = event.getPointerId(pointerIndex);
                try{
                    final int x = (int)event.getX(pointerIndexId);
                    final int y = (int)event.getY(pointerIndexId);
                    setTouchText("POINTER_ID:" + pointerId + ",ACTION_POINTER_DOWN:" + x + "," + y);
                }catch(Exception exTouch){}
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                final int pointerIndex =
                        (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerIndexId = event.getPointerId(pointerIndex);
                try{
                    final int x = (int)event.getX(pointerIndexId);
                    final int y = (int)event.getY(pointerIndexId);
                    setTouchText("POINTER_ID:" + pointerId + ",ACTION_POINTER_UP:" + x + "," + y);
                }catch(Exception exTouch){}
                break;
            }
        }
        return true;
    }


}
