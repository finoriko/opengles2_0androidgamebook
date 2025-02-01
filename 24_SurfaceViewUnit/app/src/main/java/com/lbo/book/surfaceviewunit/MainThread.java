package com.lbo.book.surfaceviewunit;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by tomcat2 on 2015-06-01.
 */
public class MainThread extends Thread {
    private SurfaceHolder surfaceholder;
    private MainView mainView;
    private boolean running = false;
    public MainThread(SurfaceHolder surfaceHolder, MainView mainView){
        surfaceholder = surfaceHolder;
        this.mainView = mainView;
    }
    public SurfaceHolder getSurfaceHolder(){
        return surfaceholder;
    }
    public void setRunning(boolean run){
        running = run;
    }
    public void run(){
        Log.d("mainThread", "run called:" + running);
        try
        {
            Canvas c;
            while(running){
                c = null;
                try{
                    c = surfaceholder.lockCanvas(null);
                    synchronized(surfaceholder){
                        try{
                            Thread.sleep(1);
                            mainView.onDraw(c);
                        }
                        catch(Exception exTemp){
                            Log.e("에러", exTemp.toString());
                        }
                    }
                }
                finally{
                    if( c!= null)
                        surfaceholder.unlockCanvasAndPost(c);
                }
            }
        }
        catch(Exception exTot){
            Log.e("에러", exTot.toString());
        }
    }
}
