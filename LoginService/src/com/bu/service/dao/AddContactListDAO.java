package com.bu.service.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;

import java.sql.Connection;

public class AddContactListDAO {

	Connection conn = null;
	Connection conn2 = null;

	public void addContactList(Contact contact) throws ClassNotFoundException, SQLException{
		Statement stmt = null;
		Statement stmt2=null;
		int rowinsert=0;
		int count=0;
		String result="Initialized";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");

			stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery("SELECT COUNT(*) FROM JITSIBU.CONTACT WHERE EMAIL_ID = '"+contact.getContactEmail()+"'");
			if(rs.next()) {
				count = rs.getInt(1);}
			if(count==0) {
				contact.setResponseMessage("Secondary Contact Not Present") ;
				System.out.println(contact.getResponseMessage());
			}
			else {
				stmt = conn.createStatement();
				rowinsert = stmt.executeUpdate("INSERT INTO JITSIBU.CONTACT_LIST SELECT '"+contact.getEmailID()+"','"+contact.getContactEmail()+"', CONTACT.FIRST_NAME,CONTACT.LAST_NAME FROM JITSIBU.CONTACT WHERE CONTACT.EMAIL_ID = '"+contact.getContactEmail()+"' AND NOT EXISTS (SELECT 1 FROM JITSIBU.CONTACT_LIST WHERE PRIMARY_CONTACT_ID = '"+contact.getEmailID()+"' and SECONDARY_CONTACT_ID = '"+contact.getContactEmail()+"')");

				if(rowinsert==1){
					contact.setResponseMessage("Secondary Contact Added") ;
					System.out.println(contact.getResponseMessage());

				}
				if(rowinsert==0) {
					contact.setResponseMessage("Secondary Contact Already Present");
					System.out.println(contact.getResponseMessage());		
				}

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