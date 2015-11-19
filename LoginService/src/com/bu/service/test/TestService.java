package com.bu.service.test;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.bu.service.login.Contact;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class TestService {

	public static void main(String[] args) throws JsonParseException, IOException, JSONException {
		// TODO Auto-generated method stub
		boolean status = false;
		ClientConfig clientConfig = new DefaultClientConfig();
		            clientConfig.getFeatures().put(
		                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

		Client client = Client.create(clientConfig);
		Contact contact = new Contact();
	/*	contact.put("firstName","Seshank");
		contact.put("lastName","Kalimireddy");
		contact.put("emailID","seshank4@gmail.com");
		contact.put("password","pass");*/
		/*ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(contact);*/
		contact.setFirstName("Seshank");
		contact.setLastName("Kaimireddy");
		contact.setPassword("pass");
		contact.setEmailID("seshank5@gmail.com");
        WebResource webResource2 = client.resource("http://localhost:8080/LoginService/api/register");
        ClientResponse response = webResource2.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,contact);
        /*if (response2.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
        }*/
 
        /*String output = response2.getEntity(String.class);
        JsonParser parser = new JsonFactory().createParser(output);
        while(parser.nextToken() != JsonToken.END_OBJECT){
        	String field = parser.getCurrentName();
        if(field != null && field.equals("loginStatus")){
        	status = parser.getBooleanValue();
        }
        }*/
        if (response.getStatus() != 200) {
        	                throw new RuntimeException("Failed : HTTP error code : "
        	                        + response.getStatus());
        	            }
        	            String output = response.getEntity(String.class);
        	            System.out.println("Server response .... \n");
        	            System.out.println(output);

	}

}
