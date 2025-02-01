package com.lbo.book.surfaceviewbitmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.BufferedInputStream;

/**
 * Created by tomcat2 on 2015-06-01.
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback{
    private MainActivity mainActivity;
    MainThread mainThread;
    Handler handler;
    Context mainContext;
    Bitmap mBitmap;
    boolean drawCls = false;
    public MainView(Context r){
        super(r);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(),this);
        setFocusable(true);
        mainContext = r;
    }
    public void init(int width, int height, MainActivity gameActivity){
        this.mainActivity = gameActivity;
        mBitmap = loadBitmap("b_bowman1.png");
        drawCls = true;
    }
    @Override
    public  void onDraw(Canvas canvas){
        //super.onDraw(canvas);
        if(drawCls == false)
            return;
        // 배경을 그림.
        canvas.drawColor(Color.YELLOW);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawBitmap(mBitmap,
                width/2 - mBitmap.getWidth()/2,
                height/2 - mBitmap.getHeight()/2, null) ;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        return true;
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }
    public void surfaceCreated(SurfaceHolder holder){
        mainThread.setRunning(true);
        try{
            if(mainThread.getState() == Thread.State.TERMINATED ){
                mainThread = new MainThread(getHolder(),this);
                mainThread.setRunning(true);
                setFocusable(true);
                mainThread.start();
            }else{
                mainThread.start();
            }
        }
        catch(Exception ex){
            Log.d("MainView", "ex:" + ex.toString());
        }
    }
    public void surfaceDestroyed(SurfaceHolder holder){
        Log.i("MainView", "surfaceDestoryed called");
        boolean retry= true;
        mainThread.setRunning(false);
        while(retry){
            try{
                mainThread.join();
                retry= false;
            }
            catch(Exception e){
                Log.i("MainView", "surfaceDestoryed ex" + e.toString());
            }
        }
    }
    private Bitmap loadBitmap(String filename){
        Bitmap bm = null;
        try{
            AssetManager am = mainContext.getAssets();
            BufferedInputStream buf = new BufferedInputStream(am.open(filename));
            bm= BitmapFactory.decodeStream(buf);
        }catch(Exception ex){
            Toast.makeText(mainContext, "Bitmap 로드 오류:" + filename + "을 찾을 수 없습니다.",
                    Toast.LENGTH_LONG);
        }
        return bm;
    }
}
