package com.lbo.book.gamebasicthread;

import android.util.Log;

/**
 * Created by tomcat2 on 2015-06-01.
 */
public class Thread3 extends Thread {
    public Thread3() {
    }
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(3000);
                Log.i("", "쓰레드3");
            }catch(Exception ex){}
        }
    }

}
