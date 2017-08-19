package com.itcr.chat;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MessagesArea extends AppCompatActivity implements OnItemClickListener{
    String[] member_names;
    TypedArray profile_pics;
    String[] lastMessages;
    String[] times;

    List<MessageItem> messageItemList;
    ListView messageListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        messageItemList = new ArrayList<MessageItem>();

        member_names = getResources().getStringArray(R.array.names_array);

        //profile_pics = getResources().obtainTypedArray(R.array.pics_array);

        lastMessages = getResources().getStringArray(R.array.sta_array);

        times = getResources().getStringArray(R.array.contact_array);

        for (int i = 0; i < member_names.length; i++) {
            MessageItem item = new MessageItem(member_names[i],
                    "Picture", lastMessages[i],
                    times[i]);
            messageItemList.add(item);
        }

        messageListView = (ListView) findViewById(R.id.messageList);
        MessageAdapter adapter = new MessageAdapter(this, messageItemList);
        messageListView.setAdapter(adapter);
        //profile_pics.recycle();
        messageListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = messageItemList.get(position).getMemberName();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }
}
