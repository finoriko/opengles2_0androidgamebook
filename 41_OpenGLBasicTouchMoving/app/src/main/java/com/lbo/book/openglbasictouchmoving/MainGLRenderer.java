package com.lbo.book.openglbasictouchmoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// 랜더링
public class MainGLRenderer implements Renderer {
    // 매트릭스
    private final float[] mMtrxProjection = new float[16];
    private final float[] mMtrxView = new float[16];
    private final float[] mMtrxProjectionAndView = new float[16];
    // 프로그램
    private static int mProgramImage;
    // 프로그램
    long mLastTime;
    // 디바이스의 넓이, 높이
    public static int mDeviceWidth = 0;
    public static int mDeviceHeight = 0;
    // 주 액티비티
    MainActivity mActivity;
    Context mContext;

    TexMulti[] mTexMulti = new TexMulti[4];
    ScreenConfig mScreenConfig;
    // 생성자
    public MainGLRenderer(MainActivity activity, int width, int height) {
        mActivity = activity;
        mContext = activity.getApplicationContext();
        mLastTime = System.currentTimeMillis() + 100;
        mDeviceWidth = width;
        mDeviceHeight = height;

    }
    // 멈춤
    public void onPause() {
    }
    // 재시작
    public void onResume() {
        mLastTime = System.currentTimeMillis();
    }
    // 서피스뷰 변경
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, (int) mDeviceWidth, (int) mDeviceHeight);
        Matrix.setIdentityM(mMtrxProjection, 0);
        Matrix.setIdentityM(mMtrxView, 0);
        Matrix.setIdentityM(mMtrxProjectionAndView, 0);
        Matrix.orthoM(mMtrxProjection, 0, 0, 1000, 0, 1600, 0, 50);
        Matrix.setLookAtM(mMtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
    }
    // 서피스뷰 생성
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mScreenConfig = new ScreenConfig(mDeviceWidth,mDeviceHeight);
        mScreenConfig.setSize(1000, 1600);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vs_Image);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs_Image);
        mProgramImage = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgramImage, vertexShader);
        GLES20.glAttachShader(mProgramImage, fragmentShader);
        GLES20.glLinkProgram(mProgramImage);
        GLES20.glUseProgram(mProgramImage);
        Log.e("", mDeviceWidth + "," + mDeviceHeight);



        Bitmap[] image = new Bitmap[4];
        int[] imageHandle = new int[4];
        for(int i=0; i<4; i++) {
            image[i] = BitmapFactory.decodeResource(mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/b_spearman" + (i + 1), null,
                            mContext.getPackageName()));
            imageHandle[i] = getImageHandle(image[i]);
        }

        for(int i =0; i<4; i++) {
            mTexMulti[i] = new TexMulti(mProgramImage);
            mTexMulti[i].setBitmap(imageHandle, 180, 300);
        }
        mTexMulti[0].setAngle(0);
        mTexMulti[0].setPosX(250);
        mTexMulti[0].setPosY(1200);
        mTexMulti[0].setScale(0.5f);

        mTexMulti[1].setAngle(90);
        mTexMulti[1].setPosX(750);
        mTexMulti[1].setPosY(1200);
        mTexMulti[1].setScale(1.0f);

        mTexMulti[2].setAngle(180);
        mTexMulti[2].setPosX(250);
        mTexMulti[2].setPosY(400);
        mTexMulti[2].setScale(1.5f);

        mTexMulti[3].setAngle(270);
        mTexMulti[3].setPosX(750);
        mTexMulti[3].setPosY(400);
        mTexMulti[3].setScale(2.0f);
    }
    // 쉐이더 이미지
    public static final String vs_Image = "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "attribute vec2 a_texCoord;" +
            "varying vec2 v_texCoord;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "  v_texCoord = a_texCoord;" + "}";
    public static final String fs_Image = "precision mediump float;" +
            "varying vec2 v_texCoord;" +
            "uniform sampler2D s_texture;" +
            "void main() {" +
            "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
            "}";
    // 쉐이더 로딩
    public static int loadShader(int type, String shaderCode) { int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
    // 그리기 호출
    @Override
    public void onDrawFrame(GL10 unused) { long now = System.currentTimeMillis();
        if (mLastTime > now)
            return;
        long elapsed = now - mLastTime;

        for(int i= 0; i<4; i++) {
            mTexMulti[i].think();
        }

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1);
        // 전체화면
        //GLES20.glViewport(0, 0, (int) mDeviceWidth, (int) mDeviceHeight);
        Matrix.orthoM(mMtrxProjection, 0, 0, 1000, 0, 1600, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1);
        for(int i= 0; i<4; i++) {
            mTexMulti[i].draw(mMtrxProjectionAndView);
        }


        mLastTime = now;
    }

    private int mPointerId;
    public boolean onTouchEvent(MotionEvent event){

        final int x = (int)event.getX();
        final int y = (int)event.getY();
        final int chgX = mScreenConfig.getX(x);
        final int chgY = mScreenConfig.getY(y);
        Log.e("", x + ":" + y + ":" + chgX + ":" + chgY);
        final int action =event.getAction();
        switch(action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:{
                selectTouch(chgX, chgY);
            }
        }

        return true;
    }

    int mSelectedIndex = -1;
    private void selectTouch(int x, int y){
        boolean temp = false;
        for(int i=0; i< 4; i++) {
            if (mTexMulti[i].isSelected(x, y) == true) {
                Log.e("", "선택" + i);
                temp = true;
                mSelectedIndex = i;
                break;
            }
        }
        if(temp == false) {
            if(mSelectedIndex != -1){
                mTexMulti[mSelectedIndex].moveTo(x,y);
                mSelectedIndex = -1;
            }
        }

    }




    // 이미지 핸들
    private int getImageHandle(Bitmap bitmap){
        int[] texturenames = new int[1];
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glGenTextures(1, texturenames, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        return texturenames[0];
    }
}
