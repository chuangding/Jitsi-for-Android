package com.bu.meet.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;


public class popupwindow1 extends AppCompatActivity {
    private ImageView image;
    private Button uploadButton;
    private Bitmap bitmap;
    private Button selectImageButton;
    Contact user=null;

    private PopupWindow popuWindow1;
    private View contentView1;
    private Button btn1;

    // number of images to select
    private static final int PICK_IMAGE = 1;

    Contact currentUser = new Contact();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow1);

        image = (ImageView) findViewById(R.id.uploadImage);
        //uploadButton = (Button) findViewById(R.id.uploadButton);



        SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
        currentUser.setEmailID(sharedpreferences.getString(BUMeetConstants.EMAIL, BUMeetConstants.NOT_FOUND));
        currentUser.setFirstName(sharedpreferences.getString(BUMeetConstants.FIRST_NAME, BUMeetConstants.NOT_FOUND));
        currentUser.setLastName(sharedpreferences.getString(BUMeetConstants.LAST_NAME, BUMeetConstants.NOT_FOUND));
        currentUser.setProfilePic(sharedpreferences.getString("profilePic", BUMeetConstants.NOT_FOUND));
        //displayProfileInfo();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popupwindow1, menu);
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

    public void onClick(View view) {

        if(view.getId() == R.id.takephoto){

        }
        else if(view.getId() == R.id.selectImageButton) {
            selectImageFromGallery();
        }
    }
    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmapImage =decodeBitmap(imageUri);
                image.setImageBitmap(bitmapImage);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] imageData = bos.toByteArray();
                String file = Base64.encodeToString(imageData, Base64.DEFAULT);
                System.out.println("file"+file);
                user = new Contact();
                SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
                user.setProfilePic(file);
                user.setEmailID(sharedpreferences.getString(BUMeetConstants.EMAIL, BUMeetConstants.EMPTY_STRING));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    public  Bitmap decodeBitmap(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 700;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
