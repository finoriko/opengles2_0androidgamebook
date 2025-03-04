package com.lbo.book.surfaceviewbitmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
    MainView mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_main);
        mainView = new MainView(this);
        setContentView(mainView);

        int ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int ScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        mainView.init(ScreenWidth, ScreenHeight, this);

    }


}
