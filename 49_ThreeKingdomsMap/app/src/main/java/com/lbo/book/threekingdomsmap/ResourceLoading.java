package com.lbo.book.threekingdomsmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

// 리소스 관리
public class ResourceLoading {
    private Context mContext;
    private Activity mActivity;

    // 유닛 객체
    private static int mHandleUnit;
    private static int mHandleLand;
    private static int[] mHandleButtons = new int[4];

    // 화면확대축소관리
    private float mScale = 0;

    // 리소스로딩 생성자
    public ResourceLoading(Activity activity, Context context, float scale){
        mActivity = activity;
        mContext = context;
        mScale = scale;
    }

    // 리소스 로딩
    public void loadingResource(Unit unit, Panel[][] map, Button[] buttons){
        Bitmap bmpSpearMan = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/b_spearman1", null, mContext.getPackageName()));
        mHandleUnit = getImageHandle(bmpSpearMan);
        bmpSpearMan.recycle();
        unit.setBitmap(mHandleUnit,150,300);
        // land
        Bitmap bmpLand = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/land_green", null, mContext.getPackageName()));
        mHandleLand = getImageHandle(bmpLand);
        bmpLand.recycle();
        //land.setBitmap(mHandleLand,200,120);
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                map[i][j].setBitmap(mHandleLand,100, 60);
            }
        }

        // 버튼 1
        Bitmap bmpBtnDown = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/button_down", null, mContext.getPackageName()));
        mHandleButtons[0] = getImageHandle(bmpBtnDown);
        bmpBtnDown.recycle();
        buttons[0].setBitmap(mHandleButtons[0],200,200);
        //unit.setBitmap(mHandleUnit,150,300);
        // 버튼 2
        Bitmap bmpBtnUp = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/button_up", null, mContext.getPackageName()));
        mHandleButtons[1] = getImageHandle(bmpBtnUp);
        bmpBtnUp.recycle();
        buttons[1].setBitmap(mHandleButtons[1],200,200);
        //unit.setBitmap(mHandleUnit,150,300);

        // 버튼 3
        Bitmap bmpBtnLeft = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/button_left", null, mContext.getPackageName()));
        mHandleButtons[2] = getImageHandle(bmpBtnLeft);
        bmpBtnLeft.recycle();
        buttons[2].setBitmap(mHandleButtons[2], 200, 200);
        //unit.setBitmap(mHandleUnit,150,300);
        // 버튼 4
        Bitmap bmpBtnRight = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/button_right", null, mContext.getPackageName()));
        mHandleButtons[3] = getImageHandle(bmpBtnRight);
        bmpBtnRight.recycle();
        buttons[3].setBitmap(mHandleButtons[3],200,200);
        //unit.setBitmap(mHandleUnit,150,300);
    }

    // 이미지 핸들 반환
    private int getImageHandle(Bitmap bitmap){
        int[] texturenames = new int[1];
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glGenTextures(1, texturenames, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        return texturenames[0];
    }

}
