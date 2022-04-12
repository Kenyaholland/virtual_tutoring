package tutoring_room.integration_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import brogrammers.tutoring_room.controllers.DirectoryController;
import brogrammers.tutoring_room.controllers.RoomController;
import brogrammers.tutoring_room.controllers.SessionController;
import brogrammers.tutoring_room.data_access.SessionDAO;
import brogrammers.tutoring_room.data_access.UserDAO;
import brogrammers.tutoring_room.reglogin.Login;
import brogrammers.tutoring_room.reglogin.Registration;
import brogrammers.tutoring_room.reglogin.User;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Purely functional integration test case for a student user registration/login with directory and room operations
 */
class StudentTest {

	@Test
	void studentLoginAndJoinBreakoutGroupTest() {
		
		UserDAO udao = new UserDAO();
		udao.connectToDatabase();
		SessionDAO sdao = new SessionDAO();
		sdao.connectToDatabase();
		
		Registration reg = new Registration();
		Login login = new Login();
		
		// Test user entry added in db on successful registration
		User test_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		assertTrue(udao.checkExistenceOfUserName("username1"));
		
		// Test valid login after registration
		boolean status = login.login("username1", "Password1!");
		assertTrue(status);
		
		// Begin session
		SessionController sessionCtrl = new SessionController("username1");
		sessionCtrl.openSession();
		String sessionId = sessionCtrl.getSessionId();
		
		// Test session establishment
		String active_username = sdao.getActiveUserName(sessionId);
		assertTrue(active_username.equals("username1"));
		
		// Testing entering room from directory
		DirectoryController dirCtrl = new DirectoryController(sessionId);
		int init_count = dirCtrl.getNumStudentsInRoom(1);
		dirCtrl.addUserToRoom(1);
		int result_count = dirCtrl.getNumStudentsInRoom(1);
		assertTrue(result_count == (init_count + 1));
		System.out.println(dirCtrl.getNumStudentsInRoom(1));
		
		// Testing joining breakout group
		RoomController roomCtrl = new RoomController(1, sessionId);
		int init_group = udao.getGroupNum("username1");
		roomCtrl.joinBreakoutGroup(1);
		int result_group = udao.getGroupNum("username1");
		assertTrue((init_group == 0) && (result_group == 1));
		
		// Testing database updates when session is closed
		roomCtrl.removeFromBreakoutGroup();
		dirCtrl.removeFromRoom();
		System.out.println(dirCtrl.getNumStudentsInRoom(1));
		
		result_group = udao.getGroupNum("username1");
		assertTrue(result_group == 0);
		
		result_count = dirCtrl.getNumStudentsInRoom(1);
		assertTrue(result_count == 0);
		
		sessionCtrl.closeSession();
		assertNull(sessionCtrl.getSession());
		
		// removing test user entry from db
		udao.deleteUser(test_user);
		assertFalse(udao.checkExistenceOfUserName("username1"));
		
		udao.closeConnection();
		sdao.closeConnection();
	}

}
