package com.lbo.book.openglbasichangul;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by tomcat2 on 2015-06-02.
 */
public class MainGLSurfaceView extends GLSurfaceView {
    private final MainGLRenderer mRenderer;

    public MainGLSurfaceView(MainActivity activity, int width, int height) {
        super(activity.getApplicationContext());
        setEGLContextClientVersion(2);
        mRenderer = new MainGLRenderer(activity, width, height);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mRenderer.onTouchEvent(event);
        return true;
    }

}
