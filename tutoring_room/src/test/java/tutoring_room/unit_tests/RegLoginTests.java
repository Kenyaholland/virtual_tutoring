package tutoring_room.unit_tests;

import brogrammers.tutoring_room.data_access.UserDAO;
import brogrammers.tutoring_room.reglogin.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Collection of unit tests for registration and login capabilities
 */
public class RegLoginTests {
	
	public RegLoginTests() {}
	
	@Test
	public void validRegistrationCredsTest() {
		
		Registration reg = new Registration();
		boolean status = reg.isInformationGood("username1", "Password1!", "email1@uwf.edu");
		assertTrue(status);
	}
	
	@Test
	public void validRegistrationTest() {
		
		UserDAO dao = new UserDAO();
		
		Registration reg = new Registration();
		boolean status = reg.isInformationGood("username1", "Password1!", "email1@uwf.edu");

		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		assertNotNull(temp_user);
		
		dao.connectToDatabase();
		status = dao.checkExistenceOfUserName("username1");
		assertTrue(status);
		
		dao.deleteUser(temp_user);
		dao.closeConnection();
	}
	
	@Test
	public void invalidRegistrationCredsTest() {
		
		boolean status;
		Registration reg = new Registration();
		UserDAO dao = new UserDAO();
		dao.connectToDatabase();
		
		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		
		// Testing repeat username
		status = reg.isInformationGood("username1", "Password2!", "email2@uwf.edu");
		assertFalse(status);
		
		// Testing repeat email
		status = reg.isInformationGood("username2", "Password2!", "email1@uwf.edu");
		assertFalse(status);
		
		// Testing invalid password
		status = reg.isInformationGood("username2", "password2", "email2@uwf.edu");
		assertFalse(status);
				
		dao.deleteUser(temp_user);
		dao.closeConnection();
	}
	
	@Test
	public void validLoginTest() {
		
		Registration reg = new Registration();
		Login login = new Login();
		UserDAO dao = new UserDAO();
		dao.connectToDatabase();

		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		
		// Valid login attempt
		boolean status = login.login("username1", "Password1!");
		assertTrue(status);
		
		dao.deleteUser(temp_user);
		dao.closeConnection();
	}
	
	@Test
	public void notRegisteredLoginCredsTest() {
		
		Login login = new Login();
		
		// Testing login attempt with unregistered user credentials
		boolean status = login.login("username1", "Password1!");
		assertFalse(status);
	}
	
	@Test
	public void invalidLoginPasswordTest() {
		
		Registration reg = new Registration();
		Login login = new Login();
		UserDAO dao = new UserDAO();

		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		
		boolean status = login.login("username1", "password1");
		assertFalse(status);
		
		dao.connectToDatabase();
		dao.deleteUser(temp_user);
		dao.closeConnection();
	}

	
	@Test
	public void changePasswordTest() {
		
		Registration reg = new Registration();
		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		UserDAO dao = new UserDAO();
		dao.connectToDatabase();
		
		ChangePassword changer = new ChangePassword();
		String storedPassword = dao.getSaltedHash("username1");
		changer.changePassword("username1", "email1@uwf.edu", "Password2!");
		String storedNewPassword = dao.getSaltedHash("username1");
				
		assertFalse(storedPassword.equals(storedNewPassword));
		dao.deleteUser(temp_user);
		dao.closeConnection();
	}

}
