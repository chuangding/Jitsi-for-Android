package com.bu.service.test;

import java.sql.SQLException;

import org.json.JSONException;

import com.bu.service.dao.AddContactListDAO;
import com.bu.service.dao.AddProfilePicDAO;
import com.bu.service.dao.DisplayContactDAO;
import com.bu.service.dao.DisplayProfilePicDAO;
import com.bu.service.dao.RegisterDAO;
import com.bu.service.dao.VerifyUserDAO;
import com.bu.service.login.Contact;
import com.bu.service.login.LoginService;

public class DAOtest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, JSONException {
		// TODO Auto-generated method stub
		Contact cont = new Contact();
		//cont.setFirstName("test");
		//cont.setLastName("test");
		cont.setEmailID("karunesh@bu.edu");
		//cont.setProfilePic("ABCD");
		//cont.setContactEmail("gyu");
		DisplayProfilePicDAO dao = new DisplayProfilePicDAO();
		dao.displayProfilePic(cont);
		
	}

}
