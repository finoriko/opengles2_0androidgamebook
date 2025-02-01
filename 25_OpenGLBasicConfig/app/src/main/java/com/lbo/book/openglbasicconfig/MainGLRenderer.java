package com.lbo.book.openglbasicconfig;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
/**
 * Created by tomcat2 on 2015-06-02.
 */
public class MainGLRenderer implements GLSurfaceView.Renderer {
    public void onDrawFrame(GL10 unused){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){

    }
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height){
        GLES20.glViewport(0,0, width, height);
    }
}
