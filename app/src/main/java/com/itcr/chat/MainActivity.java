package com.itcr.chat;

import android.app.usage.UsageEvents;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText phoneNumber;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference app = database.getReference("app");


    private View.OnClickListener onStartClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

        }
    };

    public void init(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        //btnRegister.setOnClickListener(onStartClick);
    }

    public void register(View v){
        Intent intent = new Intent(MainActivity.this,  MessagesArea.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);

        String phoneNo = phoneNumber.getText().toString();
        app.child("users").push().setValue(phoneNo);

        startActivity(intent);
    }
}
