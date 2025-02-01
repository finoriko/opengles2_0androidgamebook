package com.lbo.book.gamebasiccanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by tomcat2 on 2015-05-31.
 */
public class MainView extends View {
    public MainView(Context context) {
        super(context);
    }
    @Override
    public void onDraw(Canvas canvas){
        Paint paint = new Paint();
/*        RadialGradient rGradient = new RadialGradient(
                canvas.getWidth()/2,
                canvas.getHeight()/4,
                canvas.getHeight()/10,
                Color.RED,
                Color.GREEN,
                Shader.TileMode.MIRROR
        );
        paint.setShader(rGradient); // Gradient 부분 */
        paint.setColor(Color.RED);
        canvas.drawCircle(canvas.getWidth()/2,
                canvas.getHeight()/4,
                canvas.getHeight()/10, paint);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(
                canvas.getWidth()/2 - canvas.getHeight()/10, canvas.
                getHeight()*2/4 - canvas.getHeight()/10,
                canvas.getWidth()/2 + canvas.getHeight()/10,
                canvas.getHeight()*2/4 +canvas.getHeight()/10, paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(canvas.getWidth()/2,
                canvas.getHeight()*3/4, canvas.
                        getHeight()/10, paint);


    }


}
