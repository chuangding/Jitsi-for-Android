package com.bu.meet.model;

import java.util.ArrayList;

/**
 * Created by HL on 30-09-2015.
 */
public class Contact {

    private String firstName;
    private String emailID;
    private String lastName;
    private String password;
    private String responseMessage;
    private String friendEmailID;
    private String profilePic;
    private ArrayList<String> contactList = new ArrayList<String>();


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

    public String getFriendEmailID() {
        return friendEmailID;
    }

    public void setFriendEmailID(String friendEmailID) {
        this.friendEmailID = friendEmailID;
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

    public String toString() {

        return new StringBuffer("firstName : ").append(this.firstName)

                .append("lastName : ").append(this.lastName)

                .append("password : ").append(this.password).append("emailID : ")

                .append(this.emailID).toString();

    }

}

