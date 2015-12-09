package com.bu.meet.util;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bu.meet.client.MainActivity;
import com.bu.meet.model.Contact;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Ganesh Seshank on 11/3/2015.
 */
public class DBSyncUtil extends AsyncTask {

    Activity activity = null;
    Contact contact = null;
    String loginVia = null;
    public DBSyncUtil(Activity activity,String loginVia){
        this.activity = activity;
        this.loginVia = loginVia;
    }
    @Override
    protected Object doInBackground(Object... params) {
        {
            URL url = null;
            try {
                url = new URL(BUMeetConstants.SERVICE_BASE_URL+BUMeetConstants.REGISTER_ENDPOINT);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream printout;
            DataInputStream input;
            connection.setRequestProperty(BUMeetConstants.CONTENT_TYPE, BUMeetConstants.APPLICATION_JSON);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            try {
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            contact = (Contact)params[0];
            JSONObject obj = new JSONObject();
            try {
                obj.put(BUMeetConstants.FIRST_NAME,contact.getFirstName());
                obj.put(BUMeetConstants.LAST_NAME,contact.getLastName());
                obj.put(BUMeetConstants.EMAIL_ID,contact.getEmailID());
                obj.put(BUMeetConstants.PASSWORD, contact.getPassword());
                obj.put(BUMeetConstants.PROFILE_PIC,contact.getProfilePic());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                printout = new DataOutputStream(connection.getOutputStream());
                printout.write(obj.toString().getBytes());
                String output;
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(connection.getInputStream());
                com.fasterxml.jackson.core.JsonToken token = parser.nextToken();
                System.out.println(token);
                while(token.toString() != BUMeetConstants.END_OBJECT){
                    if(parser.getValueAsString() == BUMeetConstants.RESPONSE_MESSAGE){
                        token = parser.nextToken();
                        contact.setResponseMessage(parser.getValueAsString());
                    }
                    token = parser.nextToken();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return params;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(contact.getResponseMessage() != null && contact.getResponseMessage().equals(BUMeetConstants.REGISTERED)) {
            if(loginVia.equals(BUMeetConstants.GOOGLE) || loginVia.equals((BUMeetConstants.FACEBOOK))){
                contact.setPassword("DefaultAuthentication");
                new SignInUtil(activity).execute(contact);
            }else {
                Intent intent = new Intent(this.activity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        }else{
            if(loginVia.equals(BUMeetConstants.GOOGLE) || loginVia.equals((BUMeetConstants.FACEBOOK))){
                contact.setPassword("DefaultAuthentication");
                new SignInUtil(activity).execute(contact);
            }else {
                Toast pass = Toast.makeText(this.activity, BUMeetConstants.USER_EXISTS_ERROR_MSG, Toast.LENGTH_SHORT);
                pass.show();
            }
        }
    }
}