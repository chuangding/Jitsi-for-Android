package com.bu.service.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;
import java.sql.Connection;

public class DisplayContactDAO {
	Connection conn = null;	
	String result;

	public void displayContact(Contact contact) throws SQLException, ClassNotFoundException {
		Statement stmt = null;	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SECONDARY_CONTACT_ID FROM JITSIBU.CONTACT_LIST WHERE PRIMARY_CONTACT_ID = '"+contact.getEmailID()+"'");


			while(rs.next()) {
				contact.getContactList().add(rs.getString("SECONDARY_CONTACT_ID"));
			}

			if(contact.getContactList().size()>0){
				result="Successfully Fetched";
				contact.setResponseMessage(result);
			}
			else {
				result="Error in Fetching";
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			contact.setResponseMessage("SQL Exception");
			System.out.println(contact.getResponseMessage());	
		}finally{
			conn.close();
		}
	}

}
