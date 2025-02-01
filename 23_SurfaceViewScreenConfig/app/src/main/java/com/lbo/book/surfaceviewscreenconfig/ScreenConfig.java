package com.lbo.book.surfaceviewscreenconfig;


public class ScreenConfig {
    public static  int mScreenWidth;
    public static  int mScreenHeight;
    public static  int mVirtualWidth;
    public static  int mVirtualHeight;

    public ScreenConfig(int ScreenWidth , int ScreenHeight){
        mScreenWidth = ScreenWidth;
        mScreenHeight = ScreenHeight;
    }
    public void setSize(int width, int height){
        mVirtualWidth = width;
        mVirtualHeight = height;
    }
    public int getX(int x){
        return (int)( x * mScreenWidth/mVirtualWidth);
    }
    public int getY(int y) {
        return (int)( y * mScreenHeight/mVirtualHeight);
    }
}
