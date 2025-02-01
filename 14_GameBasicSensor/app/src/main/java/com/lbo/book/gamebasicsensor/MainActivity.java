package com.lbo.book.gamebasicsensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Sensor orientationSensor = null;
    private Sensor          accelerometerSensor = null;
    private MainView        mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mainView = new MainView(this);
        setContentView(mainView);

        sensorManager =
                (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        orientationSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(accelerometerSensor != null)
            sensorManager.registerListener(this,
                    accelerometerSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        if(orientationSensor != null)
            sensorManager.registerListener(this,
                    orientationSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onStop() {
        if(accelerometerSensor != null || orientationSensor != null)
            sensorManager.unregisterListener(this);
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        if(accelerometerSensor != null || orientationSensor != null)
            sensorManager.unregisterListener(this);
        super.onDestroy();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized(this){
            if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                mainView.moveSensorOrientation (
                        event.values[0], event.values[1], event.values[2]);
            }
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mainView.moveSensorAccelerometer(
                        event.values[0], event.values[1], event.values[2]);
            }
            mainView.invalidate();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){
    }
}
