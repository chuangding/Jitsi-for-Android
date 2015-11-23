package com.bu.meet.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;
import com.bu.meet.util.DisplayContactListUtil;

/**
 * Created by Ganesh Seshank on 9/30/2015.
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentTabHost mTabHost;
    Contact currentUser = new Contact();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras == null || (extras != null && !extras.getBoolean(BUMeetConstants.LOGIN))){

            Intent intent1 = new Intent(this,MainActivity.class);
            intent1.putExtra(BUMeetConstants.SIGN_OUT, true);
            intent1.putExtra(BUMeetConstants.LOGIN,false);
            startActivity(intent1);
        }

        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        currentUser.setEmailID(sharedpreferences.getString(BUMeetConstants.EMAIL,BUMeetConstants.NOT_FOUND));
        currentUser.setFirstName(sharedpreferences.getString(BUMeetConstants.FIRST_NAME, BUMeetConstants.NOT_FOUND));
        currentUser.setLastName(sharedpreferences.getString(BUMeetConstants.LAST_NAME, BUMeetConstants.NOT_FOUND));
        currentUser.setProfilePic(sharedpreferences.getString("profilePic", BUMeetConstants.NOT_FOUND));
         /*focus border color*/
       EditText tv=(EditText)findViewById(R.id.roomName);
        tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.focus_border_style);
                } else {
                    view.setBackgroundResource(R.drawable.lost_focus_style);
                }
            }
        });
        String username = getIntent().getStringExtra(BUMeetConstants.USER_NAME);
        displayProfileInfo();
    }

    private void displayProfileInfo() {
       ImageView profilePic = (ImageView) findViewById(R.id.profilePic);
        byte[] bytes = Base64.decode(currentUser.getProfilePic(), Base64.DEFAULT);
        Bitmap imageBmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        profilePic.setImageBitmap(imageBmp);
        TextView fullName = (TextView) findViewById(R.id.PersonName);
        fullName.setText(currentUser.getFirstName()+" "+currentUser.getLastName());

    }


    public void onClick(View view) {
        // ...
        if(view.getId() == R.id.createRoom){
            OnCreateOrJoinRoomClicked();
        }else if(view.getId() == R.id.uploadImage){
            OnUploadImage();
        }
    }


    private void OnCreateOrJoinRoomClicked() {
        EditText editText = (EditText)findViewById(R.id.roomName);
        String roomName = editText.getText().toString();
        Intent intent = new Intent(this,Room.class);
        intent.putExtra(BUMeetConstants.ROOM_NAME,roomName);
        startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_welcome, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.item_option1:
                //Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
                onSignOutClicked();
                break;

            case R.id.item_option2:
                //Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(WelcomeActivity.this,Friends.class);
                startActivity(i);*/
                new DisplayContactListUtil(this).execute(currentUser,BUMeetConstants.FROM_WELCOME,BUMeetConstants.EMPTY_STRING);
                break;
            case R.id.item_option3:
                Intent i = new Intent(WelcomeActivity.this,Profilenew.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void OnUploadImage() {
        //EditText editText = (EditText)findViewById(R.id.roomName);
        //String roomName = editText.getText().toString();
        Intent intent = new Intent(this,UploadImageActivity.class);
        startActivity(intent);
    }
}
