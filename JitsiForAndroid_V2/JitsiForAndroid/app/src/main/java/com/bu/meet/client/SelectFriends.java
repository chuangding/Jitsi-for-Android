package com.bu.meet.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SelectFriends extends AppCompatActivity {

    private ArrayList<String> contactList = new ArrayList<String>();
    private ListView listView = null;
    private String roomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null){
            roomName = extras.getString(BUMeetConstants.ROOM);
        }
        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        String contactJson = sharedpreferences.getString(BUMeetConstants.CONTACT, BUMeetConstants.EMPTY_STRING);
        Gson gson = new Gson();
        Contact user = gson.fromJson(contactJson, Contact.class);
        if (user != null) {
            contactList = user.getContactList();
             listView = (ListView) findViewById(R.id.contact_List);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, contactList);
            listView.setAdapter(adapter);
        }
    }

    private void sendEmail(SparseBooleanArray pos,int length) {

        String to[] = new String[length];
        ArrayList<String> toAdd = new ArrayList<String>();
        //to[0]=contactList.get(pos);
        for(int i=0;i<length;i++){
            if(pos.get(i)) {
                to[i] = contactList.get(i);
            }
        }
        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setData(Uri.parse(BUMeetConstants.MAIL_TO));
        sendEmail.setType(BUMeetConstants.TEXT_PLAIN);
        sendEmail.putExtra(Intent.EXTRA_EMAIL, to);
        sendEmail.putExtra(Intent.EXTRA_SUBJECT,BUMeetConstants.MAIL_SUBJECT);
        sendEmail.putExtra(Intent.EXTRA_TEXT,BUMeetConstants.MAIL_TEXT+roomName+BUMeetConstants.BACK_SLASH);
        startActivity(Intent.createChooser(sendEmail,BUMeetConstants.PICKER_HEADER));
        finish();

    }

    public void onClick(View view) {
        // ...
        if(view.getId() == R.id.inviteButton){

            inviteFriends(view);
        }
    }



    private void inviteFriends(View view) {
        listView = (ListView) findViewById(R.id.contact_List);
        int length = listView.getCount();
        SparseBooleanArray positions  = listView.getCheckedItemPositions();
        sendEmail(positions,length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_friends, menu);
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
