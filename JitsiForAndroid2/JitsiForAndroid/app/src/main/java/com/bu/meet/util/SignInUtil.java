package com.bu.meet.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bu.meet.client.ButtomBar;
import com.bu.meet.client.WelcomeActivity;
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
 * Created by Ganesh Seshank on 11/4/2015.
 */
public class SignInUtil extends AsyncTask {

    Activity activity = null;
    Contact contact= null;
    public SignInUtil(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Object doInBackground(Object[] params) {

            URL url = null;
            try {
                url = new URL(BUMeetConstants.SERVICE_BASE_URL+BUMeetConstants.VERIFY_ENDPOINT);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection requestStops = null;
            try {
                requestStops = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream printout;
            DataInputStream input;
            requestStops.setRequestProperty(BUMeetConstants.CONTENT_TYPE, BUMeetConstants.APPLICATION_JSON);
            requestStops.setDoInput(true);
            requestStops.setDoOutput(true);
            try {
                requestStops.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            contact = (Contact)params[0];
            JSONObject obj = new JSONObject();
            try {
                obj.put(BUMeetConstants.FIRST_NAME,BUMeetConstants.EMPTY_STRING);
                obj.put(BUMeetConstants.LAST_NAME,BUMeetConstants.EMPTY_STRING);
                obj.put(BUMeetConstants.EMAIL_ID,contact.getEmailID());
                obj.put(BUMeetConstants.PASSWORD, contact.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                printout = new DataOutputStream(requestStops.getOutputStream());
                printout.write(obj.toString().getBytes());
                String output;
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(requestStops.getInputStream());
                com.fasterxml.jackson.core.JsonToken token = parser.nextToken();
                System.out.println(token);
                while(token.toString() != BUMeetConstants.END_OBJECT){
                    if(parser.getValueAsString() == BUMeetConstants.RESPONSE_MESSAGE){
                        token = parser.nextToken();
                        contact.setResponseMessage(parser.getValueAsString());
                    }else if(parser.getValueAsString() == "firstName"){
                        token = parser.nextToken();
                        contact.setFirstName(parser.getValueAsString());
                    }else if(parser.getValueAsString() == "lastName"){
                        token = parser.nextToken();
                        contact.setLastName(parser.getValueAsString());
                    }else if(parser.getValueAsString() == "profilePic"){
                        token = parser.nextToken();
                        contact.setProfilePic(parser.getValueAsString());
                    }
                    token = parser.nextToken();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            requestStops.disconnect();

        return params;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(contact.getResponseMessage() != null && contact.getResponseMessage().equals(BUMeetConstants.MATCH_FOUND)) {
            Intent intent = new Intent(this.activity, ButtomBar.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(BUMeetConstants.LOGIN, true);
            SharedPreferences sharedpreferences = activity.getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(BUMeetConstants.EMAIL,contact.getEmailID());
            editor.putString(BUMeetConstants.FIRST_NAME,contact.getFirstName());
            editor.putString(BUMeetConstants.LAST_NAME,contact.getLastName());
            editor.putString("profilePic",contact.getProfilePic());
            editor.commit();
            activity.startActivity(intent);
        }else{
            Toast pass = Toast.makeText(this.activity, BUMeetConstants.INVALID_CREDENTIALS_ERROR_MSG, Toast.LENGTH_SHORT);
            pass.show();
        }
    }
}
