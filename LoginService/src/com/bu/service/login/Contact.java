package com.bu.service.login;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {

	private String firstName;
	private String emailID;
	private String lastName;
	private String userName;
	private String password;
	private boolean loginStatus;
	private String contactEmail;
	private String responseMessage;
	private ArrayList<String> contactList = new ArrayList<String>();
	private String profilePic;
	//private Byte profilePicBmp;
	
	public Contact() { }

	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(boolean loginSatus) {
		this.loginStatus = loginSatus;
	}
	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	public String getResponseMessage() {
		return responseMessage;
	}


	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public ArrayList<String> getContactList() {
		return contactList;
	}


	public void setContactList(ArrayList<String> contactList) {
		this.contactList = contactList;
	}
	
	public String getProfilePic() {
		return profilePic;
	}


	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	
	public String toString() {

		return new StringBuffer("firstName : ").append(this.firstName)

				.append("lastName : ").append(this.lastName)

				.append("password : ").append(this.password).append("emailID : ")

				.append(this.emailID).append("loginStatus :").append(this.loginStatus)
				
				.append("responseMessage :").append(this.responseMessage).toString();

	}

	
}
