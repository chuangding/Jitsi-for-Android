package com.bu.meet.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bu.meet.R;

/**
 * Created by Seshank on 9/30/2015.
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(!extras.getBoolean("login")){

            Intent intent1 = new Intent(this,MainActivity.class);
            intent1.putExtra("signout", true);
            intent1.putExtra("login",false);
            startActivity(intent1);
        }

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
        String username = getIntent().getStringExtra("Username");


    }

    public void onClick(View view) {
        // ...
        if (view.getId() == R.id.sign_out_button) {
            onSignOutClicked();
        }
        else if(view.getId() == R.id.createRoom){
            OnCreateOrJoinRoomClicked();
        }
    }

    private void OnCreateOrJoinRoomClicked() {
        EditText editText = (EditText)findViewById(R.id.roomName);
        String roomName = editText.getText().toString();
        Intent intent = new Intent(this,Room.class);
        intent.putExtra("roomName",roomName);
        startActivity(intent);
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
}
