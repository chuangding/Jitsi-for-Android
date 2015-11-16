package com.bu.meet.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bu.meet.R;
public class Friends extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
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
        switch (item.getItemId()){
            case R.id.item_addContact:
                //Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Friends.this,AddFriends.class);
                startActivity(i);
                break;
//
//            case R.id.item_option2:
//                Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.item_option3:
//                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
//                break;

            case android.R.id.home:
                this.finish();
                return true;
            case R.id.SignOut:
                //Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                onSignOutClicked();
                break;
            default:
                return super.onOptionsItemSelected(item);


        }

        return super.onOptionsItemSelected(item);
    }
}
