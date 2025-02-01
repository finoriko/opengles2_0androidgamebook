package com.lbo.book.gamebasictext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(12);
        Typeface typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD);
        canvas.drawText("CANVAS TEXT 1 !!",
                canvas.getWidth()/2 - 100,
                canvas.getHeight() * 1/4,
                paint);
        paint.setTextSize(24);
        canvas.drawText("CANVAS TEXT 2 !!",
                canvas.getWidth() / 2 - 100,
                canvas.getHeight() * 2 / 4, paint);
        paint.setTextSize(48);
        canvas.drawText("CANVAS TEXT 3 !!",
                canvas.getWidth() / 2 - 100,
                canvas.getHeight() * 3 / 4, paint);

    }


}
