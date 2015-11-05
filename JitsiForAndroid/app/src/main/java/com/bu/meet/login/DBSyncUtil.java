package com.bu.meet.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bu.meet.client.MainActivity;
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
 * Created by Seshank on 11/3/2015.
 */
public class DBSyncUtil extends AsyncTask {

    Activity activity = null;
    Contact contact = null;
    DBSyncUtil(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Object doInBackground(Object... params) {
        {
            URL url = null;
            try {
                url = new URL("http://Default-Environment-tvztzayeca.elasticbeanstalk.com/api/register");
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
            requestStops.setRequestProperty("Content-Type", "application/json");
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
                obj.put("firstName",contact.getFirstName());
                obj.put("lastName",contact.getLastName());
                obj.put("emailID",contact.getEmailID());
                obj.put("password", contact.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                printout = new DataOutputStream(requestStops.getOutputStream());
                printout.write(obj.toString().getBytes());
                String output;
                System.out.println("Output from Server:\n");
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(requestStops.getInputStream());
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
            requestStops.disconnect();
        }
        return params;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(contact.getResponseMessage() != null && contact.getResponseMessage().equals("Registered")) {
            Intent intent = new Intent(this.activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.startActivity(intent);
        }else{
            Toast pass = Toast.makeText(this.activity, "user already exists or there is an issue with connectivity", Toast.LENGTH_SHORT);
            pass.show();
        }
    }
}