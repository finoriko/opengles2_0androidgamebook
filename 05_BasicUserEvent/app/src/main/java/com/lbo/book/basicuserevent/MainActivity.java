package com.lbo.book.basicuserevent;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    final static int USER_EVENT = 1000;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnMakeEvent = (Button)findViewById(R.id.button_make_event);

        final EditText etInput = (EditText)findViewById(R.id.edittext_input);

        final TextView tvOutput = (TextView)findViewById(R.id.textview_output);
        btnMakeEvent.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                Message msg = handler.obtainMessage();
                msg.obj = etInput.getText().toString();
                msg.what = USER_EVENT;
                handler.sendMessage(msg);//Message.obtain(handler, MYEVENT));
            }

        });
        handler = new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case USER_EVENT:
                        tvOutput.setText((String)msg.obj); break;
                }
            }
        };


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
