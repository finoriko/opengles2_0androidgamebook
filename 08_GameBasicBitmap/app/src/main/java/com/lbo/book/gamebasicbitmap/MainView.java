package com.lbo.book.gamebasicbitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.spearman);
        canvas.drawBitmap(
                bitmap, canvas.getWidth()/2 - bitmap.getWidth()/2,
                canvas.getHeight()/4 -bitmap.getHeight()/2, paint);

        Bitmap bitmapScale =Bitmap.createScaledBitmap(bitmap, 150,150,false);
        canvas.drawBitmap(
                bitmapScale,
                canvas.getWidth()/2 - bitmapScale.getScaledWidth(canvas)/2 ,
                canvas.getHeight()*2/4- bitmapScale.getScaledHeight(canvas)/2, paint);

        Matrix matrixDirX = new Matrix();
        matrixDirX.preScale(-1,1);
        Bitmap bitmapMatrixDirX = Bitmap.createBitmap( bitmap, 0,0, bitmap.getWidth(),
                        bitmap.getHeight(), matrixDirX, false);
        canvas.drawBitmap(bitmapMatrixDirX,
                canvas.getWidth()/4 - bitmap.getWidth()/2 ,
                canvas.getHeight()*3/4- bitmap.getWidth()/2,paint);

        Matrix matrix = new Matrix();
        matrix.preRotate(90);
        Bitmap bitmapMatrix = Bitmap.createBitmap(bitmap, 0,0,
                bitmap.getWidth(),bitmap.getHeight(), matrix, false);
        canvas.drawBitmap(bitmapMatrix, canvas.getWidth()/2 - bitmap.getWidth()/2 ,
                canvas.getHeight()*3/4- bitmap.getWidth()/2,paint);

        Matrix matrixDirY = new Matrix();
        matrixDirY.preScale(1,-1);
        Bitmap bitmapMatrixDirY =
                Bitmap.createBitmap(bitmap, 0,0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        matrixDirY, false);
        canvas.drawBitmap(
                bitmapMatrixDirY,
                canvas.getWidth()*3/4 - bitmap.getWidth()/2 ,
                canvas.getHeight()*3/4- bitmap.getWidth()/2,paint);

        bitmapScale.recycle();
        bitmap.recycle();
    }


}
