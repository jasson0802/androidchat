package com.itcr.chat;

import android.content.ContentResolver;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ContactsActivity extends AppCompatActivity implements OnItemClickListener{

    Cursor contactCursor;
    String name, phonenumber;
    ArrayList<Contact> contacts = new ArrayList<Contact>();


    TypedArray profile_pics;
    String[] lastMessages;
    String[] times;

    List<ContactItem> contactItemList;
    ListView contactListView;

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

        LoadContacts();

        lastMessages = getResources().getStringArray(R.array.sta_array);

        times = getResources().getStringArray(R.array.contact_array);

        for (int i = 0; i < contacts.size(); i++) {
            ContactItem item = new ContactItem(contacts.get(i).getContactName(),
                    "Picture", "",
                    "", contacts.get(i).getPhoneNumber());
            contactItemList.add(item);
        }

        contactListView = (ListView) findViewById(R.id.contactList);
        ContactAdapter adapter = new ContactAdapter(this, contactItemList);
        contactListView.setAdapter(adapter);
        //profile_pics.recycle();
        contactListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        //String member_name = contactItemList.get(position).getMemberName();
        //Toast.makeText(getApplicationContext(), "" + member_name,
                //Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ContactsActivity.this,  ChatArea.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        String contactPhoneNumber = contacts.get(position).getPhoneNumber();
        String contactName = contacts.get(position).getContactName();

        intent.putExtra("contactPhoneNumber",contactPhoneNumber);
        intent.putExtra("contactName",contactName);
        startActivity(intent);
    }

    public void LoadContacts(){
        contactCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (contactCursor.moveToNext()) {

            name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Contact tempContact = new Contact();
            tempContact.setContactName(name);
            tempContact.setPhoneNumber(phonenumber);
            contacts.add(tempContact);
        }

        contactCursor.close();
    }


    /*private void LoadContacts(){
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
                        member_names.add(name);
                        //phone_numbers.add(phoneNo);
                        /*Toast.makeText(NativeContentProvider.this, "Name: " + name
                                + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();*/
                   // }
                    //pCur.close();
              //  }
           // }
       // }
   // }*/
}
