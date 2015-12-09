package com.bu.meet.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.bu.meet.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Seshank on 11/19/2015.
 */
public class DownloadProfilePicAndRegisterUtil extends AsyncTask{
   // ImageView image;
    Bitmap imageBmp = null;
    Contact user = null;
    Activity activity = null;
    String loginVia = null;
    public DownloadProfilePicAndRegisterUtil(Activity activity,String loginVia){
        this.activity = activity;
        this.loginVia = loginVia;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        user = (Contact)params[0];
        String imageUrl = (String)params[1];
        imageBmp = null;
        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            imageBmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBmp;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        //image.setImageBitmap(imageBmp);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageData = bos.toByteArray();
        String file = Base64.encodeToString(imageData, Base64.DEFAULT);
        user.setProfilePic(file);
        user.setPassword("DefaultAuthentication");
        new DBSyncUtil(activity,loginVia).execute(user);
    }
}
