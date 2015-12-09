package com.bu.meet.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bu.meet.model.Contact;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Karunesh on 12/6/2015.
 */
public class DeleteContactUtil extends AsyncTask {
    Activity activity = null;
    Contact contact= null;
    String obj1 = null;
    public DeleteContactUtil(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        URL url = null;
        try {
            url = new URL(BUMeetConstants.SERVICE_BASE_URL+BUMeetConstants.DELETE_CONTACT_ENDPOINT);
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
        try {
            connection.setRequestMethod(BUMeetConstants.POST);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
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
            obj.put(BUMeetConstants.FIRST_NAME,BUMeetConstants.EMPTY_STRING);
            obj.put(BUMeetConstants.LAST_NAME,BUMeetConstants.EMPTY_STRING);
            obj.put(BUMeetConstants.EMAIL_ID,contact.getEmailID());
            obj.put(BUMeetConstants.CONTACT_LIST,contact.getContactList());
            Gson gson = new Gson();
            obj1 =  gson.toJson(contact);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            printout = new DataOutputStream(connection.getOutputStream());
            printout.write(obj1.getBytes());
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

        return params;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(contact.getResponseMessage() != null && contact.getResponseMessage().equalsIgnoreCase(BUMeetConstants.CONTACTS_DELETED)) {
            new DisplayContactListUtil(activity).execute(contact,BUMeetConstants.FROM_WELCOME,BUMeetConstants.EMPTY_STRING);
        }else{
            Toast pass = Toast.makeText(this.activity, BUMeetConstants.UNKNOWN_USER_ERROR_MSG, Toast.LENGTH_SHORT);
            pass.show();
        }
    }
}
