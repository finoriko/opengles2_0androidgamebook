package com.lbo.book.threekingdomspopup;
// 팝업창 관리 클래스
public class Popup {
    // 팝업창 타입 - ConstMgr 클래스 참조
    private int mType;
    // 활성화여부
    private boolean mIsActive = false;
    // 팝업창 패널
    private Panel mPanel;
    // 팝업창 문구 패널
    private Panel[] mPanelStr;
    // 팝업창 버튼 - 예, 아니오, 확인
    private Button[] mButton = new Button[3];
    // 반환값을 주기 위해 mMainGLRenderer를 참조
    private MainGLRenderer mManGLRenderer;
    // 생성자 - 각 패널과 버튼을 전달받아 참조한다.
    public Popup(MainGLRenderer mainGLRenderer, Panel panel, Panel[] panelStr, Button[] button) {
        mManGLRenderer = mainGLRenderer;
        mPanel = panel;
        mPanelStr = panelStr;
        for (int i = 0; i < ConstMgr.POPUP_TYPE_SIZE; i++){
            mPanelStr[i].setPos(1000, 700);
        }
        mButton = button;
        mPanel.setPos(1000,600);
        mButton[0].setPos(800,500);
        mButton[1].setPos(1200, 500);
        mButton[2].setPos(1000, 500);
    }
    // 타입설정 - ConstMgr 참조
    public void setType(int type){
        mType = type;
    }
    // 타입을 반환한다.
    public int getType(){
        return mType;
    }
    // 활성화를 설정한다.문자열은 활성화 하고 버튼은 팝업의 종류마다 활성화 할 버튼을 다르게 설정한다.
    public void setIsActive(boolean isActive) {
        mIsActive = isActive;
        mPanel.setIsActive(isActive);
        for(int i=0; i< ConstMgr.POPUP_TYPE_SIZE; i++){
            mPanelStr[i].setIsActive (false);
        }
        if(isActive == true)
            mPanelStr[mType].setIsActive(isActive);
        for(int i=0; i< 3; i++){
            mButton[i].setIsActive(false);
        }
        if(isActive == true) {
            if (mType == ConstMgr.POPUP_TYPE_EXIT_GAME || mType == ConstMgr.POPUP_TYPE_EXIT_MAP) {
                mButton[0].setIsActive(true);
                mButton[1].setIsActive(true);
            }
            else if ( mType == ConstMgr.POPUP_TYPE_CANT_ADD || mType == ConstMgr.POPUP_TYPE_WIN  || mType == ConstMgr.POPUP_TYPE_LOSE || mType == ConstMgr.POPUP_TYPE_ALL_WIN) {
                mButton[2].setIsActive(true);
            }
        }
    }
    // 버튼의 선택여부를 설정한다. 기본적으로 버튼이 터치되면 팝업창을 닫으며
    // mMainGLRenderer의 onPopupResponse 메소드를 호출한다.

    public boolean isSelected(int x, int y) {
        if(mButton[0].isSelected(x, y)== true) {
            mManGLRenderer.onPopupResponse(mType, ConstMgr.POPUP_RES_YES);
            ConstMgr.POPUP_TYPE_MODE = ConstMgr.POPUP_TYPE_NONE;
            this.mType = ConstMgr.POPUP_TYPE_NONE;
        }
        else if(mButton[1].isSelected(x, y)== true) {
            mManGLRenderer.onPopupResponse(mType, ConstMgr.POPUP_RES_NO);
            ConstMgr.POPUP_TYPE_MODE = ConstMgr.POPUP_TYPE_NONE;
            this.mType = ConstMgr.POPUP_TYPE_NONE;
        }
        else if(mButton[2].isSelected(x, y)== true) {
            mManGLRenderer.onPopupResponse(mType, ConstMgr.POPUP_RES_CONFIRM);
            ConstMgr.POPUP_TYPE_MODE = ConstMgr.POPUP_TYPE_NONE;
            this.mType = ConstMgr.POPUP_TYPE_NONE;
        }
        return true;
    }
    // 그리기
    void draw(float[] m) {
        mPanel.draw(m);
        mPanelStr[mType].draw(m);
        for(int i=0; i<3; i++)
            mButton[i].draw(m);
    }
}
