package com.itcr.chat;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ContactsActivity extends AppCompatActivity implements OnItemClickListener{
    String[] member_names;
    ArrayList<String> phone_numbers = new ArrayList<String>();

    TypedArray profile_pics;
    String[] lastMessages;
    String[] times;

    List<ContactItem> contactItemList;
    ListView contactListView;

    private String GetPhoneNumber(){
        return "A";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
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

        contactItemList = new ArrayList<ContactItem>();

        member_names = getResources().getStringArray(R.array.names_array);

        //profile_pics = getResources().obtainTypedArray(R.array.pics_array);

        LoadContacts();

        lastMessages = getResources().getStringArray(R.array.sta_array);

        times = getResources().getStringArray(R.array.contact_array);

        for (int i = 0; i < phone_numbers.size(); i++) {
            ContactItem item = new ContactItem(phone_numbers.get(i),
                    "Picture", "",
                    "", phone_numbers.get(i));
            contactItemList.add(item);
        }

        contactListView = (ListView) findViewById(R.id.messageList);
        ContactAdapter adapter = new ContactAdapter(this, contactItemList);
        contactListView.setAdapter(adapter);
        //profile_pics.recycle();
        contactListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = contactItemList.get(position).getMemberName();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }

    private void LoadContacts(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phone_numbers.add(phoneNo);
                        /*Toast.makeText(NativeContentProvider.this, "Name: " + name
                                + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();*/
                    }
                    pCur.close();
                }
            }
        }
    }
}
