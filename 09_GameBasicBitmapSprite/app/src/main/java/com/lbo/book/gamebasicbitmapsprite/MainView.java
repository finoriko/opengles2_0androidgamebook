package com.lbo.book.gamebasicbitmapsprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by tomcat2 on 2015-05-31.
 */
public class MainView extends View {

    Bitmap bitmap;
    int bitmapWidth = 100;
    int bitmapHeight = 150;
    int count = 0;
    float scale;

    public MainView(Context context) {
        super(context);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spearman);
        scale = context.getResources().getDisplayMetrics().density;
    }
    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.RED);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap,
                new Rect(0,0, (int)(bitmapWidth * scale), (int)(bitmapHeight * scale)),
                new Rect(0,0,(int)(bitmapWidth * scale), (int)(bitmapHeight*scale)), null);
        canvas.drawBitmap(bitmap,
                new Rect((int)(bitmapWidth * scale),0, (int)(bitmapWidth * scale  *2), (int)(bitmapHeight * scale)),
                new Rect((int)(bitmapWidth * scale),0,(int)(bitmapWidth * scale * 2), (int)(bitmapHeight*scale)), null);
        canvas.drawBitmap(bitmap,
                new Rect((int)(bitmapWidth * scale * 2),0, (int)(bitmapWidth * scale * 3), (int)(bitmapHeight * scale)),
                new Rect((int)(bitmapWidth * scale * 2),0,(int)(bitmapWidth * scale * 3), (int)(bitmapHeight*scale)), null);

    }


}
