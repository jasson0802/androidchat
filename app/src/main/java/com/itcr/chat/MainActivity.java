package com.itcr.chat;

import android.app.usage.UsageEvents;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button btnRegister;

    private View.OnClickListener onStartClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

        }
    };

    public void init(){
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onStartClick);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void register(View v){
        Intent intent = new Intent(MainActivity.this,  MessagesArea.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
