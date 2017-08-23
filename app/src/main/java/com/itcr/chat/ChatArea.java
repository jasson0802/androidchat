package com.itcr.chat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ChatArea extends Activity {
    private static final String TAG = "ChatActivity";
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;

    Intent intent;
    private boolean side = false;

    String receiverNumber;
    String receiverName;

    String senderNumber;
    SharedPreferences settings;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference app = database.getReference("app");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            receiverNumber = null;
        } else {
            receiverNumber = extras.getString("contactPhoneNumber");
            receiverName = extras.getString("contactName");
            receiverNumber = receiverNumber.replaceAll("\\s+","");
            if(receiverNumber.startsWith("+")){
                receiverNumber = receiverNumber.replace("+506","");
            }
        }

        settings = getSharedPreferences("phoneSettings", 0);
        senderNumber = settings.getString("phoneNumber", "");

        setContentView(R.layout.activity_chat_area);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        listView = (ListView) findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_siglemessage);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        app.child("messages").child("senderNumber").equalTo(senderNumber).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevMessageId) {
                Message value = dataSnapshot.getValue(Message.class);
                if(value.getSenderPhone() != senderNumber){
                    chatArrayAdapter.add(new ChatMessage(true, value.getMessage()));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean sendChatMessage(){
        chatArrayAdapter.add(new ChatMessage(false, chatText.getText().toString()));
        Message currentMessage = new Message();
        Contact tempContact = new Contact();
        tempContact.setPhoneNumber(receiverNumber);
        tempContact.setContactName(receiverName);

        currentMessage.setReceiverPhone(tempContact);
        currentMessage.setSenderPhone(senderNumber);
        currentMessage.setMessage(chatText.getText().toString());
        currentMessage.setSavedHour(Calendar.getInstance().getTime());

        app.child("messages").push().setValue(currentMessage);
        chatText.setText("");
        //side = !side;

        return true;
    }
}