package com.bu.meet.login;

import android.os.AsyncTask;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Seshank on 11/3/2015.
 */
public class DBSyncUtil extends AsyncTask {
    @Override
    protected Object doInBackground(Object... params) {
        {
            URL url = null;
            try {
                url = new URL("http://192.168.0.3:8080/LoginService/api/register");
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
            Contact contact = (Contact)params[0];
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
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((requestStops.getInputStream())));
                while ((output = responseBuffer.readLine()) != null) {
                    System.out.println(output);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            requestStops.disconnect();
        }
        return null;
    }
}