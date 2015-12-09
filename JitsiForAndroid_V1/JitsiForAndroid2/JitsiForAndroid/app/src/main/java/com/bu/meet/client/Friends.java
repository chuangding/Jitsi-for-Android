package com.bu.meet.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {

    ArrayList<String> contactList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        String contactJson = sharedpreferences.getString(BUMeetConstants.CONTACT, BUMeetConstants.EMPTY_STRING);
        Gson gson = new Gson();
        Contact user = gson.fromJson(contactJson, Contact.class);
        if (user != null) {
            contactList = user.getContactList();
            ListView listView = (ListView) findViewById(R.id.contact_List);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
            listView.setAdapter(adapter);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_friends, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(BUMeetConstants.SIGN_OUT, true);
        intent.putExtra(BUMeetConstants.LOGIN,false);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.item_addContact:
                Intent i = new Intent(Friends.this,AddFriends.class);
                startActivity(i);
                break;
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.SignOut:
                onSignOutClicked();
                break;
            default:
                return super.onOptionsItemSelected(item);


        }

        return super.onOptionsItemSelected(item);
    }
}
