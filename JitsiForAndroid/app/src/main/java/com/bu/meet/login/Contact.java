package com.bu.meet.login;

/**
 * Created by HL on 30-09-2015.
 */
public class Contact {

    String firstName;
    String emailID;
    String lastName;
    String password;
    String responseMessage;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String toString() {

        return new StringBuffer("firstName : ").append(this.firstName)

                .append("lastName : ").append(this.lastName)

                .append("password : ").append(this.password).append("emailID : ")

                .append(this.emailID).toString();

    }

}

