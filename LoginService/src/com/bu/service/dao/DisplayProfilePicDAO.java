package com.bu.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;

public class DisplayProfilePicDAO {
	Connection conn = null;	
	String result;

	public void displayProfilePic(Contact contact) throws SQLException, ClassNotFoundException {
		Statement stmt = null;	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT PROFILE_PIC FROM JITSIBU.CONTACT WHERE EMAIL_ID = '"+contact.getEmailID()+"'");

			if(rs.next()){
				result = "Profile Pic Fetched";
				contact.setResponseMessage(result);
				contact.setProfilePic(rs.getString("PROFILE_PIC"));
				//System.out.println(result);
			}
			else{
				result = "Profile Pic could not be fetched";
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
