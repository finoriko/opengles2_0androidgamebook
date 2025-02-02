package com.lbo.book.threekingdomsmoveandpinch;

public class Map {
    // 가로, 세로 배열
    public static int mInfoSizeRow = 20;
    public static int mInfoSizeCol = 20;

    // 맵 하나의 크기
    public static int mWidth = 100;
    public static int mHeight = 60;

    // 맵정보 1차원 데이터 - 설정하기 편리하게 1차원으로 관리함.
    public static short[][] mOrgLand = new short[10][400];

    // 맵정보
    public static int[][] mInfo = new int[mInfoSizeRow][mInfoSizeCol];
    public static Panel[][] mLand = new Panel[mInfoSizeRow][mInfoSizeCol];

    public static float getPosX(int i,int j){
        return 1000 - (i* mWidth/2) + (j * mWidth/2);
    }
    public static float getPosY(int i,int j){
        return 1200 - (i* mHeight/2) - (j * mHeight/2) - mHeight/2;
    }
}
