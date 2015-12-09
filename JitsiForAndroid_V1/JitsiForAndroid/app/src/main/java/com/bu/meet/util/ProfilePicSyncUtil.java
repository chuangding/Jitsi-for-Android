package com.bu.meet.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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
 * Created by Karunesh on 11/18/2015.
 */
public class ProfilePicSyncUtil extends AsyncTask{
    Activity activity = null;
    Contact contact = null;
    public ProfilePicSyncUtil(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Object doInBackground(Object... params) {
        {
            URL url = null;
            try {
                url = new URL("http://Default-Environment-tvztzayeca.elasticbeanstalk.com/api/updateprofilepic");
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
            connection.setRequestProperty("Content-Type", "application/json");
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
                obj.put("firstName","");
                obj.put("lastName","");
                obj.put("emailID",contact.getEmailID());
                obj.put("profilePic",contact.getProfilePic());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                printout = new DataOutputStream(connection.getOutputStream());
                printout.write(obj.toString().getBytes());
                String output;
                System.out.println("Output from Server:\n");
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(connection.getInputStream());
                com.fasterxml.jackson.core.JsonToken token = parser.nextToken();
                System.out.println(token);
                while(token.toString() != "END_OBJECT"){
                    System.out.println("in");
                    if(parser.getValueAsString() == "responseMessage"){
                        token = parser.nextToken();
                        contact.setResponseMessage(parser.getValueAsString());
                    }
                    token = parser.nextToken();
                }

                /*BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((requestStops.getInputStream())));

                while ((output = responseBuffer.readLine()) != null) {
                    System.out.println(output);
                }*/

            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return params;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(contact.getResponseMessage() != null && contact.getResponseMessage().equals("Profile Pic Uploaded")) {
            Intent intent = new Intent(this.activity, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(BUMeetConstants.LOGIN,true);
           // Gson gson = new Gson();
            //String contactJson = gson.toJson(contact);
            SharedPreferences sharedpreferences = activity.getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("profilePic", contact.getProfilePic());
            editor.commit();
            activity.startActivity(intent);
        }else{
            Toast pass = Toast.makeText(this.activity, "user already exists or there is an issue with connectivity", Toast.LENGTH_SHORT);
            pass.show();
        }
    }
}