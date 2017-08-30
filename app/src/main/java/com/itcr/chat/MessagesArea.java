package com.itcr.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.res.TypedArray;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessagesArea extends AppCompatActivity implements OnItemClickListener{
    ArrayList<Contact> phoneContacts = new ArrayList<Contact>();
    Cursor contactCursor;
    String[] member_names;
    TypedArray profile_pics;
    String[] lastMessages;
    String[] times;
    MessageAdapter adapter;

    String senderNumber;
    SharedPreferences settings;

    List<MessageItem> messageItemList;
    ListView messageListView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference app = database.getReference("app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoadContacts();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MessagesArea.this,  ContactsActivity.class);

                startActivity(intent);
            }
        });

        settings = getSharedPreferences("phoneSettings", 0);
        senderNumber = settings.getString("phoneNumber", "");

        messageItemList = new ArrayList<MessageItem>();

        member_names = getResources().getStringArray(R.array.names_array);

        //profile_pics = getResources().obtainTypedArray(R.array.pics_array);

        lastMessages = getResources().getStringArray(R.array.sta_array);

        times = getResources().getStringArray(R.array.contact_array);

        /*for (int i = 0; i < member_names.length; i++) {
            MessageItem item = new MessageItem(member_names[i],
                    "Picture", lastMessages[i],
                    times[i]);
            messageItemList.add(item);
        }*/

        messageListView = (ListView) findViewById(R.id.messageList);
        adapter = new MessageAdapter(this, messageItemList);
        messageListView.setAdapter(adapter);
        //profile_pics.recycle();
        messageListView.setOnItemClickListener(this);

        app.child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevMessageId) {
                Message value = dataSnapshot.getValue(Message.class);
                if(value.getReceiverPhone().getPhoneNumber().equals(senderNumber)){
                    int updatedMessages = 0;
                    DateFormat format = new SimpleDateFormat("HH:mm");
                    String hour = format.format(value.getSavedHour());
                    for (MessageItem mItem: messageItemList) {
                        if(mItem.getPhoneNumber().equals(value.getSenderPhone()))
                        {
                            mItem.setLastMessage(value.getMessage());
                            mItem.setLastMessageTime(hour);
                            updatedMessages += 1;
                        }
                    }
                    if(updatedMessages == 0){
                        int foundNumbers = 0;
                        String contactName = "";
                        for (Contact tempContact: phoneContacts) {
                            if(foundNumbers >= 1){
                                break;
                            }
                            else if(tempContact.getPhoneNumber().equals(value.getSenderPhone()))
                            {
                                contactName = tempContact.getContactName();
                                foundNumbers += 1;
                            }
                        }

                        MessageItem tempItem = new MessageItem(contactName,"test2",value.getMessage(),
                                hour, value.getSenderPhone());
                        messageItemList.add(tempItem);
                    }

                    adapter.notifyDataSetChanged();
                    //chatArrayAdapter.add(new ChatMessage(true, value.getMessage()));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Intent intent = new Intent(MessagesArea.this,  ChatArea.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        String contactPhoneNumber = messageItemList.get(position).getPhoneNumber();
        String contactName = messageItemList.get(position).getMemberName();

        intent.putExtra("contactPhoneNumber",contactPhoneNumber);
        intent.putExtra("contactName",contactName);
        startActivity(intent);

        /*String member_name = messageItemList.get(position).getMemberName();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();*/
    }

    public void LoadContacts(){
        contactCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (contactCursor.moveToNext()) {
            String name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phonenumber = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phonenumber = phonenumber.replaceAll("\\s+","");
            if(phonenumber.startsWith("+")){
                phonenumber = phonenumber.replace("+506","");
            }

            Contact tempContact = new Contact();
            tempContact.setContactName(name);
            tempContact.setPhoneNumber(phonenumber);
            phoneContacts.add(tempContact);
        }

        contactCursor.close();


    }
}
