package com.lbo.book.gamebasicfilereadwrite;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWrite = (Button)findViewById(R.id.button_write);
        Button btnRead = (Button)findViewById(R.id.button_read);
        final EditText etInput = (EditText)findViewById(R.id.edittext_input);
        final EditText etOutput = (EditText)findViewById(R.id.edittext_output);

        btnWrite.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               try{
                   FileOutputStream fos =
                           openFileOutput("test.txt",
                                   MODE_PRIVATE);
                   String fileCont = etInput.getText().toString();
                   fos.write(fileCont.getBytes());
                   fos.close();
                   Toast.makeText(getApplicationContext(),"쓰기완료",Toast.LENGTH_SHORT).show();

               }catch(Exception ex){}
           }
        });
        btnRead.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    FileInputStream fis = new java.io.FileInputStream(getFilesDir() + "/" + "test.txt");
                    InputStreamReader ins = new java.io.InputStreamReader(fis, "UTF-8");

                    BufferedReader reader = new java.io.BufferedReader(ins);
                    String strContent = "";
                    String strLine = "";

                    while(( strLine = reader.readLine()) != null) {
                        strContent = strContent + strLine;
                    }
                    etOutput.setText(strContent);
                    reader.close();
                    ins.close();
                    fis.close();
                    Toast.makeText(getApplicationContext(),"읽기완료",Toast.LENGTH_SHORT).show();

                }
                catch(Exception ex) {

                }
            }


        });


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
