package com.lbo.book.threekingdomsman;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
// Unit으로부터 상속받아 병사를 구현하기 위한 객체

public class Man extends Unit {

    // 병사의 타입 0~8까지 사용한다.
    int mType = 0;
    // 병사의 상태 방어, 이동, 공격, 전투중을 나타낸다.
    int mState = 0;
    // 현재의 에너지를 나타낸다.
    int mCurrentEnergy = 0;
    // 병사의 총 에너지를 나타낸다.
    int mEnergy = 100;
    // 병사의 방어력을 나타낸다.
    int mDefence = 0;
    // 병사의 공격력을 나타낸다.
    int mAttackPoint = 0;
    // 1:아군, 2:적군을 나타낸다.
    int mKind = 0;
    // 병사의 인덱스 값을 나타낸다. 배열로 표현할 예정이므로 자신의 배열 인덱스를 갖는다.
    int mIndex = 0;
    // 병사의 게임맵의 블럭 위치를 나타낸다.
    int mPosBlockRow = 0;
    int mPosBlockCol = 0;
    // MainGLRenderer를 참조한다.
    private MainGLRenderer mMainGLRenderer;

    // 생성자
    public Man(int programImage, int programSolidColor, MainGLRenderer mainGLRenderer) {
        super(programImage, programSolidColor);
        mMainGLRenderer = mainGLRenderer;
        // mCount는 매번 루프를 돌때마다 호출된다.  병사의 움직임에 관여하는데 병사마다
        // 다른 시작점을 줌으로써 각기 다른 움직임을 갖도록 처리한다.
        mCount = (int) (Math.random() * 100);
    }

    // 병사의 속성을 설정한다.타입, 종류, 인덱스번호

    public void setProperty(int type, int kind, int index) {
        mType = type;
        mKind = kind;
        mIndex = index;
        setType(type);
    }

    // 병사의 타입을 설정한다.
    public void setType(int type) {
        mType = type;
        mEnergy = 500;
        mDefence = 10;
        mAttackPoint = 30;
        mCurrentEnergy = mEnergy;
    }

    // 해당좌표로 이동함
    public void moveTo(int x, int y) {
        mTargetX = x;
        mTargetY = y;
    }

    // 해당좌표로 이동함

    public void moveTo(float x, float y) {
        mTargetX = x;
        mTargetY = y;
    }

    // 가로방향 확대축소를 설정함
    public void setScaleX(float scaleX) {
        this.mScaleX = scaleX;
    }

    // 현재 맵의 위치를 반환함

    public int getBlockRow() {
        return mPosBlockRow;
    }
    public int getBlockCol() {
        return mPosBlockCol;
    }

    // 해당 블럭에 위치를 지정함

    public void setToBlock(int row, int col) {
        mPosBlockRow = row;
        mPosBlockCol = col;
        mTargetX = Map.getPosX(row, col);
        mTargetY = Map.getPosY(row, col) + this.mHeight / 3;
        mPosX = mTargetX;
        mPosY = mTargetY;
    }

    // 해당 블럭이로 이동함.

    public void moveToBlock(int row, int col) {
        // 이동하려는 위치중 현재 블럭을 제외함.
        if(row == mPosBlockRow && col == mPosBlockCol){
            return;
        }
        mPosBlockRow = row;
        mPosBlockCol = col;
        mTargetX = Map.getPosX(row, col);
        mTargetY = Map.getPosY(row, col) + this.mHeight / 3;
    }

    // 병사의 생각을 관리함.
    int mCount = 0;
    // 길찾기 알고리즘에서 사용하는 블럭개수 관리

    int mPathCount = 0;

    // 병사가 생각하도록 만드는 함수
    public void think() {
        if(mIsActive == false ){
            return;
        }
        mCount++;
        // 변수형 범위를 넘어설 경우를 대비해 초기화
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

        if (mCount % 100 < 25) {
            this.mBitmapState = 0;
        }
        else if (mCount % 100 < 50) {
            this.mBitmapState = 1;
        }
        else if (mCount % 100 < 75) {
            this.mBitmapState = 2;
        }
        else {
            this.mBitmapState = 3;
        }
    }

    // 병사 객체를 게임이 아닌 초기화면이나 전체 맵에서 사용하기 위해 사용하는 함수

    public void thinkSimple() {
        mCount++;

        if (mCount > 30000) {
            mCount = 0;
        }
        if (this.mPosX > this.mTargetX + 5) {
            this.mPosX = this.mPosX - 4;
        }
        else if (this.mPosX < this.mTargetX - 4) {
            this.mPosX = this.mPosX + 4;
        }
        if (this.mPosY > this.mTargetY + 5) {
            this.mPosY = this.mPosY - 4;
        }
        else if (this.mPosY < this.mTargetY - 5) {
            this.mPosY = this.mPosY + 4;
        }
        if (mCount % 100 < 25) {
            this.mBitmapState = 0;
        }
        else if (mCount % 100 < 50) {
            this.mBitmapState = 1;
        }
        else if (mCount % 100 < 75) {
            this.mBitmapState = 2;
        }
        else {
            this.mBitmapState = 3;
        }
    }

    // 그리기

    void draw(float[] m){

        // 활성화 상태가 아니라면 그리지 않음

        if (mIsActive == false) {
            return;
        }

        // 회전, 가로, 세로 확대/축소를 미리 체크하여 변환모듈 호출을 관리함

        if(this.mAngle != 0) {
            Matrix.setIdentityM(mTranslationMatrix, 0);
            Matrix.setIdentityM(mRotationMatrix, 0);
            Matrix.translateM(mTranslationMatrix, 0, mPosX, mPosY, 0);
            Matrix.setRotateM(mRotationMatrix, 0, this.mAngle, 0, 0, -1.0f);
            Matrix.multiplyMM(mMVPMatrix, 0, m, 0, mTranslationMatrix, 0);
            Matrix.multiplyMM(mMVPMatrix2, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        }
        else if(this.mScaleX != 1.0f || this.mScaleY != 1.0f) {
            Matrix.setIdentityM(mTranslationMatrix, 0);
            Matrix.setIdentityM(mScaleMatrix, 0);
            Matrix.translateM(mTranslationMatrix, 0, mPosX, mPosY, 0);
            Matrix.scaleM(mScaleMatrix, 0, this.mScaleX, this.mScaleY, 1.0f);
            Matrix.multiplyMM(mMVPMatrix, 0, m, 0, mTranslationMatrix, 0);
            Matrix.multiplyMM(mMVPMatrix2, 0, mMVPMatrix, 0, mScaleMatrix, 0);
        }
        else {
            Matrix.setIdentityM(mTranslationMatrix, 0);
            Matrix.translateM(mTranslationMatrix, 0, mPosX, mPosY, 0);
            Matrix.multiplyMM(mMVPMatrix2, 0, m, 0, mTranslationMatrix, 0);
        }
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0,
                mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, mUvBuffer);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mHandleBitmap[mBitmapState]);
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, mMVPMatrix2, 0);
        GLES20.glUniform1i(mSamplerLoc, 0);
        // 투명한 배경을 처리함
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        // 해당 이미지 핸들을 찾아 출력해줌 (think 메소드에서 mBtimapState를
        // 주기적으로 변경함
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mIndices.length, GLES20.GL_UNSIGNED_SHORT, mDrawListBuffer);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
    }
}
