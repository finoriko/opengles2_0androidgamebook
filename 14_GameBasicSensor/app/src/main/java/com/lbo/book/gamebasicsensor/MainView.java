package com.lbo.book.gamebasicsensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by tomcat2 on 2015-06-01.
 */
public class MainView extends View {
    private float input1_A=0;
    private float input2_A=0;
    private float input3_A=0;
    private float input1_O=0;
    private float input2_O=0;
    private float input3_O=0;
    public MainView(Context context) {
        super(context);
    }
    public void moveSensorAccelerometer (
            float input1, float input2, float input3) {
        input1_A = input1;
        input2_A = input2;
        input3_A = input3;
    }
    public void moveSensorOrientation(
            float input1, float input2, float input3) {
        input1_O = input1;
        input2_O = input2;
        input3_O = input3;
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(24);
        canvas.drawText("Acce 1:" + input1_A, 10, 100, paint);
        canvas.drawText("Acce 2:" + input2_A, 10, 150, paint);
        canvas.drawText("Acce 3:" + input3_A, 10, 200, paint);
        canvas.drawText("Orie 1:" + input1_O, 10, 250, paint);
        canvas.drawText("Orie 2:" + input2_O, 10, 300, paint);
        canvas.drawText("Orie 3:" + input3_O, 10, 350, paint);
    }
}
