package com.bu.meet.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;

public class Profilenew extends AppCompatActivity {

    Contact currentUser = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilenew);

        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        currentUser.setEmailID(sharedpreferences.getString(BUMeetConstants.EMAIL,BUMeetConstants.NOT_FOUND));
        currentUser.setFirstName(sharedpreferences.getString(BUMeetConstants.FIRST_NAME, BUMeetConstants.NOT_FOUND));
        currentUser.setLastName(sharedpreferences.getString(BUMeetConstants.LAST_NAME, BUMeetConstants.NOT_FOUND));
        currentUser.setProfilePic(sharedpreferences.getString("profilePic", BUMeetConstants.NOT_FOUND));

        String username = getIntent().getStringExtra(BUMeetConstants.USER_NAME);
        displayProfileInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profilenew, menu);
        return true;
    }

    private void displayProfileInfo() {
        ImageView profilePic = (ImageView) findViewById(R.id.imageview_profile);
        byte[] bytes = Base64.decode(currentUser.getProfilePic(), Base64.DEFAULT);
        Bitmap imageBmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        profilePic.setImageBitmap(imageBmp);
        TextView fullName = (TextView) findViewById(R.id.Show_PersonName);
        fullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editpage) {
            Intent i = new Intent(Profilenew.this,UploadImageActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}
