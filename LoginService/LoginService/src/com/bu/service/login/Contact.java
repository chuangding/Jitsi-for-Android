package com.bu.service.login;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {

	private String firstName;
	private String emailID;
	private String lastName;
	private String userName;
	private String password;
	private boolean loginSatus;
	
	
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
	public boolean isLoginSatus() {
		return loginSatus;
	}
	public void setLoginSatus(boolean loginSatus) {
		this.loginSatus = loginSatus;
	}
	
}
