package com.bu.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;

public class RegisterDAO {
	
	Connection conn= null;

	public boolean registerUser(Contact contact){
		Statement stmt = null;
		boolean status=false;
		ResultSet rs;
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");
			stmt = conn.createStatement();
			status = stmt.execute("INSERT INTO JITSIBU.CONTACT VALUES('"+contact.getFirstName()+"','"+contact.getLastName()+"','"+contact.getEmailID()+"','"+contact.getPassword()+"')");
		    /*while(rs.next()){
		    	System.out.println(rs.getString(1));
		    }*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
		
	}
}
