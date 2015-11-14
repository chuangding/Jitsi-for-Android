package com.bu.service.test;

import com.bu.service.dao.RegisterDAO;
import com.bu.service.login.Contact;

public class DAOtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Contact cont = new Contact();
		cont.setFirstName("test");
		cont.setLastName("test");
		cont.setEmailID("test");
		cont.setPassword("test");
		RegisterDAO dao = new RegisterDAO();
		dao.registerUser(cont);
		
	}

}
