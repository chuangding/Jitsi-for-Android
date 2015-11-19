package com.bu.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;

public class AddProfilePicDAO {
	Connection conn = null;	
	String result;

	public void addProfilePic(Contact contact) throws SQLException, ClassNotFoundException {
		Statement stmt = null;	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");

			stmt = conn.createStatement();
			int rowUpdate = stmt.executeUpdate("UPDATE JITSIBU.CONTACT SET PROFILE_PIC = '"+contact.getProfilePic()+"' WHERE EMAIL_ID = '"+contact.getEmailID()+"'");

			if(rowUpdate==1){
				result = "Profile Pic Uploaded";
				contact.setResponseMessage(result);
			}
			else{
				result = "Profile Pic could not be uploaded";
				contact.setResponseMessage(result);
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			contact.setResponseMessage("DB Error");
		}finally {
			conn.close();
		}
	}
}