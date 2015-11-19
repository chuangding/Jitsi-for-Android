package com.bu.service.login;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.bu.service.dao.AddContactListDAO;
import com.bu.service.dao.AddProfilePicDAO;
import com.bu.service.dao.DeleteContactDAO;
import com.bu.service.dao.DisplayContactDAO;
import com.bu.service.dao.DisplayProfilePicDAO;
import com.bu.service.dao.RegisterDAO;
import com.bu.service.dao.VerifyUserDAO;


@Path("/")
public class LoginService {

	//private String loginStatus="ok";
	/**
	 * @author ganesh 
	 * @param contact
	 * function for registering a new user to the system
	 * @return 
	 * @throws ClassNotFoundException 
	 * @throws JSONException 
	 */
	@POST
	@Path("/register")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(Contact contact) throws ClassNotFoundException, JSONException{
		
		RegisterDAO dao = new RegisterDAO();
		boolean status=false;
		try {
			status = dao.registerUser(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contact.setLoginStatus(status);
		JSONObject response = new JSONObject();
		response.put("firstName", contact.getFirstName());
		response.put("lastName", contact.getLastName());
		response.put("password", contact.getPassword());
		response.put("emailID", contact.getEmailID());
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}
	
	
	/**
	 * @author ganesh
	 * @param contact
	 * function for verifying user credentials for login
	 * @throws ClassNotFoundException 
	 * @throws JSONException 
	 */
	@POST
	@Path("/verify")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verifyUser(Contact contact) throws ClassNotFoundException, JSONException{
		
		//contact.setLoginStatus(true);
		VerifyUserDAO dao = new VerifyUserDAO(); 
		try {
			dao.verifyUser(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject response = new JSONObject();
		response.put("firstName", contact.getFirstName());
		response.put("lastName", contact.getLastName());
		response.put("password", contact.getPassword());
		response.put("emailID", contact.getEmailID());
		response.put("profilePic", contact.getProfilePic());
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@POST
	@Path("/addcontact")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addContact(Contact contact) throws ClassNotFoundException, JSONException{
		
		//contact.setLoginStatus(true);
		AddContactListDAO dao = new AddContactListDAO(); 
		try {
			dao.addContactList(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject response = new JSONObject();
		response.put("firstName", contact.getFirstName());
		response.put("lastName", contact.getLastName());
		response.put("password", contact.getPassword());
		response.put("emailID", contact.getEmailID());
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@POST
	@Path("/deletecontact")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteContact(Contact contact) throws ClassNotFoundException, JSONException{
		
		//contact.setLoginStatus(true);
		DeleteContactDAO dao = new DeleteContactDAO(); 
		try {
			dao.deleteContact(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}JSONObject response = new JSONObject();
		response.put("firstName", contact.getFirstName());
		response.put("lastName", contact.getLastName());
		response.put("password", contact.getPassword());
		response.put("emailID", contact.getEmailID());
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@POST
	@Path("/displaycontacts")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response displayContact(Contact contact) throws ClassNotFoundException, JSONException{
		
		//contact.setLoginStatus(true);
		DisplayContactDAO dao = new DisplayContactDAO(); 
		try {
			dao.displayContact(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}JSONObject response = new JSONObject();
		response.put("contactList", contact.getContactList());
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@POST
	@Path("/updateprofilepic")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProfilePic(Contact contact) throws ClassNotFoundException, JSONException{
		
		//contact.setLoginStatus(true);
		AddProfilePicDAO dao = new AddProfilePicDAO(); 
		try {
			dao.addProfilePic(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}JSONObject response = new JSONObject();
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}
	
	@POST
	@Path("/displayprofilepic")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response displayProfilePic(Contact contact) throws ClassNotFoundException, JSONException{
		
		//contact.setLoginStatus(true);
		DisplayProfilePicDAO dao = new DisplayProfilePicDAO(); 
		try {
			dao.displayProfilePic(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}JSONObject response = new JSONObject();
		response.put("profilePic",contact.getProfilePic());
		response.put("responseMessage",contact.getResponseMessage());
		return Response.status(200).entity(response.toString()).build();
	}	
}
