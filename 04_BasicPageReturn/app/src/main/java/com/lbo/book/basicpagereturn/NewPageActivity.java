package com.lbo.book.basicpagereturn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class NewPageActivity extends AppCompatActivity {
    final static int RESULT_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        final EditText edtInput = (EditText)findViewById(R.id.edittext_input);
        Button btnReturn = (Button)findViewById(R.id.button_return);
        btnReturn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("MY_VALUE",
                        edtInput.getText().toString());
                setResult(RESULT_CODE, returnIntent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_page, menu);
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
