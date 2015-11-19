package com.bu.service.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;

import java.sql.Connection;

public class VerifyUserDAO {
	Connection conn = null;
	Statement stmt;
	Statement stmt2;
	int count;
	String result;
	
	
	public void verifyUser(Contact contact) throws ClassNotFoundException, SQLException{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM JITSIBU.CONTACT WHERE EMAIL_ID = '"+contact.getEmailID()+"' AND PASSWORD = '"+contact.getPassword()+"'");
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count==1){
				stmt2 = conn.createStatement();
				ResultSet contactInfo=stmt2.executeQuery("SELECT FIRST_NAME,LAST_NAME,PROFILE_PIC FROM JITSIBU.CONTACT WHERE EMAIL_ID = '"+contact.getEmailID()+"' AND PASSWORD = '"+contact.getPassword()+"'");
				result = "MatchFound";
				if (contactInfo.next()) {
					contact.setFirstName(contactInfo.getString("FIRST_NAME"));
					contact.setLastName(contactInfo.getString("LAST_NAME"));
					contact.setProfilePic(contactInfo.getString("PROFILE_PIC"));
					//System.out.println(contact.getFirstName()+"   "+contact.getLastName());
					contact.setResponseMessage(result);
				}
				
			}
			else{
				result = "MatchNotFound";
				System.out.println("Match Not Found");
				contact.setResponseMessage(result);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			contact.setResponseMessage("DB Down");
		}finally {
			conn.close();
		}
		
		
	}

}