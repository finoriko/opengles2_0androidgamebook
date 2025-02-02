package com.lbo.book.threekingdomsmanenergyandtalk;

/**
 * Created by tomcat2 on 2015-06-03.
 */
public class ConstMgr {
    // 화면크기
    public final static int SCREEN_WIDTH = 2000;
    public final static int SCREEN_HEIGHT = 1200;

    //화면모드
    public final static int SCREEN_INTRO = 1;
    public final static int SCREEN_MAP = 2;
    public final static int SCREEN_GAME = 3;
    public static int SCREEN_MODE = SCREEN_GAME;

    // 병사의 수 관리
    public final static int OURFORCE_SIZE = 9;
    public final static int ENEMYFORCE_SIZE = 9;
    // 병사의 넓이와 높이
    public final static int FORCE_WIDTH = 60;
    public final static int FORCE_HEIGHT = 90;
    public final static int GENERAL_WIDTH = 100;
    public final static int GENERAL_HEIGHT = 150;
    // 병사의 타입
    public final static int TYPE_SPEAR_MAN = 0; // 창병
    public final static int TYPE_SWORD_MAN = 1; // 검병
    public final static int TYPE_BOW_MAN = 2;   // 궁병
    public final static int TYPE_SHIELD_MAN = 3;// 방패병
    public final static int TYPE_GENERAL = 4;   // 장군
    public final static int TYPE_BASE = 5; 	// 기지
    public final static int TYPE_GENERAL1 = 6;  // 장비, 장합
    public final static int TYPE_GENERAL2 = 7;  // 관우, 하후돈
    public final static int TYPE_GENERAL3 = 8;  // 유비, 조조

    public final static int FORCE_ENERGY_WIDTH = 60;
    public final static int FORCE_ENERGY_HEIGHT = 10;// 병사의 상태관리
    public final static int STATE_SIZE = 9;

    public final static int STATE_NORMAL = 0;// 기본
    public final static int STATE_MOVE = 1;// 이동
    public final static int STATE_DEFENCE = 2;// 방어
    public final static int STATE_ATTACK = 3;// 공격!
    public final static int STATE_FIGHT = 4;// 싸우는 중
    public final static int STATE_DEAD = 5;// 철수
    public final static int STATE_ARRIVE = 6;// 도착
    public final static int STATE_FIND_WAY_FAULT = 7;// 길을 찾을수 없어요
    public final static int STATE_REQ_MAKE_WAY = 8;// 비켜

}
