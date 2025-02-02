package com.lbo.book.threekingdomstouchmakeman;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;

// 리소스 관리
public class ResourceLoading {
    private Context mContext;
    private Activity mActivity;

    // 초기화면
    private static int[] mHandleRyubi = new int[4];
    private static int[] mHandleGwanu = new int[4];
    private static int[] mHandleJangbi = new int[4];
    private static int mHandleSubject;
    private static int mHandleBtnEnter;

    // 지도화면
    private static int[] mHandleChocho = new int[4];
    private static int[] mHandleHahudon = new int[4];
    private static int[] mHandleJanghab = new int[4];
    private static int mHandleBtnExit;
    private static int mHandleMapPanel;
    private static int mHandleCity;
    private static int[] mHandleCityName = new int[ConstMgr.CITY_SIZE];
    private static float[] mHandleCityNameLength = new float [ConstMgr.CITY_SIZE];
    private static String[] strCityName =
            {"천수", "안정", "무위", "장안", "남양", "함곡관", "낙양", "업", "여남", "허창"};

    // 전투화면 - 버튼 추가
    private static int mHandleBtnSwordMan;
    private static int mHandleBtnSpearMan;
    private static int mHandleBtnBowMan;
    private static int mHandleBtnShieldMan;
    private static int mHandleBtnJangbi;
    private static int mHandleBtnGwanu;

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

    //private static int mHandleLand;

    private static int[] mHandleMap = new int[ConstMgr.MAP_TREEHOUSE_SIZE];
    private static int mTreeHouseCount;

    private static int[] mHandleButtons = new int[4];

    private static int mHandleEnergyBar;
    private static int[] mHandleStateWord = new int[ConstMgr.STATE_SIZE];
    private static float[] mHandleStateWordLength = new float [ConstMgr.STATE_SIZE];
    private static String[] strStateWord = { "명령을 내려주세요", "부대 이동~~", "방어하라.(위치고수)", "가까운 적을 찾는 중...", "싸우는 중(얍얍!!)", "윽!!",  "도착완료!", "그곳으로 갈 수 없어요.", "비켜!!"};
    private HangulBitmap mHangulBitmap;

    // 화면확대축소관리
    private float mScale = 0;

    // 리소스로딩 생성자
    public ResourceLoading(Activity activity, Context context, float scale){
        mActivity = activity;
        mContext = context;
        mScale = scale;
        mHangulBitmap = new HangulBitmap(mActivity);
    }
    // 리소스 초기화
    public  void initResource() {
        int fontHeight = 48;
        // 초기화면
        // 유비 이미지 로딩
        Bitmap[] bmpRyubi = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpRyubi[i] = BitmapFactory.decodeResource(mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/ryubi" + (i + 1), null,mContext.getPackageName()));
        mHandleRyubi = getImageHandle(bmpRyubi);
        for (int i = 0; i < 4; i++)
            bmpRyubi[i].recycle();
        // 관우 이미지 로딩
        Bitmap[] bmpGwanu = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpGwanu[i] = BitmapFactory.decodeResource(mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/gwanu" + (i + 1), null, mContext.getPackageName()));
        mHandleGwanu = getImageHandle(bmpGwanu);
        for (int i = 0; i < 4; i++)
            bmpGwanu[i].recycle();
        // 장비 이미지 로딩
        Bitmap[] bmpJangbi = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpJangbi[i] = BitmapFactory.decodeResource(mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/jangbi" + (i + 1), null, mContext.getPackageName()));
        mHandleJangbi = getImageHandle(bmpJangbi);
        for (int i = 0; i < 4; i++)
            bmpJangbi[i].recycle();
        // 초기화면제목 이미지 로딩
        Bitmap bmpSubject = BitmapFactory.decodeResource(mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/title", null, mContext.getPackageName()));
        mHandleSubject = getImageHandle(bmpSubject);
        bmpSubject.recycle();
        // 입장 버튼 이미지
        Bitmap bmpBtnEnter = BitmapFactory.decodeResource(mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/button_enter", null, mContext.getPackageName()));
        mHandleBtnEnter = getImageHandle(bmpBtnEnter);
        bmpBtnEnter.recycle();        
        // 지도화면
    // 조조 이미지 로딩
        Bitmap[] bmpChocho = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpChocho[i] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/chocho" + (i + 1), null, mContext.getPackageName()));
        mHandleChocho = getImageHandle(bmpChocho);
        for (int i = 0; i < 4; i++)
            bmpChocho[i].recycle();

        // 하후돈 이미지 로딩
        Bitmap[] bmpHahudon = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpHahudon[i] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/hahudon" + (i + 1), null, mContext.getPackageName()));
        mHandleHahudon = getImageHandle(bmpHahudon);
        for (int i = 0; i < 4; i++)
            bmpHahudon[i].recycle();

        // 장합 이미지 로딩
        Bitmap[] bmpJanghab = new Bitmap[4];
        for (int i = 0; i < 4; i++)
            bmpJanghab[i] = BitmapFactory.decodeResource(
                    mContext.getResources(),
                    mContext.getResources().getIdentifier("drawable/janghab" + (i + 1), null, mContext.getPackageName()));
        mHandleJanghab = getImageHandle(bmpJanghab);
        for (int i = 0; i < 4; i++)
            bmpJanghab[i].recycle();
        // 버튼 나가기
        Bitmap bmpBtnExit = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/button_exit", null, mContext.getPackageName()));
        mHandleBtnExit = getImageHandle(bmpBtnExit);
        bmpBtnExit.recycle();

        // 맵 배경
        Bitmap bmpMapPanel = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/map_panel", null, mContext.getPackageName()));
        mHandleMapPanel = getImageHandle(bmpMapPanel);
        bmpMapPanel.recycle();

        // 맵 시티
        Bitmap bmpCity = BitmapFactory.decodeResource(
                mContext.getResources(),
                mContext.getResources().getIdentifier("drawable/rect", null, mContext.getPackageName()));
        mHandleCity = getImageHandle(bmpCity);
        bmpCity.recycle();

        // 도시명
        Bitmap bmpCityName[] = new Bitmap[ConstMgr.CITY_SIZE];
        for (int i = 0; i < ConstMgr.CITY_SIZE; i++) {
            bmpCityName[i] = Bitmap.createBitmap((int) (fontHeight * strCityName[i].length()), (int) (fontHeight), Bitmap.Config.ARGB_8888);
            mHandleCityNameLength[i] = mHangulBitmap.GetBitmap(bmpCityName[i], strCityName[i], fontHeight, Color.WHITE, -1, mScale);
        }
        mHandleCityName = getImageHandle(bmpCityName);
        for (int i = 0; i < ConstMgr.CITY_SIZE; i++)
            bmpCityName[i].recycle();


        // 전투화면
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
        // 에너지
        Bitmap bmpEnergyBar = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/energy_bar", null, mContext.getPackageName()));
        mHandleEnergyBar = getImageHandle(bmpEnergyBar);
        bmpEnergyBar.recycle();
        // 전투 맵
        /*
        Bitmap bmpLand = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/land_green", null, mContext.getPackageName()));
        mHandleLand = getImageHandle(bmpLand);
        bmpLand.recycle();
        */
// 맵관련 자원

        Bitmap[] bmpMap = new Bitmap[ConstMgr.MAP_TREEHOUSE_SIZE];
        bmpMap[ConstMgr.MAP_LAND_GREEN] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/land_green", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_LAND_WATER] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/land_water", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_TREE1] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/tree1", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_TREE2] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/tree2", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_TREE3] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/tree3", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_TREE4] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/tree4", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_HOUSE1] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/house1", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_HOUSE2] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/house2", null, mContext.getPackageName()));
        bmpMap[ConstMgr.MAP_HOUSE3] = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/house3", null, mContext.getPackageName()));
        mHandleMap = getImageHandle(bmpMap);
        for (int i = 0; i < ConstMgr.MAP_TREEHOUSE_SIZE; i++)
            bmpMap[i].recycle();



        // 대화문구

        Bitmap[] bmpStateWord = new Bitmap[ConstMgr.STATE_SIZE];
        for (int i = 0; i < ConstMgr.STATE_SIZE; i++) {
            bmpStateWord[i] = Bitmap.createBitmap((int) (fontHeight * strStateWord[i].length()), (int) (fontHeight), Bitmap.Config.ARGB_8888);
            mHandleStateWordLength[i] = mHangulBitmap.GetBitmap(bmpStateWord[i],
                    strStateWord[i], fontHeight, Color.WHITE, -1, mScale);
        }
        mHandleStateWord = getImageHandle(bmpStateWord);
        for (int i = 0; i < ConstMgr.STATE_SIZE; i++)
            bmpStateWord[i].recycle();
        
        // 버튼 이미지 추가
// 버튼 (검병)
        Bitmap bmpSwordMan = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/button_swordman", null, mContext.getPackageName()));
        mHandleBtnSwordMan = getImageHandle(bmpSwordMan);
        bmpSwordMan.recycle();

        // 버튼 (창병)
        Bitmap bmpBtnSpearMan = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/button_spearman", null, mContext.getPackageName()));
        mHandleBtnSpearMan = getImageHandle(bmpBtnSpearMan);
        bmpBtnSpearMan.recycle();

        // 버튼 (궁병)
        Bitmap bmpBtnBowMan = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/button_bowman", null, mContext.getPackageName()));
        mHandleBtnBowMan = getImageHandle(bmpBtnBowMan);
        bmpBtnBowMan.recycle();

        // 버튼 (방패병)
        Bitmap bmpBtnShieldMan = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/button_shieldman", null, mContext.getPackageName()));
        mHandleBtnShieldMan = getImageHandle(bmpBtnShieldMan);
        bmpBtnShieldMan.recycle();

        // 버튼 (장비)
        Bitmap bmpBtnJangbi = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/button_jangbi", null, mContext.getPackageName()));
        mHandleBtnJangbi = getImageHandle(bmpBtnJangbi);
        bmpBtnJangbi.recycle();

        // 버튼 (관우)
        Bitmap bmpBtnGwanu = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/button_gwanu", null, mContext.getPackageName()));
        mHandleBtnGwanu = getImageHandle(bmpBtnGwanu);
        bmpBtnGwanu.recycle();
        
        
    }
    public void setResourceIntro(Panel subject, Man ryubi, Man gwanu, Man jangbi, Button btnEnter){
        subject.setBitmap(mHandleSubject, 2000, 1200);
        ryubi.setBitmap(mHandleRyubi, 180, 450);
        gwanu.setBitmap(mHandleGwanu,180, 450);
        jangbi.setBitmap(mHandleJangbi, 180, 450);
        btnEnter.setBitmap(mHandleBtnEnter, 200, 100);
    }
// 지도화면 설정

    public void setResourceMap(
            Panel mapPanel, Button[] city, Panel[] cityName,
            Man ryubi, Man gwanu, Man jangbi,
            Man chocho, Man hahudon, Man janghab,
            Button btnExit){

        mapPanel.setBitmap(mHandleMapPanel, 2000,1200);
        int fontHeight = 48;
        for(int i=0; i< ConstMgr.CITY_SIZE; i++){
            cityName[i].setBitmap(mHandleCityName[i], (int) (mHandleCityNameLength[i]), (int) (fontHeight));
            city[i].setBitmap(mHandleCity, 200, 200);
        }
        ryubi.setBitmap(mHandleRyubi, 100, 250);
        gwanu.setBitmap(mHandleGwanu, 100, 250);
        jangbi.setBitmap(mHandleJangbi, 100, 250);
        chocho.setBitmap(mHandleChocho, 100, 250);
        hahudon.setBitmap(mHandleHahudon, 100, 250);
        janghab.setBitmap(mHandleJanghab, 100, 250);
        btnExit.setBitmap(mHandleBtnExit, 200,200);
    }

    // 게임화면 설정
    public void setResourceGame(
            Man[] ourForce, Panel[] ourForceEnergy, Panel[][] ourForceWord,
            Man[] enemyForce, Panel[] enemyForceEnergy, Panel[][] enemyForceWord,
            Panel[][] map, Panel[] treeHouse,
            Button btnExitGame, Button btnSwordMan, Button btnSpearMan, Button btnBowMan, Button btnShieldMan, Button btnJangbi, Button btnGwanu
    ){
// 전투 맵설정
        /*
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                map[i][j].setBitmap(mHandleLand,100, 60);
            }
        }
        */
        mTreeHouseCount = 0;
        for (int i = 0; i < Map.mInfoSizeRow; i++) {
            for (int j = 0; j < Map.mInfoSizeCol; j++) {
                if (Map.mInfo[i][j] == ConstMgr.MAP_LAND_WATER)
                    map[i][j].setBitmap(mHandleMap[ConstMgr.MAP_LAND_WATER], 100, 60);
                else
                    map[i][j].setBitmap(mHandleMap[ConstMgr.MAP_LAND_GREEN], 100, 60);
            }
        }
        // 나무, 가옥설정
        for(int i=0; i<Map.mInfoSizeRow; i++){
            for(int j=0; j<Map.mInfoSizeCol; j++) {
                if(Map.mInfo[i][j] >= ConstMgr.MAP_TREE1 && Map.mInfo[i][j] <= ConstMgr.MAP_HOUSE3){
                    treeHouse[mTreeHouseCount].setBitmap( mHandleMap[Map.mInfo[i][j]], ConstMgr.TREE_WIDTH, ConstMgr.TREE_HEIGHT);
                    mTreeHouseCount++;
                }
            }
        }


        // 대화문구 설정
        int fontHeight = 48;
        for (int i = 0; i < ourForce.length; i++) {
            for (int j = 0; j < ConstMgr.STATE_SIZE; j++) {
                ourForceWord[i][j].setBitmap(mHandleStateWord[j],
                        (int) (mHandleStateWordLength[j]), (int) (fontHeight));
            }
        }
        for (int i = 0; i < enemyForce.length; i++) {
            for (int j = 0; j < ConstMgr.STATE_SIZE; j++) {
                enemyForceWord[i][j].setBitmap(mHandleStateWord[j], (int) (mHandleStateWordLength[j]), (int) (fontHeight));
            }
        }
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
                ourForce[i].setBitmap(mHandleOurForceGeneral, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_BASE){
                ourForce[i].setBitmap(mHandleOurForceBase, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL1){
                ourForce[i].setBitmap(mHandleOurForceGeneral1, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL2){
                ourForce[i].setBitmap(mHandleOurForceGeneral2, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            else if (ourForce[i].mType == ConstMgr.TYPE_GENERAL3){
                ourForce[i].setBitmap(mHandleOurForceGeneral3, ConstMgr.GENERAL_WIDTH, ConstMgr.GENERAL_HEIGHT);
            }
            ourForceEnergy[i].setBitmap(mHandleEnergyBar, ConstMgr.FORCE_ENERGY_WIDTH,ConstMgr.FORCE_ENERGY_HEIGHT );
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
                enemyForce[i].setBitmap(mHandleEnemyForceShieldMan, ConstMgr.FORCE_WIDTH,ConstMgr.FORCE_HEIGHT);
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
            enemyForceEnergy[i].setBitmap(mHandleEnergyBar, ConstMgr.FORCE_ENERGY_WIDTH,ConstMgr.FORCE_ENERGY_HEIGHT);
        }
        btnExitGame.setBitmap(mHandleBtnExit, 200,200);
        btnSwordMan.setBitmap(mHandleBtnSwordMan, 200, 200);
        btnSpearMan.setBitmap(mHandleBtnSpearMan, 200, 200);
        btnBowMan.setBitmap(mHandleBtnBowMan, 200, 200);
        btnShieldMan.setBitmap(mHandleBtnShieldMan, 200, 200);
        btnJangbi.setBitmap(mHandleBtnJangbi, 200, 200);
        btnGwanu.setBitmap(mHandleBtnGwanu, 200, 200);

    }


    /*
    // 리소스 로딩
    public void loadingResource(Man[] ourForce, Panel[] ourForceEnergy, Panel[][] ourForceWord,
                                Man[] enemyForce, Panel[] enemyForceEnergy, Panel[][] enemyForceWord,
                                Panel[][] map, Button[] buttons){

        Bitmap bmpEnergyBar = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/energy_bar", null, mContext.getPackageName()));
        mHandleEnergyBar = getImageHandle(bmpEnergyBar);
        bmpEnergyBar.recycle();



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
            ourForceEnergy[i].setBitmap(mHandleEnergyBar,
                    ConstMgr.FORCE_ENERGY_WIDTH, ConstMgr.FORCE_ENERGY_HEIGHT );
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
            enemyForceEnergy[i].setBitmap(mHandleEnergyBar,
                    ConstMgr.FORCE_ENERGY_WIDTH, ConstMgr.FORCE_ENERGY_HEIGHT );
        }
        // 대화문구
        int fontHeight = 48;
        Bitmap[] bmpStateWord = new Bitmap[ConstMgr.STATE_SIZE];
        for (int i = 0; i < ConstMgr.STATE_SIZE; i++) {
            bmpStateWord[i] = Bitmap.createBitmap((int) (fontHeight * strStateWord[i].length()), (int) (fontHeight), Bitmap.Config.ARGB_8888);
            mHandleStateWordLength[i] = mHangulBitmap.GetBitmap(bmpStateWord[i], strStateWord[i], fontHeight, Color.WHITE, -1, mScale);
        }
        mHandleStateWord = getImageHandle(bmpStateWord);
        for (int i = 0; i < ourForce.length; i++) {
            for (int j = 0; j < ConstMgr.STATE_SIZE; j++) {
                ourForceWord[i][j].setBitmap(mHandleStateWord[j],(int) (mHandleStateWordLength[j]), (int) (fontHeight));
            }
        }
        for (int i = 0; i < enemyForce.length; i++) {
            for (int j = 0; j < ConstMgr.STATE_SIZE; j++) {
                enemyForceWord[i][j].setBitmap(mHandleStateWord[j], (int) (mHandleStateWordLength[j]), (int) (fontHeight));
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
    */
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
