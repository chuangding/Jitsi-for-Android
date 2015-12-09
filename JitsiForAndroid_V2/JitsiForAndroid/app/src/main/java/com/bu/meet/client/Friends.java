package com.bu.meet.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;
import com.bu.meet.util.DeleteContactUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {

    ArrayList<String> contactList = new ArrayList<String>();
    private ListView listView = null;
    Activity activity = this;
    public ArrayList<String> deletedList= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        String contactJson = sharedpreferences.getString(BUMeetConstants.CONTACT, BUMeetConstants.EMPTY_STRING);
        Gson gson = new Gson();
        final Contact user = gson.fromJson(contactJson, Contact.class);
        if (user != null) {
            contactList = user.getContactList();
            ListView listView = (ListView) findViewById(R.id.contact_List);
           Button button = (Button) findViewById(R.id.delete_Button);
            button.setVisibility(View.INVISIBLE);
            button.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    Contact contact =  new Contact();
                    contact.setEmailID(user.getEmailID());
                    contact.setContactList(deletedList);
                        new DeleteContactUtil(activity).execute(contact);
                }
            });
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, contactList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                    boolean isChecked = getCheckedCount();
                    setVisibility(isChecked);
                }
            });
        }
    }

    public void onClick(View view){
        if(view.getId() == R.id.contact_List){
            boolean isChecked = getCheckedCount();
            setVisibility(isChecked);
        }
    }


    private boolean getCheckedCount() {
        deletedList.clear();
        listView = (ListView) findViewById(R.id.contact_List);
        int length = listView.getCheckedItemCount();
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        int i=0;
        for(String contact : contactList){
            if(checkedItems.get(i)){
                deletedList.add(contact);
            }
            i++;
        }
        if(length>0){
            return true;
        } else {
            return false;
        }

    }

    public void setVisibility(boolean isChecked) {
        // ...
        Button btn = (Button)findViewById(R.id.delete_Button);
        if(isChecked){
            btn.setVisibility(View.VISIBLE);
        }
        else{
            btn.setVisibility(View.INVISIBLE);
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
        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
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
