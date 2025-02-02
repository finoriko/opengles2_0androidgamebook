package com.lbo.book.threekingdomsenemymove;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
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

    // 초기화면
    private static Panel mSubject;
    private static Button mBtnEnter;
    private static Man mRyubiIntro;
    private static Man mGwanuIntro;
    private static Man mJangbiIntro;

    // 지도화면
    private static Panel mPanelMap;
    private static Button[] mCity = new Button[ConstMgr.CITY_SIZE];
    private static Panel[] mCityName = new Panel[ConstMgr.CITY_SIZE];
    private static Button mBtnExit;
    private static Man mRyubiMap;
    private static Man mJangbiMap;
    private static Man mGwanuMap;
    private static Man mJanghabMap;
    private static Man mHahudonMap;
    private static Man mChochoMap;

    //
    private static int mTreeHouseCount = 0;
    private static Panel[] mTreeHouse = new Panel[ConstMgr.TREEHOUSE_SIZE];
    // 객체로 사용할 객체

    private static Man[] mOurForce = new Man[ConstMgr.OURFORCE_SIZE];
    private static Panel[] mOurForceEnergy = new Panel[ConstMgr.OURFORCE_SIZE];
    private static Panel[][] mOurForceWord = new Panel[ConstMgr.OURFORCE_SIZE][ConstMgr.STATE_SIZE];

    private static Man[] mEnemyForce = new Man[ConstMgr.ENEMYFORCE_SIZE];
    private static Panel[] mEnemyForceEnergy = new Panel[ConstMgr.ENEMYFORCE_SIZE];
    private static Panel[][] mEnemyForceWord =new Panel[ConstMgr.ENEMYFORCE_SIZE][ConstMgr.STATE_SIZE];

    // 버튼 추가
    private static int mSelectedButtonIndex = -1;   // 선택한 버튼의 인덱스 값.
    private static int mOurForceIndexCount = 0; // 터치하여 아군 추가시 사용할 인덱스 ㄱ밧
    private static Button mBtnExitGame;
    private static Button mBtnSwordMan;
    private static Button mBtnSpearMan;
    private static Button mBtnBowMan;
    private static Button mBtnShieldMan;
    private static Button mBtnJangbi;
    private static Button mBtnGwanu;



    Button[] mButtons = new Button[4];

    private boolean mIsFirstCalled = true;
    private boolean mIsDraw = false;

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




        if(mIsFirstCalled == true){
           init();
        }
        else {
            initResource();
            mResourceLoading.setResourceIntro(mSubject,mRyubiIntro, mGwanuIntro, mJangbiIntro, mBtnEnter);

            mResourceLoading.setResourceMap(mPanelMap, mCity, mCityName, mRyubiMap, mGwanuMap,
                    mJangbiMap, mChochoMap, mHahudonMap, mJanghabMap, mBtnExit);
            mResourceLoading.setResourceGame(mOurForce, mOurForceEnergy, mOurForceWord,
                    mEnemyForce, mEnemyForceEnergy, mEnemyForceWord, Map.mLand, mTreeHouse,
                    mBtnExitGame,mBtnSwordMan, mBtnSpearMan, mBtnBowMan, mBtnShieldMan, mBtnJangbi, mBtnGwanu);
        }
        mIsFirstCalled = false;
        /*
        // 유닛을 생성하고 이미지, 크기, 좌표를 설정한다.
        //mMan = new Man(mProgramImage, mProgramSolidColor, this);
        for(int i=0; i<ConstMgr.OURFORCE_SIZE; i++){
            mOurForce[i] = new Man(mProgramImage,mProgramSolidColor, this);
            mOurForce[i].setType(i);
            mOurForceEnergy[i] = new Panel(mProgramImage,mProgramSolidColor);
        }
        for(int i=0; i<ConstMgr.ENEMYFORCE_SIZE; i++){
            mEnemyForce[i] = new Man(mProgramImage,mProgramSolidColor, this);
            mEnemyForce[i].setType(i);
            mEnemyForceEnergy[i] = new Panel(mProgramImage,mProgramSolidColor);
        }
        for(int i=0; i<ConstMgr.OURFORCE_SIZE; i++){
            for(int j=0;j<ConstMgr.STATE_SIZE; j++){
                mOurForceWord[i][j] = new Panel(mProgramImage,mProgramSolidColor);
            }
        }
        for(int i=0; i<ConstMgr.ENEMYFORCE_SIZE; i++){
            for(int j=0;j<ConstMgr.STATE_SIZE; j++){
                mEnemyForceWord[i][j] = new Panel(mProgramImage,mProgramSolidColor);
            }
        }
        for(int i=0; i<ConstMgr.OURFORCE_SIZE; i++){
            mOurForce[i].addObject(mOurForceEnergy[i], mOurForceWord[i]);
            mOurForce[i].setIsActiveAll(true);
        }
        for(int i=0; i<ConstMgr.ENEMYFORCE_SIZE; i++){
            mEnemyForce[i].addObject(mEnemyForceEnergy[i], mEnemyForceWord[i]);
            mEnemyForce[i].setIsActiveAll(true);
        }
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
        mResourceLoading.loadingResource(mOurForce, mOurForceEnergy, mOurForceWord,
                mEnemyForce, mEnemyForceEnergy, mEnemyForceWord, Map.mLand, mButtons);
        //mMan.setIsActive(true);
        //mMan.setToBlock(10, 10);

        for(int i=0; i<ConstMgr.OURFORCE_SIZE; i++){
            mOurForce[i].setIsActiveAll(true);
            mOurForce[i].setPosBlockAll(19, i);
        }
        for(int i=0; i<ConstMgr.ENEMYFORCE_SIZE; i++){
            mEnemyForce[i].setIsActiveAll(true);
            mEnemyForce[i].setPosBlockAll(0,i);
        }

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
        */
    }

    private void init(){
        Map.getMap();
        initResource();
        initObject();
        initIntro();
    }
    private void initResource(){
        float scale = mContext.getResources().getDisplayMetrics().density;
        mResourceLoading = new ResourceLoading(mActivity, mContext, scale);
        mResourceLoading.initResource();
    }
    // 초기객체 생성
    private void initObject() {
        mIsDraw = false;
        // 초기화면
        mRyubiIntro = new Man(mProgramImage, mProgramSolidColor, this);
        mRyubiIntro.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mRyubiIntro.setIsActive(true);
        mRyubiIntro.setPos(ConstMgr.SCREEN_WIDTH * 2 / 4, ConstMgr.SCREEN_HEIGHT * 1 / 3);
        mJangbiIntro = new Man(mProgramImage, mProgramSolidColor, this);
        mJangbiIntro.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mJangbiIntro.setPos(ConstMgr.SCREEN_WIDTH * 3 / 4, ConstMgr.SCREEN_HEIGHT * 1 / 3);
        mJangbiIntro.setIsActive(true);
        mGwanuIntro = new Man(mProgramImage, mProgramSolidColor, this);
        mGwanuIntro.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mGwanuIntro.setPos(ConstMgr.SCREEN_WIDTH * 1 / 4, ConstMgr.SCREEN_HEIGHT * 1 / 3);
        mGwanuIntro.setIsActive(true);
        mSubject = new Panel(mProgramImage, mProgramSolidColor);
        mSubject.setIsActive(true);
        mSubject.setPos(ConstMgr.SCREEN_WIDTH * 1 / 2, ConstMgr.SCREEN_HEIGHT * 1 / 2);
        mBtnEnter = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnEnter.setIsActive(true);
        mBtnEnter.setPos(ConstMgr.SCREEN_WIDTH / 2, 100 + 10);
        // 지도화면
        mPanelMap = new Panel(mProgramImage, mProgramSolidColor);
        mPanelMap.setIsActive(true);
        mPanelMap.setPos(1000, 600);
        for (int i = 0;i < ConstMgr.CITY_SIZE;i++) {
            mCity[i] = new Button(mProgramImage, mProgramSolidColor, this);
            mCity[i].setIsActive(true);
            mCityName[i] = new Panel(mProgramImage, mProgramSolidColor);
            mCityName[i].setIsActive(true);
            if (i < 5)
                mCity[i].setPos(ConstMgr.SCREEN_WIDTH * (i + 1) / 6, ConstMgr.SCREEN_HEIGHT * 2 / 5);
            else
                mCity[i].setPos(ConstMgr.SCREEN_WIDTH * (i + 1 - 5) / 6, ConstMgr.SCREEN_HEIGHT * 4 / 6);
            if (i < 5)
                mCityName[i].setPos(ConstMgr.SCREEN_WIDTH * (i + 1) / 6, ConstMgr.SCREEN_HEIGHT * 2 / 5);
            else
                mCityName[i].setPos(ConstMgr.SCREEN_WIDTH * (i + 1 - 5) / 6, ConstMgr.SCREEN_HEIGHT * 4 / 6);
        }
        mChochoMap = new Man(mProgramImage, mProgramSolidColor, this);
        mChochoMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mChochoMap.setIsActive(true);
        mHahudonMap = new Man(mProgramImage, mProgramSolidColor, this);
        mHahudonMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mHahudonMap.setIsActive(true);
        mJanghabMap = new Man(mProgramImage, mProgramSolidColor, this);
        mJanghabMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mJanghabMap.setIsActive(true);
        mRyubiMap = new Man(mProgramImage, mProgramSolidColor, this);
        mRyubiMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mRyubiMap.setIsActive(true);
        mGwanuMap = new Man(mProgramImage, mProgramSolidColor, this);
        mGwanuMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mGwanuMap.setIsActive(true);
        mJangbiMap = new Man(mProgramImage, mProgramSolidColor, this);
        mJangbiMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mJangbiMap.setIsActive(true);
        mChochoMap.setPos(ConstMgr.SCREEN_WIDTH * 5 / 6, ConstMgr.SCREEN_HEIGHT * 4 / 6 + 150);
        mHahudonMap.setPos(ConstMgr.SCREEN_WIDTH * 2 / 6, ConstMgr.SCREEN_HEIGHT * 4 / 6 + 150);
        mJanghabMap.setPos(ConstMgr.SCREEN_WIDTH * 4 / 6, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
        mRyubiMap = new Man(mProgramImage, mProgramSolidColor, this);
        mRyubiMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mRyubiMap.setIsActive(true);
        mJangbiMap = new Man(mProgramImage, mProgramSolidColor, this);
        mJangbiMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mJangbiMap.setIsActive(true);
        mGwanuMap = new Man(mProgramImage, mProgramSolidColor, this);
        mGwanuMap.setProperty(ConstMgr.TYPE_GENERAL, ConstMgr.KIND_OUR, 0);
        mGwanuMap.setIsActive(true);
        mRyubiMap.setPos(-50, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
        mGwanuMap.setPos(-50, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
        mJangbiMap.setPos(-50, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
        int step = 1;
        if (step <= 5) {
            mRyubiMap.moveTo(ConstMgr.SCREEN_WIDTH * step / 6, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
            mGwanuMap.moveTo(ConstMgr.SCREEN_WIDTH * step / 6 + 50, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
            mJangbiMap.moveTo(ConstMgr.SCREEN_WIDTH * step / 6 - 50, ConstMgr.SCREEN_HEIGHT * 2 / 5 + 150);
        }
        else {
            mRyubiMap.moveTo(ConstMgr.SCREEN_WIDTH * step / 6, ConstMgr.SCREEN_HEIGHT * 4 / 6 + 150);
            mGwanuMap.moveTo(ConstMgr.SCREEN_WIDTH * step / 6 + 50, ConstMgr.SCREEN_HEIGHT * 4 / 6 + 150);
            mJangbiMap.moveTo(ConstMgr.SCREEN_WIDTH * step / 6 - 50, ConstMgr.SCREEN_HEIGHT * 4 / 6 + 150);
        }
        mBtnExit = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnExit.setIsActive(true);
        mBtnExit.setPos(ConstMgr.SCREEN_WIDTH - 100 - 10, 100 + 10);

        // 전투화면
        for(int i=0; i< ConstMgr.OURFORCE_SIZE; i++){
            mOurForce[i] = new Man(mProgramImage, mProgramSolidColor, this);
            mOurForce[i].setIsActive(false);
            mOurForceEnergy[i] = new Panel(mProgramImage, mProgramSolidColor);
        }
        for(int i=0; i< ConstMgr.ENEMYFORCE_SIZE; i++){
            mEnemyForce[i] = new Man(mProgramImage, mProgramSolidColor, this);
            mEnemyForce[i].setIsActive(false);
            mEnemyForceEnergy[i] = new Panel(mProgramImage, mProgramSolidColor);
        }
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                Map.mLand[i][j] = new Panel(mProgramImage, mProgramSolidColor);
            }
        }
        for (int i = 0; i < ConstMgr.OURFORCE_SIZE; i++) {
            for (int j = 0; j < ConstMgr.STATE_SIZE; j++) {
                mOurForceWord[i][j] = new Panel(mProgramImage, mProgramSolidColor);
            }
        }
        for (int i = 0; i < ConstMgr.ENEMYFORCE_SIZE; i++) {
            for (int j = 0; j < ConstMgr.STATE_SIZE; j++) {
                mEnemyForceWord[i][j] = new Panel(mProgramImage, mProgramSolidColor);
            }
        }
        for (int i = 0; i < ConstMgr.OURFORCE_SIZE; i++) {
            mOurForce[i].addObject(mOurForceEnergy[i], mOurForceWord[i]);
            mOurForce[i].setIsActive(true);   /*false --> true*/
        }
        for (int i = 0; i < ConstMgr.ENEMYFORCE_SIZE; i++) {
            mEnemyForce[i].addObject(mEnemyForceEnergy[i], mEnemyForceWord[i]);
            mEnemyForce[i].setIsActive(true);
        }
        // 맵관련설정
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                Map.mLand[i][j].setPos(Map.getPosX(i, j), Map.getPosY(i, j));
                Map.mLand[i][j].setIsActive(true);
            }
        }
        for(int i=0; i< ConstMgr.TREEHOUSE_SIZE; i++){
            mTreeHouse[i] = new Panel(mProgramImage, mProgramSolidColor);
        }

        // 전투화면의 버튼 객체를 생성함
        mBtnSwordMan = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnSwordMan.setPos(20 + 500, 110);
        mBtnSwordMan.setIsActive(true);

        mBtnSpearMan = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnSpearMan.setPos(20 + 700, 110);
        mBtnSpearMan.setIsActive(true);

        mBtnBowMan = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnBowMan.setPos(20 + 900, 110);
        mBtnBowMan.setIsActive(true);

        mBtnShieldMan = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnShieldMan.setPos(20 + 1100, 110);
        mBtnShieldMan.setIsActive(true);

        mBtnJangbi = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnJangbi.setPos(20 + 1300, 110);
        mBtnJangbi.setIsActive(true);

        mBtnGwanu = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnGwanu.setPos(20 + 1500, 110);
        mBtnGwanu.setIsActive(true);

        mBtnExitGame = new Button(mProgramImage, mProgramSolidColor, this);
        mBtnExitGame.setPos(2000 - 100 - 10, 110);
        mBtnExitGame.setIsActive(true);

        mIsDraw = true;
    }

    private void initIntro(){
        mIsDraw = false;
        ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_INTRO;
        mResourceLoading.setResourceIntro(mSubject,mRyubiIntro, mGwanuIntro, mJangbiIntro, mBtnEnter);
        mIsDraw = true;
    }
    private void initMap(){
        mIsDraw = false;
        ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_MAP;
        mResourceLoading.setResourceMap(mPanelMap, mCity, mCityName, mRyubiMap, mGwanuMap, mJangbiMap,
                mChochoMap, mHahudonMap, mJanghabMap, mBtnExit);

        int step = 1;
        if(step == 1){
            mRyubiMap.setPos(mCity[step-1].mPosX,mCity[step-1].mPosY + 150 );
            mGwanuMap.setPos(mCity[step-1].mPosX -50,mCity[step-1].mPosY+ 150 );
            mJangbiMap.setPos(mCity[step-1].mPosX + 50,mCity[step-1] .mPosY+ 150 );
        }
        else {
            mRyubiMap.setPos(mCity[step - 2].mPosX, mCity[step - 2].mPosY + 150);
            mGwanuMap.setPos(mCity[step - 2].mPosX - 50, mCity[step - 2].mPosY + 150);
            mJangbiMap.setPos(mCity[step - 2].mPosX + 50, mCity[step - 2].mPosY + 150);
            mRyubiMap.moveTo(mCity[step - 1].mPosX, mCity[step - 1].mPosY + 150);
            mGwanuMap.moveTo(mCity[step - 1].mPosX - 50, mCity[step - 1].mPosY + 150);
            mJangbiMap.moveTo(mCity[step - 1].mPosX + 50, mCity[step - 1].mPosY + 150);
        }

        mIsDraw = true;
    }
    // 게임화면을 초기화한다.
    private void initGame(int step) {
        mIsDraw = false;
        mTreeHouseCount = 0;

        ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_GAME;
        Map.mStep = step;
        Map.setStep(Map.mStep);
        // 맵을 초기화한다.
        // 병사들을 초기화한다.
        /*
        for(int i=0;i< ConstMgr.OURFORCE_SIZE;i++){
            mOurForce[i].setProperty(i, ConstMgr.KIND_OUR, i);
            mOurForce[i].setIsActiveAll(true);
        }
        for(int i=0;i< ConstMgr.ENEMYFORCE_SIZE;i++){
            mEnemyForce[i].setProperty(i, ConstMgr.KIND_ENEMY, i);
            mEnemyForce[i].setIsActiveAll(true);
        }
        */
        for(int i=0;i< ConstMgr.OURFORCE_SIZE;i++) {
            mOurForce[i].setIsActiveAll(false);
        }
        for(int i=0;i< ConstMgr.ENEMYFORCE_SIZE;i++) {
            mEnemyForce[i].setIsActiveAll(false);
            mEnemyForce[i].setOrder(ConstMgr.ORDER_DEFENCE);
        }
        for(int i=0; i< Map.mInfoSizeRow; i++){
            for(int j=0; j<Map.mInfoSizeCol; j++){
                if(Map.mLandForceInfo[i][j][0] == ConstMgr.KIND_ENEMY){
                    mEnemyForce[Map.mLandForceInfo[i][j][1]].setIsActiveAll(true);
                    mEnemyForce[Map.mLandForceInfo[i][j][1]].setProperty(
                            Map.mLandForceInfo[i][j][2],
                            ConstMgr.KIND_ENEMY,
                            Map.mLandForceInfo[i][j][1]
                    );
                }
            }
        }

        // 타입에 맞게 유닛의 이미지를 설정한다.
        mResourceLoading.setResourceGame(mOurForce, mOurForceEnergy, mOurForceWord,
                mEnemyForce, mEnemyForceEnergy, mEnemyForceWord, Map.mLand, mTreeHouse,
                mBtnExitGame,mBtnSwordMan, mBtnSpearMan, mBtnBowMan, mBtnShieldMan, mBtnJangbi, mBtnGwanu);

        for(int i=0; i<Map.mInfoSizeRow; i++){
            for(int j=0;j<Map.mInfoSizeCol; j++){
                Map.mLand[i][j].setPos(Map.getPosX(i,j), Map.getPosY(i,j));
                Map.mLand[i][j].setIsActive(true);
                if(Map.mInfo[i][j] >= ConstMgr.MAP_TREE1 &&
                   Map.mInfo[i][j] <= ConstMgr.MAP_HOUSE3) {
                    mTreeHouse[mTreeHouseCount].setPosBottomX(Map.getPosX(i, j));
                    mTreeHouse[mTreeHouseCount].setPosBottomY(Map.getPosY(i, j));
                    mTreeHouse[mTreeHouseCount].setIsActive(true);
                    mTreeHouseCount++;

                }
            }
        }
        /*
        for(int i=0;i< ConstMgr.OURFORCE_SIZE;i++) {
            mOurForce[i].setIsActiveAll(true);
            mOurForce[i].setPosBlockAll(19, i);
        }
        for(int i=0;i< ConstMgr.ENEMYFORCE_SIZE;i++) {
            mEnemyForce[i].setIsActiveAll(true);
            mEnemyForce[i].setPosBlockAll(0, i);
        }
        mOurForce[8].moveToBlockAll(19-5,5);
        mEnemyForce[8].moveToBlockAll(5,5);
        */
        for(int i=0; i< Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                if(Map.mLandForceInfo[i][j][0] == ConstMgr.KIND_ENEMY){
                    mEnemyForce[Map.mLandForceInfo[i][j][1]].setPosBlockAll(i,j);
                }
            }
        }
        mOurForce[mOurForceIndexCount].setProperty(ConstMgr.TYPE_GENERAL1,
                ConstMgr.KIND_OUR,
                mOurForceIndexCount);
        mOurForce[mOurForceIndexCount].setPosBlockAll(17,10);
        mOurForce[mOurForceIndexCount].setBitmap(ResourceLoading.mHandleOurForceGeneral3,
                ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
        mOurForce[mOurForceIndexCount].setIsActiveAll(true);
        mOurForceIndexCount++;

        mIsDraw = true;
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
        if(mIsDraw == false)
            return;
        mRyubiIntro.thinkSimple();
        mGwanuIntro.thinkSimple();
        mJangbiIntro.thinkSimple();

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1);
        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f, ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        mSubject.draw(m);
        mRyubiIntro.draw(m);
        mGwanuIntro.draw(m);
        mJangbiIntro.draw(m);
        mBtnEnter.draw(m);
    }

    // 맵화면 랜더링
    private void RenderMap(float[] m) {
        if(mIsDraw == false)
            return;
        mChochoMap.thinkSimple();
        mHahudonMap.thinkSimple();
        mJanghabMap.thinkSimple();
        mRyubiMap.thinkSimple();
        mGwanuMap.thinkSimple();
        mJangbiMap.thinkSimple();

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);
        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f, ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);

        mPanelMap.draw(m);
        for(int i =0; i< ConstMgr.CITY_SIZE; i++){
            mCity[i].draw(m);
            mCityName[i].draw(m);
        }
        mChochoMap.draw(m);
        mHahudonMap.draw(m);
        mJanghabMap.draw(m);
        mRyubiMap.draw(m);
        mGwanuMap.draw(m);
        mJangbiMap.draw(m);

        mBtnExit.draw(m);
        //mMan.draw(mMtrxProjectionAndView);
    }

    // 그리기 시작
    private void RenderGame(float[] m) {
        if(mIsDraw == false)
            return;
        //mLand.think();
        //mMan.think();
        for(int i=0; i< ConstMgr.OURFORCE_SIZE; i++){
            mOurForce[i].think();
        }
        for(int i=0; i< ConstMgr.ENEMYFORCE_SIZE; i++){
            mEnemyForce[i].think();
        }
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
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                Map.mLand[i][j].draw(m);
            }
        }
        /*
        for(int i=0; i< mTreeHouseCount; i++){
            mTreeHouse[i].draw(m);
        }
        //mMan.draw(mMtrxProjectionAndView);

        for(int i=0; i< ConstMgr.OURFORCE_SIZE; i++){
            mOurForce[i].drawAll(m);
        }
        for(int i=0; i< ConstMgr.ENEMYFORCE_SIZE; i++){
            mEnemyForce[i].drawAll(m);
        }
        */
        for(int i=0; i< Map.mInfoSizeRow; i++ ){
            for(int j=0; j< Map.mInfoSizeCol; j++){
                if(Map.mLandForceInfo[i][j][0] == ConstMgr.KIND_OUR){
                    if(mOurForce[Map.mLandForceInfo[i][j][1]].getIsActive()== false){
                        Map.setForceInfo(i,j,0,0);
                    }
                    else{
                        mOurForce[Map.mLandForceInfo[i][j][1]].drawAll(m);
                    }
                }
                else if(Map.mLandForceInfo[i][j][0] == ConstMgr.KIND_ENEMY){
                    if(mEnemyForce[Map.mLandForceInfo[i][j][1]].getIsActive()== false){
                        Map.setForceInfo(i,j,0,0);
                    }
                    else{
                        mEnemyForce[Map.mLandForceInfo[i][j][1]].drawAll(m);
                    }
                }
                else if(Map.mLandForceInfo[i][j][0] == 3){  // 나무, 가옥
                    mTreeHouse[Map.mLandForceInfo[i][j][1]].draw(m);
                }
            }
        }

        Matrix.orthoM(mMtrxProjection, 0, 0f, ConstMgr.SCREEN_WIDTH, 0.0f,
                ConstMgr.SCREEN_HEIGHT, 0, 50);
        Matrix.multiplyMM(mMtrxProjectionAndView, 0, mMtrxProjection, 0, mMtrxView, 0);
        /*
        for (int i = 0; i < 4; i++){
            mButtons[i].draw(mMtrxProjectionAndView);
        }
        */
        mBtnSwordMan.draw(m);
        mBtnSpearMan.draw(m);
        mBtnBowMan.draw(m);
        mBtnShieldMan.draw(m);
        mBtnJangbi.draw(m);
        mBtnGwanu.draw(m);
        mBtnExitGame.draw(m);

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
        /*
        if(mMan.isSelected(x,y)== true){
            Log.e("", "intro");
            ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_MAP;
        }
        */
        if(mIsDraw == false)
            return;
        if(mBtnEnter.isSelected(x,y) == true) {
            initMap();
        }
    }
    private void selectTouchMap(int x, int y){
        if(mIsDraw == false)
            return;

        if(mBtnExit.isSelected(x,y) == true){
            mActivity.finish();
        }
        for(int i=0; i<ConstMgr.CITY_SIZE; i++){
            if(mCity[i].isSelected(x,y) == true){
                initGame(i+1);
            }
        }
        /*
        if(mMan.isSelected(x,y)== true){
            Log.e("", "selectTouchMap");
            ConstMgr.SCREEN_MODE = ConstMgr.SCREEN_GAME;
        }
        */
        //initGame(1);
    }
    private void selectTouchGame(int x, int y){

        int ratioX = (int)((mMoveInputX + x) * mSizeRatio);
        int ratioY = (int)((mMoveInputY + y) * mSizeRatio);

        if (mBtnExitGame.isSelected(x, y) == true) {
            initMap();  // 맵화면으로 이동
        }
        else if (mBtnSpearMan.isSelected(x,y) == true){
            mSelectedButtonIndex = ConstMgr.TYPE_SPEAR_MAN;
            //mOurForce[0].moveToPosBlock(5,5);
        }
        else if (mBtnSwordMan.isSelected(x,y) == true){
            mSelectedButtonIndex = ConstMgr.TYPE_SWORD_MAN;
            //mOurForce[0].moveToPosBlock(5,15);
        }
        else if (mBtnBowMan.isSelected(x,y) == true){
            mSelectedButtonIndex = ConstMgr.TYPE_BOW_MAN;
            //mOurForce[0].moveToPosBlock(15,5);
        }
        else if (mBtnShieldMan.isSelected(x,y) == true){
            mSelectedButtonIndex = ConstMgr.TYPE_SHIELD_MAN;
            //mOurForce[0].moveToPosBlock(15,15);
        }
        else if (mBtnJangbi.isSelected(x,y) == true){
            mSelectedButtonIndex = ConstMgr.TYPE_GENERAL1;
            //mOurForce[0].moveToPosBlock(10,10);
        }
        else if (mBtnGwanu.isSelected(x,y) == true){
            mSelectedButtonIndex = ConstMgr.TYPE_GENERAL2;
            //mOurForce[0].moveToPosBlock(0,0);
        }
        else { // 맵의 타일을 터치할 경우
            if(mSelectedButtonIndex > -1){ // 버튼을 터치한 경우 -1이면 터치하지 않았다.
                int selectedRow = -1;
                int selectedCol = -1;
                for (int i = 0; i < Map.mInfoSizeRow; i++) {
                    for (int j = 0; j < Map.mInfoSizeCol; j++) {
                        if (Map.mLand[i][j].isSelected(ratioX, ratioY)) {
                            if(Map.mInfo[i][j] == 0 && Map.mLandForceInfo[i][j][0] == 0) {
                                selectedRow = i;
                                selectedCol = j;
                                addOurForce(mSelectedButtonIndex, selectedRow, selectedCol);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    // 터치시 아군 추가
    private void addOurForce(int type, int row, int col){
        int tempCount = 0;
        while(true) {
            tempCount++;
            if(tempCount == 100){
                return;
            }
            if(mOurForceIndexCount == 100){
                mOurForceIndexCount = 0;
            }
            if(mOurForce[mOurForceIndexCount].getIsActive() == false) {
                mOurForce[mOurForceIndexCount].setProperty(type, ConstMgr.KIND_OUR,
                        mOurForceIndexCount);
                mOurForce[mOurForceIndexCount].setPosBlockAll(row, col);
                if (type == ConstMgr.TYPE_SPEAR_MAN) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceSpearMan, ConstMgr.FORCE_WIDTH,
                            ConstMgr.FORCE_HEIGHT);
                }
                else if (type == ConstMgr.TYPE_SWORD_MAN) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceSwordMan, ConstMgr.FORCE_WIDTH,
                            ConstMgr.FORCE_HEIGHT);
                }
                else if (type == ConstMgr.TYPE_BOW_MAN) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceBowMan, ConstMgr.FORCE_WIDTH,
                            ConstMgr.FORCE_HEIGHT);
                }
                else if (type == ConstMgr.TYPE_SHIELD_MAN) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceBowMan, ConstMgr.FORCE_WIDTH,
                            ConstMgr.FORCE_HEIGHT);
                }
                else if (type == ConstMgr.TYPE_GENERAL1) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceGeneral1, ConstMgr.GENERAL_WIDTH,
                            ConstMgr.GENERAL_HEIGHT);
                }
                else if (type == ConstMgr.TYPE_GENERAL2) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceGeneral2, ConstMgr.GENERAL_WIDTH,
                            ConstMgr.GENERAL_HEIGHT);
                }
                else if (type == ConstMgr.TYPE_GENERAL3) {
                    mOurForce[mOurForceIndexCount].setBitmap(
                            ResourceLoading.mHandleOurForceGeneral3, ConstMgr.GENERAL_WIDTH,
                            ConstMgr.GENERAL_HEIGHT);
                }
                mOurForce[mOurForceIndexCount].setIsActiveAll(true);
                // 가까운 적찾기
                /*
                int[] posBlock = mOurForce[mOurForceIndexCount].findNearEnemyPosBlock();
                mOurForce[mOurForceIndexCount].moveToPosBlock(posBlock[0],  posBlock[1]);
                */
                mOurForce[mOurForceIndexCount].setOrder(ConstMgr.ORDER_ATTACK);
                mOurForceIndexCount++;
                return;

            }
            else{
                mOurForceIndexCount++;
            }
        }
    }
    //주변의 적을 공격함
    public void attackNear(int kind, int index, int attackPoint){
        if(kind==ConstMgr.KIND_OUR){
            mOurForce[index].attacked(attackPoint);

        }
        else if(kind == ConstMgr.KIND_ENEMY){
            mEnemyForce[index].attacked(attackPoint);
        }
    }

}
