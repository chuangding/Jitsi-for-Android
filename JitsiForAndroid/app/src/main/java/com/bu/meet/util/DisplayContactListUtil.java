package com.bu.meet.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.bu.meet.client.Friends;
import com.bu.meet.client.SelectFriends;
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
 * Created by Ganesh Seshank on 11/16/2015.
 */
public class DisplayContactListUtil extends AsyncTask{

    Activity activity = null;
    Contact contact= null;
    String navigationFrom;
    String roomName;

    public DisplayContactListUtil(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        navigationFrom = (String)params[1];
        roomName = (String)params[2];
        URL url = null;
        try {
            url = new URL(BUMeetConstants.SERVICE_BASE_URL+BUMeetConstants.DISPLAY_CONTACTS_ENDPOINT);
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
        Contact user = (Contact)params[0];
        contact = new Contact();
        JSONObject obj = new JSONObject();
        try {
            obj.put(BUMeetConstants.FIRST_NAME,BUMeetConstants.EMPTY_STRING);
            obj.put(BUMeetConstants.LAST_NAME,BUMeetConstants.EMPTY_STRING);
            obj.put(BUMeetConstants.EMAIL_ID,user.getEmailID());
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
            if(token.toString().equals(BUMeetConstants.START_OBJECT)){
                while(!token.toString().equals(BUMeetConstants.END_OBJECT)){
                    if(token.toString().equals(BUMeetConstants.START_ARRAY)){
                        while(token.toString()!=BUMeetConstants.END_ARRAY) {
                            System.out.println(token);
                            if(token.toString().equals(BUMeetConstants.VALUE_STRING)) {
                                System.out.println(parser.getValueAsString());
                                contact.getContactList().add(parser.getText());
                            }
                            token = parser.nextToken();
                        }
                    }else if(parser.getValueAsString() == BUMeetConstants.RESPONSE_MESSAGE){
                        token = parser.nextToken();
                        contact.setResponseMessage(parser.getValueAsString());
                    }
                    token = parser.nextToken();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();

        return params;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(contact.getResponseMessage() != null && contact.getResponseMessage().equalsIgnoreCase(BUMeetConstants.SUCCESSFULLY_FETCHED)) {
            Gson gson = new Gson();
            String contactJson = gson.toJson(contact);
            SharedPreferences sharedpreferences = activity.getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(BUMeetConstants.CONTACT, contactJson);
            editor.commit();
        }
        if(navigationFrom.equalsIgnoreCase(BUMeetConstants.FROM_ROOM)) {
            Intent intent = new Intent(this.activity, SelectFriends.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(BUMeetConstants.ROOM,roomName);
            activity.startActivity(intent);
        }else if(navigationFrom.equalsIgnoreCase(BUMeetConstants.FROM_WELCOME)){
            Intent intent = new Intent(this.activity, Friends.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }
}
