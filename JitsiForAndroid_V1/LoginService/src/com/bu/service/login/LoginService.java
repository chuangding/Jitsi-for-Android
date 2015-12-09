package com.bu.service.login;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import com.bu.service.dao.RegisterDAO;


@Path("/")
public class LoginService {

	//private String loginStatus="ok";
	/**
	 * @author ganesh 
	 * @param contact
	 * function for registering a new user to the system
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_XML)
	public void registerUser(JAXBElement<Contact> contactXML){
		Contact newUser = contactXML.getValue();
		RegisterDAO dao = new RegisterDAO();
		boolean status = dao.registerUser(newUser);
	}
	
	
	/**
	 * @author ganesh
	 * @param contact
	 * function for verifying user credentials for login
	 */
	@GET
	@Path("/verify")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response verifyUser(){
		Contact contact = new Contact();
		contact.setLoginSatus(true);
		
		return Response.status(200).entity(contact).build();
	}
	
}
