package com.lbo.book.threekingdomsman;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// 랜더링
public class MainGLRenderer implements Renderer {

    // 터치시 제어변수
    private float mStartX; // 터치시 시작위치
    private float mStartY;
    private float mEndX; // 터치가 종료된
    private float mEndY;
    private float mSizeRatio = 1; // 화면 비율을 관리함
    private int mPointerId; // 포인터 ID (터치한 순번)
    private int mPointerId2; // 포인터 ID2 (핀치 기능으로 2개의 터치까지 체크함)
    private boolean mIsTap = true; // 탭이었는지를 체크함
    private boolean mIsMove = false; // 이동이었는지를 체크함
    private boolean mIsExpend = false; // 확대,축소 였는지를 체크함
    private int mMoveInputX; // 화면 이동시 X축 시작점
    private int mMoveInputY; // 화면 이동시 Y축 시작점
    private float mStartExpandLength; // 핀치 기능을 사용시 시작점 길이
    private float mEndExpandLength; // 핀치 기능을 사용시 종료점 길이
    private float mBfEndExpandLength; // 핀치 기능을 사용시 이전 종료점 길이

    // 매트릭스
    private final float[] mMtrxProjection = new float[16];
    private final float[] mMtrxView = new float[16];
    private final float[] mMtrxProjectionAndView = new float[16];
    // 프로그램색상, 이미지
    private static int mProgramSolidColor;
    private static int mProgramImage;
    long mLastTime;
    // 디바이스의 넓이, 높이
    public static int mDeviceWidth = 0;
    public static int mDeviceHeight = 0;
    // 주 액티비티
    MainActivity mActivity;
    Context mContext;
    // 화면 설정
    ScreenConfig mScreenConfig;
    // 객체로 사용할 객체
    //Unit mUnit;
    Man mMan;
    Button[] mButtons = new Button[4];
    boolean mIsDraw = true;

    ResourceLoading mResourceLoading;

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
        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f, ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.setLookAtM(mMtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }

    // 서피스뷰 생성
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mScreenConfig = new ScreenConfig(mDeviceWidth, mDeviceHeight);
        mScreenConfig.setSize(ConstMgr.SCREEN_WIDTH, ConstMgr.SCREEN_HEIGHT);
        GLES20.glClearColor(0.0f, 0.5f, 0.0f, 1);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vs_Image);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs_Image);
        mProgramImage = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgramImage, vertexShader);
        GLES20.glAttachShader(mProgramImage, fragmentShader);
        GLES20.glLinkProgram(mProgramImage);
        GLES20.glUseProgram(mProgramImage);

        float scale = mContext.getResources().getDisplayMetrics().density;
        mResourceLoading = new ResourceLoading(mActivity, mContext, scale);

        // 유닛을 생성하고 이미지, 크기, 좌표를 설정한다.
        mMan = new Man(mProgramImage, mProgramSolidColor, this);
        //mLand = new  Panel(mProgramImage, mProgramSolidColor);
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                Map.mLand[i][j] = new Panel(mProgramImage, mProgramSolidColor);
            }
        }
        for (int i = 0; i < 4; i++) {
            mButtons[i] = new Button(mProgramImage, mProgramSolidColor, this);
        }
        // 리소스 로딩.
        mResourceLoading.loadingResource(mMan, Map.mLand, mButtons);
        mMan.setIsActive(true);
        mMan.setToBlock(10,10);
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                Map.mLand[i][j].setPos(Map.getPosX(i, j), Map.getPosY(i, j));
                Map.mLand[i][j].setIsActive(true);
            }
        }

        for (int i = 0; i < 4; i++) {
            mButtons[i].setIsActive(true);
            mButtons[i].setPos(110 + i * 200, 110);
        }

    }

    // 쉐이더 이미지
    public static final String vs_Image =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 a_texCoord;" +
                    "varying vec2 v_texCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  v_texCoord = a_texCoord;" +
                    "}";

    public static final String fs_Image =
            "precision mediump float;" +
                    "varying vec2 v_texCoord;" +
                    "uniform sampler2D s_texture;" + "void main() {" +
                    "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
                    "}";

    // 쉐이더 로딩
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    // 그리기 호출
    @Override
    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();
        if (mLastTime > now)
            return;

        long elapsed = now - mLastTime;

        // 그리기를 시작한다.
        /*
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1);
        Matrix.orthoM(mMtrxProjection, 0, 0f, 2000, 0.0f, 1200, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        */
        if (ConstMgr.SCREEN_MODE == ConstMgr.SCREEN_INTRO) {
            RenderIntro(mMtrxProjectionAndView);
        } else if (ConstMgr.SCREEN_MODE == ConstMgr.SCREEN_MAP) {
            RenderMap(mMtrxProjectionAndView);
        } else if (ConstMgr.SCREEN_MODE == ConstMgr.SCREEN_GAME) {
            RenderGame(mMtrxProjectionAndView);
        }
        mLastTime = now;
    }

    // 초기화면 랜더링
    private void RenderIntro(float[] m) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1);
        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f, ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        mMan.draw(mMtrxProjectionAndView);
    }

    // 맵화면 랜더링
    private void RenderMap(float[] m) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);
        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f, ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        mMan.draw(mMtrxProjectionAndView);
    }

    // 그리기 시작
    private void RenderGame(float[] m) {
        //mLand.think();
        mMan.think();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.5f, 0.0f, 1);

        Matrix.orthoM(mMtrxProjection, 0,
                mMoveInputX * mSizeRatio,
                mMoveInputX * mSizeRatio + ConstMgr.SCREEN_WIDTH * mSizeRatio,
                mMoveInputY * mSizeRatio,
                mMoveInputY * mSizeRatio + ConstMgr.SCREEN_HEIGHT * mSizeRatio, 0, 50);
        Matrix.setLookAtM(mMtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) { Map.mLand[i][j].draw(m);
            }
        }
        mMan.draw(mMtrxProjectionAndView);
        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f,
                ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        for (int i = 0; i < 4; i++){
            mButtons[i].draw(mMtrxProjectionAndView);
        }

    }


    public boolean onTouchEvent(MotionEvent event) {
        // 좌표
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        // 변환된 좌표
        final int chgX = mScreenConfig.getX(x); // 디바이스에 터치된 위치를 OpenGL 프로젝션 크기로 변환한다.
        final int chgY = mScreenConfig.getY(y);
        final int action = event.getAction(); // 터치 이벤트의 종류를 받는다.
        mPointerId = event.getPointerId(0); // 터치 이벤트의 첫번째 포인터다.

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                // 터치 다운할 경우
                mStartX = chgX;  // 시작위치
                mStartY = chgY;
                mIsTap = true;  // 우선 탭으로 간주한다.
                mIsMove = false; // 우선 화면이동으로 간주하지 않는다.
                mIsExpend = false; // 우선 화면확대, 축소로 간주하지 않는다.
                break;
            }
            case MotionEvent.ACTION_MOVE: { // 터치한 곳이 이동할 경우
                mEndX = chgX; // 이동중에도 좌표를 읽는다.
                mEndY = chgY;
                if (mIsExpend == false) { // 확장이 아닐 경우
                    if (mIsTap == true) { // 탭을 하고나서 움직인 거라면
                        if (Math.abs(mEndX - mStartX) > mScreenConfig.getX(5) || Math.abs(mEndY - mStartY) > mScreenConfig.getY(5)) {
                            mIsMove = true; // 5보다 크게 움직였다면 움직인 것으로 간주한다.
                        }
                        moveScreenX((int) (mEndX - mStartX)); // 화면을 이동시킨다.
                        moveScreenY((int) (mEndY - mStartY));
                        mStartX = mEndX; // 시작점은 움직인 점으로 대체한다.
                        mStartY = mEndY;
                    }
                } else { // 확장일 경우
                    final float x2 = event.getX(mPointerId2); // 두번째 터치한 좌표를 읽는다.
                    final float y2 = event.getY(mPointerId2);
                    mEndExpandLength = Math.abs(x - x2); // 손가락을 벌린 폭을 계산한다.
                    // 확장한 비유을 계산한다.
                    mSizeRatio = mSizeRatio - ((mEndExpandLength - mStartExpandLength) - mBfEndExpandLength) / mStartExpandLength;
                    if (mSizeRatio < 0.5) {  // 너무 작게 축소할 경우 제한한다.
                        mSizeRatio = 0.5f;
                    }
                    if (mSizeRatio > 1) { // 너무 크게 확대할  경우 제한한다.
                        mSizeRatio = 1;
                    }
                    scaleScreen(mSizeRatio); // 화면을 축소, 확대하도록 호출한다.
                    mBfEndExpandLength = (mEndExpandLength - mStartExpandLength);
                }
                break;
            }
            case MotionEvent.ACTION_UP: { // 터치 업할 경우
                if (mIsExpend == false) { // 확장이 아니라면
                    if (mIsMove == false) { // 이동이 아니라면
                        selectTouch(chgX, chgY); // 터치한 것이 무엇인지를 계산하기 위해 호출한다.
                    }
                    mIsTap = false; // 탭을 취소한다.
                    mIsMove = false; // 이동을 취소한다.
                    mEndX = chgX;
                    mEndY = chgY;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: { // 터치한 좌표가 화면 밖으로 이동하거나 할  경우
                mIsTap = false;
                mIsMove = false;
                mIsExpend = false;
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: { // 두번째 터치 다운이 발생할 경우
                mIsMove = false; // 이동이 아님
                mIsTap = false; // 탭이 아님
                mIsExpend = true; // 확대, 축소임
                // 터치된 포인터가 꼭 두번째가 아닐 수 있기 때문에 이를 계산하기 위해 쉬프트 연산한다.
                final int pointerIndex = (event.getAction() &
                        MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                event.getPointerCount();
                try {
                    mPointerId2 = event.getPointerId(pointerIndex); // 두번째 터치 포인터를 얻는다.
                    final float x2 = (int) event.getX(mPointerId2); // 터치 좌표를 읽는다.
                    final float y2 = (int) event.getY(mPointerId2);
                    mStartExpandLength = Math.abs(x - x2); // 확대한 폭을 계산한다.
                } catch (Exception ex) {
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: { // 두번째 터치 업의 경우
                try {
                    mStartExpandLength = 0; // 확대, 축소의 시작길이을 초기화한다.
                    mEndExpandLength = 0; // 확대, 축소의 종료길이를 초기화 한다.
                    mBfEndExpandLength = 0;
                    mIsExpend = false; // 확대,축소 모드가 아님
                } catch (Exception ex) {
                }
                break;
            }
        }
        return true;
    }

    private void selectTouch(int x, int y) {

        if (ConstMgr.SCREEN_MODE == ConstMgr.SCREEN_INTRO) {
            selectTouchIntro(x, y);
        } else if (ConstMgr.SCREEN_MODE == ConstMgr.SCREEN_MAP) {
            selectTouchMap(x, y);
        } else if (ConstMgr.SCREEN_MODE == ConstMgr.SCREEN_GAME) {
            selectTouchGame(x, y);
        }
    }

    public void moveScreenX(float input) {
        mMoveInputX -= (int) input;
    }

    public void moveScreenY(float input) {
        mMoveInputY -= (int) input;
    }

    public void scaleScreen(float ratio) {
        mSizeRatio = ratio;
        if(mSizeRatio <=0.2f){
            mSizeRatio = 0.2f;
        }
    }


    private void selectTouchIntro(int x, int y){
        if(mMan.isSelected(x,y)== true){
            Log.e("", "intro");
            ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_MAP;
        }
    }
    private void selectTouchMap(int x, int y){
        if(mMan.isSelected(x,y)== true){
            Log.e("", "selectTouchMap");
            ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_GAME;
        }
    }
    private void selectTouchGame(int x, int y){
        if(mMan.isSelected(x,y)== true){
            Log.e("", "selectTouchGame");
            ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_INTRO;
        }
        if(mButtons[0].isSelected(x,y)== true){
            //mLand.moveTo(ConstMgr.SCREEN_WIDTH/2, 100);
            mMan.moveToBlock(mMan.getBlockRow() + 1, mMan.getBlockCol());
        }
        if(mButtons[1].isSelected(x, y)== true){
            //mLand.moveTo(ConstMgr.SCREEN_WIDTH/2, ConstMgr.SCREEN_HEIGHT -100);
            mMan.moveToBlock(mMan.getBlockRow() - 1, mMan.getBlockCol());

        }
        if(mButtons[2].isSelected(x, y)== true){
            //mLand.moveTo(100, ConstMgr.SCREEN_HEIGHT/2);
            mMan.moveToBlock(mMan.getBlockRow(), mMan.getBlockCol() -1);
        }
        if(mButtons[3].isSelected(x, y)== true){
            //mLand.moveTo(ConstMgr.SCREEN_WIDTH - 100, ConstMgr.SCREEN_HEIGHT/2);
            mMan.moveToBlock(mMan.getBlockRow(), mMan.getBlockCol() + 1);
        }
    }


}
