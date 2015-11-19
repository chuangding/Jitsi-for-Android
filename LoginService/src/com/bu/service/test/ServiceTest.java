package com.bu.service.test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.bu.service.login.Contact;
import com.bu.service.login.LoginService;
import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceTest {

	public static void main(String[] args) throws JsonParseException, JSONException, IOException {
		// TODO Auto-generated method stub
		LoginService obj =  new LoginService();
		JSONObject contact = new JSONObject();
		contact.put("firstName","Seshank");
		contact.put("lastName","Kalimireddy");
		contact.put("emailID","seshank4@gmail.com");
		contact.put("password","pass");
		//ObjectMapper mapper = new ObjectMapper();
		
		//obj.registerUser(contact);
	}

}
