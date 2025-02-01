package com.lbo.book.surfaceviewunit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Unit {
    private Bitmap mBitmap;
    private int mPosX;
    private int mPosY;
    private int mTargetX;
    private int mTargetY;
    private int mWidth;
    private int mHeight;
    private int mRotate;
    private ScreenConfig mScreenConfig;

    public void destroy() {
        try {
            if (mBitmap != null) {
                mBitmap.recycle();
            }
        } catch (Exception ex) {
        }
    }

    // 카드의 생성자
    public Unit(ScreenConfig screenConfig, Bitmap bitmaporg, int width, int height) {
        mScreenConfig = screenConfig;
        mWidth = mScreenConfig.getX(width);
        mHeight = mScreenConfig.getY(height);
        mBitmap  = Bitmap.createScaledBitmap(bitmaporg, mWidth, mHeight, false);
    }

    static Matrix mMatrix = new Matrix();

    public static Bitmap rotate(Bitmap mBitmap, int degree) {
        mMatrix.setRotate(degree, mBitmap.getWidth(), mBitmap.getHeight());
        Bitmap bitmapChg = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mMatrix, true);
        mBitmap = bitmapChg;
        return mBitmap;
    }

    public void move(int x, int y) {
        mPosX = mScreenConfig.getX(x);
        mPosY = mScreenConfig.getY(y);
        mTargetX = mScreenConfig.getX(x);
        mTargetY = mScreenConfig.getY(y);
    }

    public void setPosX(int x) {
        mPosX = mScreenConfig.getX(x);
        mTargetX = mScreenConfig.getX(x);
    }

    public void setPosY(int y) {
        mPosY = mScreenConfig.getY(y);
        mTargetY = mScreenConfig.getY(y);
    }
    public void moveTo(int x, int y) {
        mTargetX = mScreenConfig.getX(x);
        mTargetY = mScreenConfig.getY(y);
    }
    public void think(){
        mRotate++;
        if(mRotate == 360)
            mRotate = 0;
        if (this.mPosX > this.mTargetX + 10) {
            this.mPosX = this.mPosX - 9;
        } else if (this.mPosX < this.mTargetX - 10) {
            this.mPosX = this.mPosX + 9;
        }
        if (this.mPosY > this.mTargetY + 10) {
            this.mPosY = this.mPosY - 9;
        } else if (this.mPosY < this.mTargetY - 10) {
            this.mPosY = this.mPosY + 9;
        }
    }

    public void draw(Canvas canvas){
        if(mRotate != 0){
            canvas.drawBitmap(rotate(mBitmap,(int)(this.mRotate)),(int)(mPosX - mWidth/2), (int)(mPosY - mHeight/2), null);
        }
        else{
            canvas.drawBitmap(mBitmap, (int) (mPosX - mWidth / 2), (int) (mPosY - mHeight / 2), null);
        }
    }
}
