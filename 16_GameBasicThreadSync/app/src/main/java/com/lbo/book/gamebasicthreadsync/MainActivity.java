package com.lbo.book.gamebasicthreadsync;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 첫번째 쓰레드
        Thread thread1 = new Thread(){
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(2000);
                        //myMethod(1);
                        mySyncMethod(1);
                    }
                    catch(Exception exTemp) {
                        Log.e("", exTemp.toString());
                    }
                }
            }
        };
        thread1.start();
        // 두번째 쓰레드
        Thread thread2 = new Thread(){
            public void run(){
                while(true) {
                    try {
                        Thread.sleep(2000);
                        myMethod(2);
                        //mySyncMethod(2);
                    }
                    catch(Exception exTemp) {}
                }
            }
        };
        thread2.start();
    }
    public void myMethod(int i) throws Exception{
        Thread.sleep(2000);
        Log.i("", i+  " 호출됨");
    }
    public synchronized void  mySyncMethod(int i)
            throws Exception{
        Thread.sleep(2000);
        Log.i("", i + " sync호출됨");
    }

}
