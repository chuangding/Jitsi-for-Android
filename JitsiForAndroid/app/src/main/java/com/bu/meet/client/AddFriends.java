package com.bu.meet.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bu.meet.R;
import com.bu.meet.login.AddContactUtil;
import com.bu.meet.login.Contact;

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
        intent.putExtra("signout", true);
        intent.putExtra("login",false);
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
                //Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                onSignOutClicked();
                break;
//            default:
//                return super.onOptionsItemSelected(item);
        }

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
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
        contact.setEmailID(email.getText().toString());
        new AddContactUtil(this).execute(contact);
    }
}
