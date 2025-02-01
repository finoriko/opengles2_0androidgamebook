package com.lbo.book.openglbasictex;

import android.content.Context;
import android.opengl.GLSurfaceView;

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

}
