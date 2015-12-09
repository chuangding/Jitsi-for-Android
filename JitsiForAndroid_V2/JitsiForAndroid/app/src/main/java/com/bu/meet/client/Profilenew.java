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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;

public class Profilenew extends AppCompatActivity implements View.OnClickListener {

    public RelativeLayout header_image;
    public RelativeLayout fname;
    public RelativeLayout lname;
    public RelativeLayout relativeLayout_email;



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
        initView();
    }

    private void initView(){
        header_image=(RelativeLayout)findViewById(R.id.layout_for_image);
        header_image.setOnClickListener(this);

        relativeLayout_email=(RelativeLayout)findViewById(R.id.linear_layout_email);
        relativeLayout_email.setOnClickListener(this);

        fname=(RelativeLayout)findViewById(R.id.linear_layout_name);
        fname.setOnClickListener(this);

        lname=(RelativeLayout)findViewById(R.id.layout_for_lname);
        lname.setOnClickListener(this);


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
//        TextView fullName = (TextView) findViewById(R.id.Show_PersonName);
//        fullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());

        TextView firstName = (TextView) findViewById(R.id.Show_PersonName);
        TextView lastName = (TextView) findViewById(R.id.Show_LName);

        firstName.setText(currentUser.getFirstName());
        lastName.setText(currentUser.getLastName());

        TextView emailID = (TextView) findViewById(R.id.PersonEmail);
        emailID.setText(currentUser.getEmailID());

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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.layout_for_image:
                Intent a = new Intent(Profilenew.this,UploadImageActivity.class);
                startActivity(a);
                break;
            case R.id.linear_layout_email:
                Intent b = new Intent(Profilenew.this,UploadImageActivity.class);
                startActivity(b);
                break;
            case R.id.linear_layout_name:
                Intent c = new Intent(Profilenew.this,UploadImageActivity.class);
                startActivity(c);
                break;
            case R.id.layout_for_lname:
                Intent d = new Intent(Profilenew.this,UploadImageActivity.class);
                startActivity(d);
                break;
        }
    }
}
