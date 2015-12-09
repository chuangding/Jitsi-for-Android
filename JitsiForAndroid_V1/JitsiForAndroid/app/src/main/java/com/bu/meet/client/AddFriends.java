package com.bu.meet.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bu.meet.R;
import com.bu.meet.util.AddContactUtil;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;

public class AddFriends extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        return true;
    }

    private void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(BUMeetConstants.SIGN_OUT, true);
        intent.putExtra(BUMeetConstants.LOGIN,false);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //showSignedOutUI();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.SignOut:
                onSignOutClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonAddFriend){
            addContact();
        }
    }

    private void addContact() {

        Contact contact = new Contact();
        EditText email = (EditText) findViewById(R.id.friendEmailID);


        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        if(!email.equals(sharedpreferences.getString(BUMeetConstants.EMAIL,BUMeetConstants.EMPTY_STRING))) {
            contact.setEmailID(sharedpreferences.getString(BUMeetConstants.EMAIL, BUMeetConstants.EMPTY_STRING));
            contact.setFriendEmailID(email.getText().toString());
            new AddContactUtil(this).execute(contact);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), BUMeetConstants.SELF_ADD_ERROR_MSG, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
