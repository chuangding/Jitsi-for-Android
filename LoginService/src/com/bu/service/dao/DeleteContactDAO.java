package com.bu.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.bu.service.login.Contact;

public class DeleteContactDAO {
	
	Connection conn= null;

	public void deleteContact(Contact contact) throws ClassNotFoundException, SQLException{
		Statement stmt = null;
		int count=0;
					
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://jitsi-bu-mysql-instance1.cwxrlbzxd0ib.us-west-2.rds.amazonaws.com:3306","Jitsiandroid1","Jitsiandroid1");
			stmt = conn.createStatement();
			count = stmt.executeUpdate("DELETE FROM JITSIBU.CONTACT_LIST WHERE SECONDARY_CONTACT_ID = '"+contact.getContactEmail()+"' AND PRIMARY_CONTACT_ID = '"+contact.getEmailID()+"'");
		    if(count==1){
		    	contact.setResponseMessage("Delete Successful");	
		    	System.out.println(contact.getResponseMessage());
		    }
		    else if(count==0){
		    	contact.setResponseMessage("Error Occured");		    	
		    }
		    
		    else contact.setResponseMessage("Error Occured");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			contact.setResponseMessage("DB Down");
		}finally{
			conn.close();
		}
		
	}
}