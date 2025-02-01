package com.lbo.book.gamebasicthread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread1 = new Thread() {
            public void run() {
                while(true){
                    try {
                        Thread.sleep(2000);
                        Log.i("", "쓰레드1");
                    }catch(Exception ex){}
                }
            }
        };
        thread1.start();
        Thread thread2 = new Thread() {
            public void run() {
                while(true){
                    try {
                        Thread.sleep(4000);
                        Log.i("", "쓰레드2");
                    }catch(Exception ex){}
                }
            }
        };
        thread2.start();

        Thread3 thread3 = new Thread3();
        thread3.start();

    }
}
