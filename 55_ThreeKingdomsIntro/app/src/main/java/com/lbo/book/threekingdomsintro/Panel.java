package com.lbo.book.threekingdomsintro;
// 기본 패널 설정 - 화면여 보여주는 내용. 
public class Panel extends Unit {
    // 생성자
    public Panel(int programImage, int programSolidColor) {
        super(programImage, programSolidColor);
    }
    // 가로 확대/축소 설정
    public void setScaleX(float scaleX){
        this.mScaleX = scaleX;
    }
    // 이동할 위치 지정
    public void moveTo(int x, int y) {
        mTargetX = x;
        mTargetY = y;
    }
    // 이동을 위해 think 메소드 추가 (구름에 대한 설정)
    public void think() {
        mCount++;
        if (mCount > 30000) {
            mCount = 0;
        }
        if (this.mPosX > this.mTargetX + 10) {
            this.mPosX = this.mPosX - 9;
        }
        else if (this.mPosX < this.mTargetX - 10) {
            this.mPosX = this.mPosX + 9;
        }
        if (this.mPosY > this.mTargetY + 10) {
            this.mPosY = this.mPosY - 9;
        }
        else if (this.mPosY < this.mTargetY - 10) {
            this.mPosY = this.mPosY + 9;
        }
    }
}
