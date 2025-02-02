package com.lbo.book.threekingdomscomplete;

// 버튼 클래스
public class Button extends Unit {
    MainGLRenderer mMainGLRenderer;
    // 버튼 생성자
    public Button(int programImage, int programSolidColor, MainGLRenderer mainGLRenderer) {
        super(programImage, programSolidColor);
        mMainGLRenderer = mainGLRenderer;
    }
    // 선택되었을 경우 부모클래스를 호출하고 효과음만 출력한다.
    public boolean isSelected(int x, int y){
        boolean returnValue = super.isSelected(x, y);
        if(returnValue == true) {
            mMainGLRenderer.mActivity.soundButton();
        }
        return returnValue;
    }
}
