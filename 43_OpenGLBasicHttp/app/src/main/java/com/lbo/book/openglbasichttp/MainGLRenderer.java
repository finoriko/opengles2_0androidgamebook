package com.lbo.book.openglbasichttp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by tomcat2 on 2015-06-02.
 */
public class MainGLRenderer implements GLSurfaceView.Renderer {
    MyHttpConn mMyHttpConn;
    public void onDrawFrame(GL10 unused){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){

    }
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height){
        GLES20.glViewport(0,0, width, height);
        mMyHttpConn = new MyHttpConn("http://www.piece.mireene.com/index.html");
        mMyHttpConn.start();
        Log.e("", mMyHttpConn.getValue());
    }
}
