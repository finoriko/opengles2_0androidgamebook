package com.lbo.book.gamebasicsound;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int soundExplosion;
    private int streamId;
    private AudioManager audioManager;
    private String selectedSoundFile = "test1.ogg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundPool = new SoundPool.Builder().build();
        //soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0); //구버전 api
        try{
            AssetManager assetManager = getAssets();
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(selectedSoundFile);
            soundExplosion = soundPool.load(assetFileDescriptor, 1);
        }catch(Exception ex){}
        audioManager = (AudioManager)getSystemService(
                getApplicationContext().AUDIO_SERVICE);
        SeekBar sbVolume = (SeekBar)findViewById(R.id.seekbar_volume);
        sbVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        RadioButton rbOgg1 = (RadioButton)findViewById(R.id.radiobutton_ogg1);
        RadioButton rbOgg2 = (RadioButton)findViewById(R.id.radiobutton_ogg2);
        RadioButton rbOgg3 = (RadioButton)findViewById(R.id.radiobutton_ogg3);
        rbOgg1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedSoundFile = "test1.ogg";
            }
        });
        rbOgg2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedSoundFile = "test2.ogg";
            }
        });
        rbOgg3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedSoundFile = "test3.ogg";
            }
        });

        Button btnPlay = (Button)findViewById(R.id.button_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playSound();
            }
        });

        Button btnStop = (Button)findViewById(R.id.button_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopSound();
            }
        });
        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                changeVolume(progress);
            }
        });

    }

    public void playSound(){
        streamId = soundPool.play(soundExplosion, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    public void stopSound(){
        try {
            soundPool.stop(streamId);
        }
        catch(Exception ex){}
    }
    public void changeVolume(int progress){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                progress, AudioManager.FLAG_SHOW_UI);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
