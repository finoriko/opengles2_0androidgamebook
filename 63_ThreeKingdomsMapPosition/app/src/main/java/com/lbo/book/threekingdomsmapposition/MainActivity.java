package com.lbo.book.threekingdomsmapposition;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

// 메인 액티비티
public class MainActivity extends Activity {
    // GLSurfaceView
    private GLSurfaceView mGLSurfaceView;
    private SoundPool mSoundPool;
    private int mSoundButton;
    private int mSoundFight;

    // 액티비티 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 타이틀바를 제거함 requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        // 화면 최대화
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSoundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        try{
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptorBtn = assetManager.openFd("buttoneffect.ogg");
            AssetFileDescriptor descriptorFight = assetManager.openFd("fight.ogg");
            mSoundButton = mSoundPool.load(descriptorBtn, 1);
            mSoundFight = mSoundPool.load(descriptorFight,1);
        }catch(Exception exAsset){}

        // 서피스뷰 생성을 위한 매트릭스
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        mGLSurfaceView = new MainGLSurfaceView(this, width, height);
        setContentView(mGLSurfaceView);
    }
    public void soundButton(){
        try{
            mSoundPool.play(mSoundButton, 1.0f, 1.0f, 0, 0, 1.0f);
        }catch(Exception ex){}
    }
    public void soundFight(){
        try{
            mSoundPool.play(mSoundFight, 1.0f, 1.0f, 0, 0, 1.0f);
        }catch(Exception ex){}
    }

    @Override
    protected void onPause() { super.onPause(); mGLSurfaceView.onPause();
    }
    @Override
    protected void onResume() { super.onResume(); mGLSurfaceView.onResume();
    }
}
