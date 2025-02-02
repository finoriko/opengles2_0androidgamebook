package com.lbo.book.threekingdomsmantypeandarray;

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
    public static int[] mHandleOurForceSpearMan = new int[4];
    public static int[] mHandleEnemyForceSpearMan = new int[4];
    public static int[] mHandleOurForceSwordMan = new int[4];
    public static int[] mHandleEnemyForceSwordMan = new int[4];
    public static int[] mHandleOurForceBowMan = new int[4];
    public static int[] mHandleEnemyForceBowMan = new int[4];
    public static int[] mHandleOurForceShieldMan = new int[4];
    public static int[] mHandleEnemyForceShieldMan = new int[4];

    public static int[] mHandleOurForceGeneral = new int[4];
    public static int[] mHandleEnemyForceGeneral = new int[4];
    public static int[] mHandleOurForceBase = new int[4];
    public static int[] mHandleEnemyForceBase = new int[4];
    public static int[] mHandleOurForceGeneral1 = new int[4];
    public static int[] mHandleEnemyForceGeneral1 = new int[4];
    public static int[] mHandleOurForceGeneral2 = new int[4];
    public static int[] mHandleEnemyForceGeneral2 = new int[4];
    public static int[] mHandleOurForceGeneral3 = new int[4];
    public static int[] mHandleEnemyForceGeneral3 = new int[4];

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
    public void loadingResource(Man[] ourForce, Man[] enemyForce, Panel[][] map, Button[] buttons){
        /*
        Bitmap bmpSpearMan = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/b_spearman1", null, mContext.getPackageName()));
        mHandleUnit = getImageHandle(bmpSpearMan);
        bmpSpearMan.recycle();
        unit.setBitmap(mHandleUnit,150,300);
        */
        /*
        Bitmap bmpSpearMan[] = new Bitmap[4];
        for(int i=0; i<4; i++){
            bmpSpearMan[i] = BitmapFactory.decodeResource(mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/b_spearman" + (i+1), null,
                            mContext.getPackageName()));

        }
        mHandleEnemyForceSpearMan = getImageHandle(bmpSpearMan);

        for(int i=0; i<4; i++)
            bmpSpearMan[i].recycle();
        man.setBitmap(mHandleEnemyForceSpearMan, 60,90);
        */
    // 아군 창병

        Bitmap[] bmpOurForceSpearMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceSpearMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/y_spearman" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceSpearMan = getImageHandle(bmpOurForceSpearMan);
        for (int i = 0; i < 4; i++)
            bmpOurForceSpearMan[i].recycle();

    // 아군 검병
        Bitmap[] bmpOurForceSwordMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceSwordMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/y_swordman" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceSwordMan = getImageHandle(bmpOurForceSwordMan);
        for (int i = 0; i < 4; i++)
            bmpOurForceSwordMan[i].recycle();

        // 아군 궁병
        Bitmap[] bmpOurForceBowMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceBowMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/y_bowman" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceBowMan = getImageHandle(bmpOurForceBowMan);
        for (int i = 0; i < 4; i++)
            bmpOurForceBowMan[i].recycle();

// 아군 방패병
        Bitmap[] bmpOurForceShieldMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceShieldMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/y_shieldman" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceShieldMan = getImageHandle(bmpOurForceShieldMan);
        for (int i = 0; i < 4; i++)
            bmpOurForceShieldMan[i].recycle();

// 아군 장군
        Bitmap[] bmpOurForceGeneral = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/y_general" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceGeneral = getImageHandle(bmpOurForceGeneral);
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral[i].recycle();

// 베이스
        Bitmap[] bmpOurForceBase = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceBase[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/base" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceBase = getImageHandle(bmpOurForceBase);
        for (int i = 0; i < 4; i++)
            bmpOurForceBase[i].recycle();

// 장비
        Bitmap[] bmpOurForceGeneral1 = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral1[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/jangbi" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceGeneral1 = getImageHandle(bmpOurForceGeneral1);
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral1[i].recycle();

// 관우
        Bitmap[] bmpOurForceGeneral2 = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral2[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/gwanu" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceGeneral2 = getImageHandle(bmpOurForceGeneral2);
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral2[i].recycle();

// 유비
        Bitmap[] bmpOurForceGeneral3 = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral3[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/ryubi" + (i + 1), null, mContext.getPackageName()));
        mHandleOurForceGeneral3 = getImageHandle(bmpOurForceGeneral3);
        for (int i = 0; i < 4; i++)
            bmpOurForceGeneral3[i].recycle();

// 적군 창병
        Bitmap[] bmpEnemyForceSpearMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceSpearMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/b_spearman" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceSpearMan = getImageHandle(bmpEnemyForceSpearMan);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceSpearMan[i].recycle();

// 적군 검병
        Bitmap[] bmpEnemyForceSwordMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceSwordMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/b_swordman" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceSwordMan = getImageHandle(bmpEnemyForceSwordMan);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceSwordMan[i].recycle();

// 적군 궁병
        Bitmap[] bmpEnemyForceBowMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceBowMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/b_bowman" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceBowMan = getImageHandle(bmpEnemyForceBowMan);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceBowMan[i].recycle();

// 적군 궁병
        Bitmap[] bmpEnemyForceShieldMan = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceShieldMan[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/b_shieldman" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceShieldMan = getImageHandle(bmpEnemyForceShieldMan);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceShieldMan[i].recycle();

// 적군 장군
        Bitmap[] bmpEnemyForceGeneral = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/b_general" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceGeneral = getImageHandle(bmpEnemyForceGeneral);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral[i].recycle();

// 베이스
        Bitmap[] bmpEnemyForceBase = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceBase[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/base" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceBase = getImageHandle(bmpEnemyForceBase);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceBase[i].recycle();

// 장합
        Bitmap[] bmpEnemyForceGeneral1 = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral1[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/janghab" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceGeneral1 = getImageHandle(bmpEnemyForceGeneral1);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral1[i].recycle();

// 하후돈
        Bitmap[] bmpEnemyForceGeneral2 = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral2[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/hahudon" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceGeneral2 = getImageHandle(bmpEnemyForceGeneral2);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral2[i].recycle();

// 조조
        Bitmap[] bmpEnemyForceGeneral3 = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral3[i] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/chocho" + (i + 1), null, mContext.getPackageName()));
        mHandleEnemyForceGeneral3 = getImageHandle(bmpEnemyForceGeneral3);
        for (int i = 0; i < 4; i++)
            bmpEnemyForceGeneral3[i].recycle();

// 아군 설정
        for(int i=0; i< ourForce.length; i++) {
            if (ourForce[i].mType == ConstMgr.TYPE_SPEAR_MAN){
                ourForce[i].setBitmap(mHandleOurForceSpearMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_SWORD_MAN){
                ourForce[i].setBitmap(mHandleOurForceSwordMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_BOW_MAN){
                ourForce[i].setBitmap(mHandleOurForceBowMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_SHIELD_MAN){
                ourForce[i].setBitmap(mHandleOurForceShieldMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL){
                ourForce[i].setBitmap(mHandleOurForceGeneral, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_WIDTH);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_BASE){
                ourForce[i].setBitmap(mHandleOurForceBase, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_WIDTH);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL1){
                ourForce[i].setBitmap(mHandleOurForceGeneral1, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_WIDTH);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL2){
                ourForce[i].setBitmap(mHandleOurForceGeneral2, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_WIDTH);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL3){
                ourForce[i].setBitmap(mHandleOurForceGeneral3, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_WIDTH);
            }
        }

// 적군 설정
        for(int i=0; i< enemyForce.length; i++) {
            if (enemyForce[i].mType == ConstMgr.TYPE_SPEAR_MAN){
                enemyForce[i].setBitmap(mHandleEnemyForceSpearMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_SWORD_MAN){
                enemyForce[i].setBitmap(mHandleEnemyForceSwordMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_BOW_MAN){
                enemyForce[i].setBitmap(mHandleEnemyForceBowMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_SHIELD_MAN){
                enemyForce[i].setBitmap(mHandleEnemyForceShieldMan, ConstMgr.FORCE_WIDTH, ConstMgr.FORCE_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_GENERAL){
                enemyForce[i].setBitmap(mHandleEnemyForceGeneral, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_BASE){
                enemyForce[i].setBitmap(mHandleEnemyForceBase, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_GENERAL1){
                enemyForce[i].setBitmap(mHandleEnemyForceGeneral1, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_GENERAL2){
                enemyForce[i].setBitmap(mHandleEnemyForceGeneral2, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (enemyForce[i].mType == ConstMgr.TYPE_GENERAL3){
                enemyForce[i].setBitmap(mHandleEnemyForceGeneral3, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
        }


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
// 이미지 핸들 설정
    private int[] getImageHandle(Bitmap[] bitmap){

        int handleSize = bitmap.length;
        int[] retHandle = new int[handleSize];
        for(int i=0; i<handleSize; i++ ){
            retHandle[i] = getImageHandle(bitmap[i]);
        }
        return retHandle;
    }

}
