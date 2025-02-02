package com.lbo.book.threekingdomscompthread;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

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
    // 파일쓰기
    public void saveFiles(String fileCont)
            throws Exception{
        try{
            FileOutputStream fos= openFileOutput( "step.txt" , MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
            fos.write(fileCont.getBytes());
            fos.close();
            Toast toast = Toast.makeText(getApplicationContext(), "쓰기 완료", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(Exception ex){
            Toast toast = Toast.makeText(getApplicationContext(), "오류" + ex.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // 파일불러오기
    public String loadFiles()
            throws Exception {
        String strContent = "";
        try{
            FileInputStream fis =

                    new FileInputStream(getFilesDir() + "/" + "step.txt" );
            InputStreamReader ins =new InputStreamReader(fis, "UTF-8");
            BufferedReader reader = new BufferedReader(ins);
            String strLine = "";
            while(( strLine = reader.readLine()) != null){
                Log.i("", strLine);
                strContent = strContent + strLine;
            }
            reader.close();
            ins.close();
            fis.close();
            Toast toast = Toast.makeText(getApplicationContext(), "읽기 완료", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(Exception ex){
        }
        return strContent;
    }

    @Override
    protected void onPause() { super.onPause(); mGLSurfaceView.onPause();
    }
    @Override
    protected void onResume() { super.onResume(); mGLSurfaceView.onResume();
    }
}
